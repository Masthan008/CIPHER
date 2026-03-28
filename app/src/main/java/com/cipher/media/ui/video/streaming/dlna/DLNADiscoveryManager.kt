package com.cipher.media.ui.video.streaming.dlna

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.*

/**
 * PRO FEATURE: Lightweight DLNA/UPnP device discovery.
 * Uses Android NSD (native) + raw SSDP multicast — no cling dependency.
 */
class DLNADiscoveryManager(private val context: Context) {

    companion object {
        private const val TAG = "DLNADiscovery"
        private val SSDP_ADDRESS = InetAddress.getByName("239.255.255.250")
        private const val SSDP_PORT = 1900
        private const val SEARCH_TARGET = "urn:schemas-upnp-org:device:MediaRenderer:1"
        private const val UPNP_SERVICE_TYPE = "_upnp._tcp."
    }

    private val _devices = MutableStateFlow<List<DLNADevice>>(emptyList())
    val devices: StateFlow<List<DLNADevice>> = _devices.asStateFlow()

    private var nsdManager: NsdManager? = null
    private var discoveryListener: NsdManager.DiscoveryListener? = null
    private var discoveryJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    /**
     * Start device discovery using NSD + SSDP.
     */
    fun startDiscovery() {
        Log.d(TAG, "Starting DLNA discovery...")
        _devices.value = emptyList()
        discoverViaNSD()
        discoveryJob = scope.launch { sendSSDPSearch() }
    }

    /**
     * Stop all discovery.
     */
    fun stopDiscovery() {
        try {
            discoveryListener?.let { nsdManager?.stopServiceDiscovery(it) }
        } catch (e: Exception) {
            Log.w(TAG, "NSD stop error: ${e.message}")
        }
        discoveryJob?.cancel()
        discoveryListener = null
    }

    // ═══════════════════════════════════════════
    //  NSD Discovery (Android native, zero deps)
    // ═══════════════════════════════════════════
    private fun discoverViaNSD() {
        nsdManager = context.getSystemService(Context.NSD_SERVICE) as? NsdManager ?: return

        discoveryListener = object : NsdManager.DiscoveryListener {
            override fun onServiceFound(serviceInfo: NsdServiceInfo) {
                nsdManager?.resolveService(serviceInfo, object : NsdManager.ResolveListener {
                    override fun onServiceResolved(info: NsdServiceInfo) {
                        val device = DLNADevice(
                            id = info.host?.hostAddress ?: return,
                            name = info.serviceName ?: "Unknown Device",
                            host = info.host ?: return,
                            port = info.port,
                            discoveryType = "NSD"
                        )
                        addDevice(device)
                    }
                    override fun onResolveFailed(info: NsdServiceInfo, errorCode: Int) {
                        Log.w(TAG, "NSD resolve failed: $errorCode")
                    }
                })
            }
            override fun onServiceLost(info: NsdServiceInfo) {
                removeDevice(info.host?.hostAddress ?: "")
            }
            override fun onDiscoveryStarted(serviceType: String) {
                Log.d(TAG, "NSD discovery started")
            }
            override fun onDiscoveryStopped(serviceType: String) {
                Log.d(TAG, "NSD discovery stopped")
            }
            override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
                Log.e(TAG, "NSD start failed: $errorCode")
            }
            override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
                Log.e(TAG, "NSD stop failed: $errorCode")
            }
        }

        try {
            nsdManager?.discoverServices(UPNP_SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener)
        } catch (e: Exception) {
            Log.e(TAG, "NSD discovery error: ${e.message}")
        }
    }

    // ═══════════════════════════════════════════
    //  SSDP Multicast (raw socket, lightweight)
    // ═══════════════════════════════════════════
    private suspend fun sendSSDPSearch() = withContext(Dispatchers.IO) {
        try {
            val socket = MulticastSocket(null).apply {
                reuseAddress = true
                bind(InetSocketAddress(SSDP_PORT))
                soTimeout = 5000
                joinGroup(SSDP_ADDRESS)
            }

            val searchMessage = buildString {
                append("M-SEARCH * HTTP/1.1\r\n")
                append("HOST: 239.255.255.250:1900\r\n")
                append("MAN: \"ssdp:discover\"\r\n")
                append("MX: 3\r\n")
                append("ST: $SEARCH_TARGET\r\n")
                append("\r\n")
            }

            val data = searchMessage.toByteArray()
            val packet = DatagramPacket(data, data.size, SSDP_ADDRESS, SSDP_PORT)
            socket.send(packet)

            // Receive responses
            val buffer = ByteArray(2048)
            while (isActive) {
                try {
                    val response = DatagramPacket(buffer, buffer.size)
                    socket.receive(response)
                    val body = String(response.data, 0, response.length)
                    parseSSDPResponse(body, response.address)
                } catch (e: SocketTimeoutException) {
                    break // Done receiving
                }
            }

            socket.leaveGroup(SSDP_ADDRESS)
            socket.close()
        } catch (e: Exception) {
            Log.e(TAG, "SSDP search failed: ${e.message}")
        }
    }

    private fun parseSSDPResponse(response: String, address: InetAddress) {
        val location = response.lines()
            .firstOrNull { it.startsWith("LOCATION:", ignoreCase = true) }
            ?.substringAfter(":")
            ?.trim() ?: return

        scope.launch { fetchDeviceDescription(location, address) }
    }

    /**
     * Fetch device description XML from LOCATION header URL.
     * Extracts friendlyName and controlURL for AVTransport.
     */
    private suspend fun fetchDeviceDescription(locationUrl: String, fallbackAddress: InetAddress) {
        withContext(Dispatchers.IO) {
            try {
                val url = URL(locationUrl)
                val conn = url.openConnection() as HttpURLConnection
                conn.connectTimeout = 5000
                conn.readTimeout = 5000
                val xml = conn.inputStream.bufferedReader().readText()
                conn.disconnect()

                val deviceName = xml.extractXmlTag("friendlyName") ?: "DLNA Device"
                val controlURL = xml.extractXmlTag("controlURL") ?: "/AVTransport/control"

                val device = DLNADevice(
                    id = fallbackAddress.hostAddress ?: locationUrl,
                    name = deviceName,
                    host = fallbackAddress,
                    port = url.port.let { if (it == -1) url.defaultPort else it },
                    controlURL = controlURL,
                    discoveryType = "SSDP"
                )
                addDevice(device)
            } catch (e: Exception) {
                Log.w(TAG, "Failed to fetch device XML: ${e.message}")
            }
        }
    }

    // ═══════════════════════════════════════════
    //  Device list management
    // ═══════════════════════════════════════════
    private fun addDevice(device: DLNADevice) {
        val current = _devices.value.toMutableList()
        val existing = current.indexOfFirst { it.id == device.id }
        if (existing >= 0) {
            current[existing] = device // Update with richer info
        } else {
            current.add(device)
        }
        _devices.value = current
        Log.d(TAG, "Device found: ${device.name} @ ${device.baseUrl}")
    }

    private fun removeDevice(id: String) {
        _devices.value = _devices.value.filter { it.id != id }
    }

    fun release() {
        stopDiscovery()
        scope.cancel()
    }
}

// Lightweight XML tag extractor (no parser dependency)
internal fun String.extractXmlTag(tagName: String): String? {
    val regex = Regex("<$tagName[^>]*>(.*?)</$tagName>", RegexOption.DOT_MATCHES_ALL)
    return regex.find(this)?.groupValues?.get(1)?.trim()
}
