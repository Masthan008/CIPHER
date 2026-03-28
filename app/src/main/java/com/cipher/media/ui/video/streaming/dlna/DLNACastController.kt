package com.cipher.media.ui.video.streaming.dlna

import android.content.Context
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * PRO FEATURE: Unified DLNA cast controller.
 * Manages device discovery, media transport, and position polling.
 * Integrates alongside existing Chromecast CastManager.
 */
class DLNACastController(context: Context) {

    companion object {
        private const val TAG = "DLNACast"
        private const val POLL_INTERVAL_MS = 1000L
    }

    private val discovery = DLNADiscoveryManager(context)
    private val transport = DLNATransportManager()
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    /** Discovered DLNA devices. */
    val devices: StateFlow<List<DLNADevice>> = discovery.devices

    /** Currently casting to this device (null = not casting). */
    private val _activeDevice = MutableStateFlow<DLNADevice?>(null)
    val activeDevice: StateFlow<DLNADevice?> = _activeDevice.asStateFlow()

    /** Current playback state. */
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    /** Position info for seek bar. */
    private val _position = MutableStateFlow(DLNAPositionInfo())
    val position: StateFlow<DLNAPositionInfo> = _position.asStateFlow()

    private var pollingJob: Job? = null

    // ═══════════════════════════════════════════
    //  Discovery
    // ═══════════════════════════════════════════

    fun startDiscovery() = discovery.startDiscovery()
    fun stopDiscovery() = discovery.stopDiscovery()

    // ═══════════════════════════════════════════
    //  Casting
    // ═══════════════════════════════════════════

    /**
     * Cast a video URL to the selected DLNA device.
     * @param device Target DLNA renderer
     * @param videoUrl HTTP(S) video URL accessible on the local network
     * @param title Display title
     * @param seekToSeconds Optional starting position
     */
    suspend fun castVideo(
        device: DLNADevice,
        videoUrl: String,
        title: String,
        seekToSeconds: Int = 0
    ): Boolean {
        Log.d(TAG, "Casting '$title' → ${device.name}")

        val setUri = transport.setAVTransportURI(device, videoUrl, title)
        if (!setUri) {
            Log.e(TAG, "SetAVTransportURI failed")
            return false
        }

        delay(500) // Brief delay for device buffering

        val played = transport.play(device)
        if (!played) {
            Log.e(TAG, "Play command failed")
            return false
        }

        if (seekToSeconds > 0) {
            delay(300)
            transport.seek(device, seekToSeconds)
        }

        _activeDevice.value = device
        _isPlaying.value = true
        startPositionPolling(device)

        Log.d(TAG, "Casting started successfully")
        return true
    }

    /** Toggle play/pause. */
    suspend fun togglePlayPause() {
        val device = _activeDevice.value ?: return
        if (_isPlaying.value) {
            transport.pause(device)
            _isPlaying.value = false
        } else {
            transport.play(device)
            _isPlaying.value = true
        }
    }

    /** Seek to position in seconds. */
    suspend fun seekTo(positionSeconds: Int) {
        val device = _activeDevice.value ?: return
        transport.seek(device, positionSeconds)
    }

    /** Stop casting and disconnect. */
    suspend fun stopCasting() {
        val device = _activeDevice.value ?: return
        pollingJob?.cancel()
        transport.stop(device)
        _activeDevice.value = null
        _isPlaying.value = false
        _position.value = DLNAPositionInfo()
        Log.d(TAG, "Casting stopped")
    }

    /** Whether we are currently casting via DLNA. */
    val isCasting: Boolean get() = _activeDevice.value != null

    // ═══════════════════════════════════════════
    //  Position polling
    // ═══════════════════════════════════════════

    private fun startPositionPolling(device: DLNADevice) {
        pollingJob?.cancel()
        pollingJob = scope.launch {
            while (isActive) {
                try {
                    val info = transport.getPositionInfo(device)
                    if (info != null) {
                        _position.value = info
                    }
                } catch (e: Exception) {
                    Log.w(TAG, "Position poll error: ${e.message}")
                }
                delay(POLL_INTERVAL_MS)
            }
        }
    }

    // ═══════════════════════════════════════════
    //  Cleanup
    // ═══════════════════════════════════════════

    fun release() {
        pollingJob?.cancel()
        discovery.release()
        scope.cancel()
    }
}
