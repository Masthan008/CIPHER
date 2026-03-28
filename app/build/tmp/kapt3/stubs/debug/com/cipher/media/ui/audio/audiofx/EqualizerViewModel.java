package com.cipher.media.ui.audio.audiofx;

import androidx.lifecycle.ViewModel;
import com.cipher.media.data.model.audiofx.AudioEffectSettings;
import com.cipher.media.data.model.audiofx.EQPreset;
import com.cipher.media.data.model.audiofx.Tier;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\n\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u0016\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016J\u000e\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u0016J\u000e\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\tJ\u000e\u0010\u001f\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u0016R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\r\u00a8\u0006 "}, d2 = {"Lcom/cipher/media/ui/audio/audiofx/EqualizerViewModel;", "Landroidx/lifecycle/ViewModel;", "audioFxManager", "Lcom/cipher/media/ui/audio/audiofx/AudioEffectsManager;", "(Lcom/cipher/media/ui/audio/audiofx/AudioEffectsManager;)V", "_settings", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/cipher/media/data/model/audiofx/AudioEffectSettings;", "_userTier", "Lcom/cipher/media/data/model/audiofx/Tier;", "settings", "Lkotlinx/coroutines/flow/StateFlow;", "getSettings", "()Lkotlinx/coroutines/flow/StateFlow;", "userTier", "getUserTier", "applyPreset", "", "preset", "Lcom/cipher/media/data/model/audiofx/EQPreset;", "setBandGain", "virtualBand", "", "gainDb", "setBassBoost", "strength", "setReverb", "presetId", "", "setTierForTesting", "tier", "setVirtualizer", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class EqualizerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.audio.audiofx.AudioEffectsManager audioFxManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cipher.media.data.model.audiofx.AudioEffectSettings> _settings = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.data.model.audiofx.AudioEffectSettings> settings = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cipher.media.data.model.audiofx.Tier> _userTier = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.data.model.audiofx.Tier> userTier = null;
    
    @javax.inject.Inject()
    public EqualizerViewModel(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.audio.audiofx.AudioEffectsManager audioFxManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.data.model.audiofx.AudioEffectSettings> getSettings() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.data.model.audiofx.Tier> getUserTier() {
        return null;
    }
    
    public final void setTierForTesting(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.audiofx.Tier tier) {
    }
    
    public final void setBandGain(int virtualBand, int gainDb) {
    }
    
    public final void applyPreset(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.audiofx.EQPreset preset) {
    }
    
    public final void setBassBoost(int strength) {
    }
    
    public final void setVirtualizer(int strength) {
    }
    
    public final void setReverb(short presetId) {
    }
}