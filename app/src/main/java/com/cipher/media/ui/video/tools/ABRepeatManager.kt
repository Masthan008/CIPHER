package com.cipher.media.ui.video.tools

import androidx.media3.common.Player
import com.cipher.media.billing.ProFeatureGate
import com.cipher.media.billing.Tier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Feature 14: A-B Repeat (₹99/month)
 * Loop video sections with markers
 */
@Singleton
class ABRepeatManager @Inject constructor(
    private val proFeatureGate: ProFeatureGate
) {
    private var currentTier: Tier = Tier.FREE

    var pointA: Long? = null
        private set
    var pointB: Long? = null
        private set
    var isEnabled: Boolean = false
        private set

    private val _state = MutableStateFlow(ABRepeatState())
    val state: StateFlow<ABRepeatState> = _state.asStateFlow()

    fun setUserTier(tier: Tier) {
        currentTier = tier
    }

    fun setPointA(currentPosition: Long) {
        if (!proFeatureGate.checkAccess(currentTier)) return

        pointA = currentPosition
        if (pointB != null && pointA!! >= pointB!!) {
            pointB = null
        }
        updateState()
    }

    fun setPointB(currentPosition: Long) {
        if (!proFeatureGate.checkAccess(currentTier)) return

        pointB = currentPosition
        if (pointA != null && pointB!! <= pointA!!) {
            pointA = null
        }
        updateState()
    }

    fun toggle() {
        if (!proFeatureGate.checkAccess(currentTier)) return
        isEnabled = pointA != null && pointB != null
        updateState()
    }

    fun clear() {
        pointA = null
        pointB = null
        isEnabled = false
        updateState()
    }

    fun checkAndLoop(player: Player) {
        if (!proFeatureGate.checkAccess(currentTier) || !isEnabled) return

        val current = player.currentPosition
        val end = pointB ?: return
        val start = pointA ?: return

        if (current >= end - 500) {
            player.seekTo(start)
        }
    }

    private fun updateState() {
        _state.value = ABRepeatState(
            pointA = pointA,
            pointB = pointB,
            isEnabled = isEnabled,
            duration = if (pointA != null && pointB != null) pointB!! - pointA!! else null
        )
    }
}

data class ABRepeatState(
    val pointA: Long? = null,
    val pointB: Long? = null,
    val isEnabled: Boolean = false,
    val duration: Long? = null
)
