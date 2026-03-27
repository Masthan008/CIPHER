package com.cipher.media.ui.audio.equalizer;

import androidx.lifecycle.ViewModel;
import com.cipher.media.data.model.EqualizerPreset;
import com.cipher.media.data.repository.EqualizerRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u0016\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\b2\u0006\u0010\u001a\u001a\u00020\bJ\u000e\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\bJ\u000e\u0010\u001d\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u000eJ\u000e\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\bR\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\nR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\n\u00a8\u0006 "}, d2 = {"Lcom/cipher/media/ui/audio/equalizer/EqualizerViewModel;", "Landroidx/lifecycle/ViewModel;", "eqRepository", "Lcom/cipher/media/data/repository/EqualizerRepository;", "(Lcom/cipher/media/data/repository/EqualizerRepository;)V", "bandGains", "Lkotlinx/coroutines/flow/StateFlow;", "", "", "getBandGains", "()Lkotlinx/coroutines/flow/StateFlow;", "bassBoostStrength", "getBassBoostStrength", "isEqEnabled", "", "selectedPresetName", "", "getSelectedPresetName", "virtualizerStrength", "getVirtualizerStrength", "selectPreset", "", "preset", "Lcom/cipher/media/data/model/EqualizerPreset;", "setBandGain", "bandIndex", "gain", "setBassBoost", "strength", "setEqEnabled", "enabled", "setVirtualizer", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class EqualizerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.data.repository.EqualizerRepository eqRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.Integer>> bandGains = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> selectedPresetName = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isEqEnabled = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> bassBoostStrength = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> virtualizerStrength = null;
    
    @javax.inject.Inject()
    public EqualizerViewModel(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.repository.EqualizerRepository eqRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<java.lang.Integer>> getBandGains() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSelectedPresetName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isEqEnabled() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getBassBoostStrength() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getVirtualizerStrength() {
        return null;
    }
    
    public final void selectPreset(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.EqualizerPreset preset) {
    }
    
    public final void setBandGain(int bandIndex, int gain) {
    }
    
    public final void setEqEnabled(boolean enabled) {
    }
    
    public final void setBassBoost(int strength) {
    }
    
    public final void setVirtualizer(int strength) {
    }
}