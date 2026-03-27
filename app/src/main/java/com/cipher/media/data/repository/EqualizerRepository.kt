package com.cipher.media.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.cipher.media.data.model.EqualizerPreset
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.eqDataStore: DataStore<Preferences> by preferencesDataStore(name = "equalizer_settings")

/**
 * Persists equalizer settings (selected preset, custom gains, bass boost, virtualizer)
 * using Jetpack DataStore.
 */
@Singleton
class EqualizerRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val KEY_PRESET_NAME = stringPreferencesKey("eq_preset_name")
        private val KEY_BASS_BOOST = intPreferencesKey("bass_boost_strength")
        private val KEY_VIRTUALIZER = intPreferencesKey("virtualizer_strength")
        private val KEY_EQ_ENABLED = booleanPreferencesKey("eq_enabled")

        // Store 10 band gains as individual keys
        private fun bandKey(index: Int) = intPreferencesKey("band_gain_$index")
    }

    val selectedPresetName: Flow<String> = context.eqDataStore.data.map { prefs ->
        prefs[KEY_PRESET_NAME] ?: "Flat"
    }

    val isEqEnabled: Flow<Boolean> = context.eqDataStore.data.map { prefs ->
        prefs[KEY_EQ_ENABLED] ?: false
    }

    val bandGains: Flow<List<Int>> = context.eqDataStore.data.map { prefs ->
        (0 until EqualizerPreset.NUM_BANDS).map { i ->
            prefs[bandKey(i)] ?: 0
        }
    }

    val bassBoostStrength: Flow<Int> = context.eqDataStore.data.map { prefs ->
        prefs[KEY_BASS_BOOST] ?: 0
    }

    val virtualizerStrength: Flow<Int> = context.eqDataStore.data.map { prefs ->
        prefs[KEY_VIRTUALIZER] ?: 0
    }

    suspend fun savePreset(preset: EqualizerPreset) {
        context.eqDataStore.edit { prefs ->
            prefs[KEY_PRESET_NAME] = preset.name
            preset.bandGains.forEachIndexed { index, gain ->
                prefs[bandKey(index)] = gain
            }
        }
    }

    suspend fun saveBandGain(bandIndex: Int, gain: Int) {
        context.eqDataStore.edit { prefs ->
            prefs[bandKey(bandIndex)] = gain
            prefs[KEY_PRESET_NAME] = "Custom"
        }
    }

    suspend fun setEqEnabled(enabled: Boolean) {
        context.eqDataStore.edit { prefs ->
            prefs[KEY_EQ_ENABLED] = enabled
        }
    }

    suspend fun saveBassBoost(strength: Int) {
        context.eqDataStore.edit { prefs ->
            prefs[KEY_BASS_BOOST] = strength
        }
    }

    suspend fun saveVirtualizer(strength: Int) {
        context.eqDataStore.edit { prefs ->
            prefs[KEY_VIRTUALIZER] = strength
        }
    }
}
