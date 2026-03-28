package com.cipher.media.ui.video.streaming.dlna

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

/**
 * PRO FEATURE: Lightweight DLNA transport layer.
 * Sends SOAP-lite HTTP commands to DLNA MediaRenderers.
 * No cling, no heavy SOAP libraries — pure HTTP + XML strings.
 *
 * Supported commands:
 *  - SetAVTransportURI (send video to TV)
 *  - Play / Pause / Stop / Seek
 *  - GetPositionInfo (poll playback position)
 */
class DLNATransportManager {

    companion object {
        private const val TAG = "DLNATransport"
        private const val AV_TRANSPORT = "urn:schemas-upnp-org:service:AVTransport:1"
    }

    /**
     * Send a video URL to the target DLNA renderer.
     */
    suspend fun setAVTransportURI(device: DLNADevice, videoUrl: String, title: String): Boolean {
        val escapedUrl = escapeXml(videoUrl)
        val escapedTitle = escapeXml(title)

        val soapBody = """<?xml version="1.0" encoding="utf-8"?>
<s:Envelope xmlns:s="http://schemas.xmlsoap.org/soap/envelope/" s:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
  <s:Body>
    <u:SetAVTransportURI xmlns:u="$AV_TRANSPORT">
      <InstanceID>0</InstanceID>
      <CurrentURI>$escapedUrl</CurrentURI>
      <CurrentURIMetaData>&lt;DIDL-Lite xmlns=&quot;urn:schemas-upnp-org:metadata-1-0/DIDL-Lite/&quot;&gt;&lt;item id=&quot;0&quot; parentID=&quot;-1&quot; restricted=&quot;1&quot;&gt;&lt;dc:title xmlns:dc=&quot;http://purl.org/dc/elements/1.1/&quot;&gt;$escapedTitle&lt;/dc:title&gt;&lt;upnp:class xmlns:upnp=&quot;urn:schemas-upnp-org:metadata-1-0/upnp/&quot;&gt;object.item.videoItem&lt;/upnp:class&gt;&lt;res protocolInfo=&quot;http-get:*:video/mp4:*&quot;&gt;$escapedUrl&lt;/res&gt;&lt;/item&gt;&lt;/DIDL-Lite&gt;</CurrentURIMetaData>
    </u:SetAVTransportURI>
  </s:Body>
</s:Envelope>"""

        return sendSoapAction(device, "SetAVTransportURI", soapBody)
    }

    /** Play command. */
    suspend fun play(device: DLNADevice): Boolean {
        val soapBody = soapEnvelope("Play", "<InstanceID>0</InstanceID><Speed>1</Speed>")
        return sendSoapAction(device, "Play", soapBody)
    }

    /** Pause command. */
    suspend fun pause(device: DLNADevice): Boolean {
        val soapBody = soapEnvelope("Pause", "<InstanceID>0</InstanceID>")
        return sendSoapAction(device, "Pause", soapBody)
    }

    /** Stop command. */
    suspend fun stop(device: DLNADevice): Boolean {
        val soapBody = soapEnvelope("Stop", "<InstanceID>0</InstanceID>")
        return sendSoapAction(device, "Stop", soapBody)
    }

    /** Seek to position. */
    suspend fun seek(device: DLNADevice, positionSeconds: Int): Boolean {
        val time = formatDuration(positionSeconds)
        val soapBody = soapEnvelope("Seek",
            "<InstanceID>0</InstanceID><Unit>REL_TIME</Unit><Target>$time</Target>")
        return sendSoapAction(device, "Seek", soapBody)
    }

    /** Poll current playback position. */
    suspend fun getPositionInfo(device: DLNADevice): DLNAPositionInfo? {
        val soapBody = soapEnvelope("GetPositionInfo", "<InstanceID>0</InstanceID>")
        val response = sendSoapActionWithResponse(device, "GetPositionInfo", soapBody)
            ?: return null

        return try {
            val relTime = response.extractXmlTag("RelTime") ?: "0:00:00"
            val duration = response.extractXmlTag("TrackDuration") ?: "0:00:00"
            val trackUri = response.extractXmlTag("TrackURI") ?: ""

            DLNAPositionInfo(
                trackDurationSeconds = parseDuration(duration),
                relTimeSeconds = parseDuration(relTime),
                trackURI = trackUri
            )
        } catch (e: Exception) {
            Log.w(TAG, "Failed to parse position: ${e.message}")
            null
        }
    }

    // ═══════════════════════════════════════════
    //  Core SOAP sender
    // ═══════════════════════════════════════════

    private suspend fun sendSoapAction(
        device: DLNADevice,
        action: String,
        soapBody: String
    ): Boolean {
        return sendSoapActionWithResponse(device, action, soapBody) != null
    }

    private suspend fun sendSoapActionWithResponse(
        device: DLNADevice,
        action: String,
        soapBody: String
    ): String? = withContext(Dispatchers.IO) {
        try {
            val url = URL(device.controlEndpoint)
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "text/xml; charset=utf-8")
                setRequestProperty("SOAPAction", "\"$AV_TRANSPORT#$action\"")
                connectTimeout = 5000
                readTimeout = 5000
                doOutput = true
            }

            conn.outputStream.use { it.write(soapBody.toByteArray()) }

            val code = conn.responseCode
            if (code in 200..299) {
                val body = conn.inputStream.bufferedReader().readText()
                conn.disconnect()
                Log.d(TAG, "$action → $code OK")
                body
            } else {
                val error = try { conn.errorStream?.bufferedReader()?.readText() } catch (_: Exception) { null }
                conn.disconnect()
                Log.e(TAG, "$action → $code: $error")
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "$action failed: ${e.message}")
            null
        }
    }

    // ═══════════════════════════════════════════
    //  Helpers
    // ═══════════════════════════════════════════

    private fun soapEnvelope(action: String, innerXml: String): String {
        return """<?xml version="1.0" encoding="utf-8"?>
<s:Envelope xmlns:s="http://schemas.xmlsoap.org/soap/envelope/" s:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
  <s:Body>
    <u:$action xmlns:u="$AV_TRANSPORT">
      $innerXml
    </u:$action>
  </s:Body>
</s:Envelope>"""
    }

    private fun formatDuration(seconds: Int): String {
        val h = seconds / 3600
        val m = (seconds % 3600) / 60
        val s = seconds % 60
        return "%02d:%02d:%02d".format(h, m, s)
    }

    private fun parseDuration(timeStr: String): Int {
        val parts = timeStr.split(":")
        if (parts.size != 3) return 0
        return try {
            val h = parts[0].toInt()
            val m = parts[1].toInt()
            val s = parts[2].split(".")[0].toInt() // strip fractional
            h * 3600 + m * 60 + s
        } catch (e: Exception) { 0 }
    }

    private fun escapeXml(s: String): String = s
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&apos;")
}
