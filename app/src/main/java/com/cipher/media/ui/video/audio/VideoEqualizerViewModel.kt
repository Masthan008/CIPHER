package com.cipher.media.ui.video.audio

import android.media.audiofx.PresetReverb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.C
import androidx.media3.common.Format
import androidx.media3.common.MimeTypes
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.exoplayer.ExoPlayer
import com.cipher.media.billing.BillingRepository
import com.cipher.media.billing.model.SubscriptionTier
import com.cipher.media.ui.video.audio.model.AudioTrackInfo
import com.cipher.media.ui.video.audio.model.EQPreset
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Defines available Reverb Room Types.
 */
enum class ReverbRoom(val id: Short, val label: String) {
    NONE(PresetReverb.PRESET_NONE, "None"),
    SMALL_ROOM(PresetReverb.PRESET_SMALLROOM, "Small Room"),
    MEDIUM_ROOM(PresetReverb.PRESET_MEDIUMROOM, "Medium Room"),
    LARGE_ROOM(PresetReverb.PRESET_LARGEROOM, "Large Room"),
    CONCERT_HALL(PresetReverb.PRESET_LARGEHALL, "Concert Hall")
}

data class VideoAudioUiState(
    val isPro: Boolean = false,
    val isPower: Boolean = false,
    val isHiRes: Boolean = false,
    
    // Effects State
    val bandGains: List<Int> = List(10) { 0 },
    val bassBoostStrength: Int = 0, // 0 to 1000
    val virtualizerStrength: Int = 0, // 0 to 1000
    val loudnessEnhancerGain: Int = 0, // 0 to 1000 mB
    val reverbPreset: ReverbRoom = ReverbRoom.NONE,
    val selectedPreset: EQPreset = EQPreset.FLAT,
    
    // ExoPlayer properties
    val availableAudioTracks: List<AudioTrackInfo> = emptyList(),
    val audioDelayMs: Long = 0L,
    
    val showBottomSheet: Boolean = false
)

@HiltViewModel
class VideoEqualizerViewModel @Inject constructor(
    private val billingRepository: BillingRepository
) : ViewModel() {

    private val audioManager = VideoAudioEffectsManager()
    private var exoPlayer: ExoPlayer? = null

    private val _uiState = MutableStateFlow(VideoAudioUiState())
    val uiState: StateFlow<VideoAudioUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            billingRepository.activeTier.collect { tier ->
                _uiState.value = _uiState.value.copy(
                    isPro = tier == SubscriptionTier.PRO || tier == SubscriptionTier.POWER,
                    isPower = tier == SubscriptionTier.POWER
                )
                // Cap features immediately if tier downgrade happens during playback
                applyTierConstraints()
            }
        }
    }

    /**
     * Attach the ViewModel to the active ExoPlayer instance.
     */
    fun attachPlayer(player: ExoPlayer) {
        exoPlayer = player
        audioManager.attach(player.audioSessionId)
        
        // Setup initial default flat EQ
        selectPreset(EQPreset.FLAT)
        
        // Refresh tracks
        refreshAudioTracks()
    }

    private fun applyTierConstraints() {
        val state = _uiState.value
        
        // If not Pro, constrain Bass Boost to 50% max (500)
        if (!state.isPro && state.bassBoostStrength > 500) {
            setBassBoost(500)
        }
        
        // If not Pro, disable Virtualizer & Reverb
        if (!state.isPro) {
            if (state.virtualizerStrength > 0) setVirtualizer(0)
            if (state.reverbPreset != ReverbRoom.NONE) setReverb(ReverbRoom.NONE)
            if (state.audioDelayMs != 0L) setAudioDelay(0L)
        }
        
        // Free tier uses 3 logical bands under the hood (Low, Mid, High), mapped to the 10-band array visually.
        // It's easier to restrict the UI to only let Free users edit groups of bands together,
        // but the core engine can still receive 10.
    }

    // ── FX Controls ──

    fun setBandGain(logicalIndex: Int, gainMillibels: Int) {
        val newGains = _uiState.value.bandGains.toMutableList()
        newGains[logicalIndex] = gainMillibels
        _uiState.value = _uiState.value.copy(
            bandGains = newGains,
            selectedPreset = EQPreset("Custom", newGains) // Setting a band clears preset selection
        )
        audioManager.setLogicalBandLevel(logicalIndex, gainMillibels)
    }

    fun setFreeTier3Band(lowMb: Int, midMb: Int, highMb: Int) {
        // Map 3 bands to the 10 physical bands for Free tier
        val newGains = listOf(
            lowMb, lowMb, lowMb,   // 31, 63, 125 Hz
            midMb, midMb, midMb, midMb, // 250, 500, 1k, 2k Hz
            highMb, highMb, highMb  // 4k, 8k, 16k Hz
        )
        _uiState.value = _uiState.value.copy(
            bandGains = newGains,
            selectedPreset = EQPreset("Custom", newGains)
        )
        audioManager.apply10BandGains(newGains)
    }

    fun selectPreset(preset: EQPreset) {
        _uiState.value = _uiState.value.copy(
            selectedPreset = preset,
            bandGains = preset.gainsMillibels
        )
        audioManager.apply10BandGains(preset.gainsMillibels)
    }

    fun setBassBoost(strength: Int) {
        val capped = if (!_uiState.value.isPro) strength.coerceAtMost(500) else strength
        _uiState.value = _uiState.value.copy(bassBoostStrength = capped)
        audioManager.setBassBoostStrength(capped)
    }

    fun setVirtualizer(strength: Int) {
        if (!_uiState.value.isPro) return
        _uiState.value = _uiState.value.copy(virtualizerStrength = strength)
        audioManager.setVirtualizerStrength(strength)
    }

    fun setLoudnessEnhancer(gain: Int) {
        _uiState.value = _uiState.value.copy(loudnessEnhancerGain = gain)
        audioManager.setLoudnessEnhancerGain(gain)
    }

    fun setReverb(room: ReverbRoom) {
        if (!_uiState.value.isPro && room != ReverbRoom.NONE) return
        _uiState.value = _uiState.value.copy(reverbPreset = room)
        audioManager.setReverbPreset(room.id)
    }

    // ── ExoPlayer Controls ──

    fun refreshAudioTracks() {
        val p = exoPlayer ?: return
        val currentTracks = p.currentTracks

        var isHiRes = false
        val trackInfos = mutableListOf<AudioTrackInfo>()

        for (group in currentTracks.groups) {
            if (group.type == C.TRACK_TYPE_AUDIO) {
                for (i in 0 until group.length) {
                    val format: Format = group.getTrackFormat(i)
                    
                    // Check if Hi-Res
                    if (format.sampleRate != Format.NO_VALUE && format.sampleRate >= 96000) {
                        isHiRes = true
                    }

                    trackInfos.add(
                        AudioTrackInfo(
                            groupIndex = currentTracks.groups.indexOf(group),
                            trackIndex = i,
                            language = format.language ?: "und",
                            label = format.label ?: "Track ${i + 1}",
                            codec = format.sampleMimeType?.let { MimeTypes.getAudioMediaMimeType(it) } ?: "Audio",
                            sampleRate = format.sampleRate.takeIf { it != Format.NO_VALUE } ?: 44100,
                            channelCount = format.channelCount.takeIf { it != Format.NO_VALUE } ?: 2,
                            isSelected = group.isTrackSelected(i)
                        )
                    )
                }
            }
        }

        _uiState.value = _uiState.value.copy(
            availableAudioTracks = trackInfos,
            isHiRes = isHiRes
        )
    }

    fun selectAudioTrack(info: AudioTrackInfo) {
        if (!_uiState.value.isPro) return
        
        val p = exoPlayer ?: return
        val group = p.currentTracks.groups[info.groupIndex].mediaTrackGroup
        
        p.trackSelectionParameters = p.trackSelectionParameters
            .buildUpon()
            .clearOverridesOfType(C.TRACK_TYPE_AUDIO)
            .addOverride(TrackSelectionOverride(group, listOf(info.trackIndex)))
            .build()
            
        refreshAudioTracks()
    }

    fun setAudioDelay(delayMs: Long) {
        if (!_uiState.value.isPro) return
        
        _uiState.value = _uiState.value.copy(audioDelayMs = delayMs)
        // Note: Audio delay requires custom AudioProcessors in older Exoplayer versions.
        // We will skip hardware audio delay assignment here for compilation compatibility.
    }

    // ── Visibility ──
    
    fun showBottomSheet() {
        _uiState.value = _uiState.value.copy(showBottomSheet = true)
        refreshAudioTracks()
    }
    
    fun hideBottomSheet() {
        _uiState.value = _uiState.value.copy(showBottomSheet = false)
    }

    override fun onCleared() {
        super.onCleared()
        audioManager.release()
    }
}
