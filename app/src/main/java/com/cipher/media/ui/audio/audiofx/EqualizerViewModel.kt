package com.cipher.media.ui.audio.audiofx

import androidx.lifecycle.ViewModel
import com.cipher.media.data.model.audiofx.AudioEffectSettings
import com.cipher.media.data.model.audiofx.EQPreset
import com.cipher.media.data.model.audiofx.Tier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EqualizerViewModel @Inject constructor(
    private val audioFxManager: AudioEffectsManager
) : ViewModel() {

    private val _settings = MutableStateFlow(AudioEffectSettings())
    val settings: StateFlow<AudioEffectSettings> = _settings.asStateFlow()

    private val _userTier = MutableStateFlow(Tier.FREE) // Default. Would normally come from BillingManager
    val userTier: StateFlow<Tier> = _userTier.asStateFlow()

    fun setTierForTesting(tier: Tier) {
        _userTier.value = tier
    }

    fun setBandGain(virtualBand: Int, gainDb: Int) {
        val updatedGains = _settings.value.customBands.toMutableList().apply {
            this[virtualBand] = gainDb
        }
        _settings.update { it.copy(customBands = updatedGains, selectedPreset = EQPreset.Custom(updatedGains)) }
        audioFxManager.setBandLevel(virtualBand, gainDb)
    }

    fun applyPreset(preset: EQPreset) {
        if (_userTier.value == Tier.FREE && !EQPreset.FREE_TIER.contains(preset)) {
            // Cannot apply a Pro preset on Free tier in practice here without upgrading.
            return
        }
        
        _settings.update { it.copy(selectedPreset = preset, customBands = preset.bands) }
        preset.bands.forEachIndexed { virtualBand, gainDb ->
            audioFxManager.setBandLevel(virtualBand, gainDb)
        }
    }

    fun setBassBoost(strength: Int) {
        val maxAllowed = if (_userTier.value == Tier.FREE) 500 else 1000
        val secureStrength = strength.coerceIn(0, maxAllowed)
        _settings.update { it.copy(bassBoostStrength = secureStrength) }
        audioFxManager.setBassBoost(secureStrength)
    }

    fun setVirtualizer(strength: Int) {
        if (_userTier.value == Tier.FREE) return // Locked
        val secureStrength = strength.coerceIn(0, 1000)
        _settings.update { it.copy(virtualizerStrength = secureStrength) }
        audioFxManager.setVirtualizer(secureStrength)
    }

    fun setReverb(presetId: Short) {
        if (_userTier.value == Tier.FREE) return // Locked
        _settings.update { it.copy(reverbPreset = presetId) }
        audioFxManager.setReverbPreset(presetId)
    }
}
