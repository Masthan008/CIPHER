package com.cipher.media.ui.video.streaming.dlna

import java.net.InetAddress

/**
 * Discovered DLNA/UPnP MediaRenderer device.
 */
data class DLNADevice(
    val id: String,
    val name: String,
    val host: InetAddress,
    val port: Int,
    val controlURL: String = "",
    val iconURL: String = "",
    val discoveryType: String = "SSDP" // "NSD" or "SSDP"
) {
    /** Full base URL for SOAP commands. */
    val baseUrl: String get() = "http://${host.hostAddress}:$port"

    /** Full control endpoint. */
    val controlEndpoint: String get() = "$baseUrl$controlURL"
}

/**
 * DLNA transport position info from GetPositionInfo.
 */
data class DLNAPositionInfo(
    val trackDurationSeconds: Int = 0,
    val relTimeSeconds: Int = 0,
    val trackURI: String = ""
)

/**
 * Unified cast device merging Chromecast + DLNA for the selector UI.
 */
data class UnifiedCastDevice(
    val id: String,
    val name: String,
    val type: CastDeviceType,
    val isConnected: Boolean = false
) {
    enum class CastDeviceType { CHROMECAST, DLNA }
}
