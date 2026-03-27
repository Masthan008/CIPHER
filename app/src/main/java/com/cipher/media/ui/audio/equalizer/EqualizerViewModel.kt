package com.cipher.media.ui.audio.equalizer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipher.media.data.model.EqualizerPreset
import com.cipher.media.data.repository.EqualizerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EqualizerViewModel @Inject constructor(
    private val eqRepository: EqualizerRepository
) : ViewModel() {

    val bandGains: StateFlow<List<Int>> = eqRepository.bandGains
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), List(10) { 0 })

    val selectedPresetName: StateFlow<String> = eqRepository.selectedPresetName
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Flat")

    val isEqEnabled: StateFlow<Boolean> = eqRepository.isEqEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val bassBoostStrength: StateFlow<Int> = eqRepository.bassBoostStrength
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val virtualizerStrength: StateFlow<Int> = eqRepository.virtualizerStrength
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun selectPreset(preset: EqualizerPreset) {
        viewModelScope.launch {
            eqRepository.savePreset(preset)
        }
    }

    fun setBandGain(bandIndex: Int, gain: Int) {
        viewModelScope.launch {
            eqRepository.saveBandGain(bandIndex, gain)
        }
    }

    fun setEqEnabled(enabled: Boolean) {
        viewModelScope.launch {
            eqRepository.setEqEnabled(enabled)
        }
    }

    fun setBassBoost(strength: Int) {
        viewModelScope.launch {
            eqRepository.saveBassBoost(strength)
        }
    }

    fun setVirtualizer(strength: Int) {
        viewModelScope.launch {
            eqRepository.saveVirtualizer(strength)
        }
    }
}
