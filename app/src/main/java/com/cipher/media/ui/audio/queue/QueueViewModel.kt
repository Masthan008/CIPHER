package com.cipher.media.ui.audio.queue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipher.media.billing.BillingRepository
import com.cipher.media.billing.model.SubscriptionTier
import com.cipher.media.data.model.AudioItem
import com.cipher.media.ui.audio.queue.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for multi-queue management.
 */
data class QueueUiState(
    val queues: List<PlaybackQueueEntity> = emptyList(),
    val activeQueueId: String? = null,
    val activeQueue: PlaybackQueue? = null,
    val isPro: Boolean = false,
    val canCreateMore: Boolean = true,
    val showCreateDialog: Boolean = false,
    val showQueueList: Boolean = false,
    val error: String? = null
) {
    val queueLimit: Int get() = if (isPro) QueueRepository.PRO_QUEUE_LIMIT else QueueRepository.FREE_QUEUE_LIMIT
    val queueCount: Int get() = queues.size
}

/**
 * ViewModel for multiple queue management.
 * FREE: 5 queues, PRO: 20 queues.
 */
@HiltViewModel
class QueueViewModel @Inject constructor(
    private val repo: QueueRepository,
    private val billingRepository: BillingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QueueUiState())
    val uiState: StateFlow<QueueUiState> = _uiState.asStateFlow()

    init {
        // Observe tier
        viewModelScope.launch {
            billingRepository.activeTier.collect { tier ->
                _uiState.value = _uiState.value.copy(
                    isPro = tier == SubscriptionTier.PRO || tier == SubscriptionTier.POWER
                )
                refreshCanCreate()
            }
        }

        // Observe all queues
        viewModelScope.launch {
            repo.allQueues.collect { queues ->
                val activeId = queues.firstOrNull { it.isActive }?.id
                _uiState.value = _uiState.value.copy(
                    queues = queues,
                    activeQueueId = activeId
                )
                refreshCanCreate()
            }
        }

        // Ensure default queue exists
        viewModelScope.launch {
            repo.ensureDefaultQueue()
        }
    }

    // ── Queue CRUD ──

    fun createQueue(name: String, iconName: String = "queue_music") {
        viewModelScope.launch {
            val result = repo.createQueue(name, iconName, _uiState.value.isPro)
            if (result == null) {
                _uiState.value = _uiState.value.copy(
                    error = if (_uiState.value.isPro)
                        "Maximum ${QueueRepository.PRO_QUEUE_LIMIT} queues reached"
                    else
                        "Upgrade to Pro for up to ${QueueRepository.PRO_QUEUE_LIMIT} queues!"
                )
            }
            _uiState.value = _uiState.value.copy(showCreateDialog = false)
            refreshCanCreate()
        }
    }

    fun deleteQueue(queueId: String) {
        viewModelScope.launch {
            // Don't delete the last queue
            if (_uiState.value.queues.size <= 1) return@launch
            repo.deleteQueue(queueId)
            // If deleted queue was active, switch to first remaining
            if (_uiState.value.activeQueueId == queueId) {
                val first = _uiState.value.queues.firstOrNull { it.id != queueId }
                first?.let { switchQueue(it.id) }
            }
        }
    }

    fun renameQueue(queueId: String, newName: String) {
        viewModelScope.launch { repo.renameQueue(queueId, newName) }
    }

    fun duplicateQueue(queueId: String) {
        viewModelScope.launch {
            val result = repo.duplicateQueue(queueId, _uiState.value.isPro)
            if (result == null) {
                _uiState.value = _uiState.value.copy(
                    error = "Queue limit reached. ${if (!_uiState.value.isPro) "Upgrade to Pro!" else ""}"
                )
            }
            refreshCanCreate()
        }
    }

    // ── Queue switching ──

    fun switchQueue(queueId: String) {
        viewModelScope.launch {
            // Save current playback position first
            val activeId = _uiState.value.activeQueueId
            // Then switch
            repo.switchQueue(queueId)
            val queue = repo.getQueue(queueId)
            _uiState.value = _uiState.value.copy(
                activeQueueId = queueId,
                activeQueue = queue,
                showQueueList = false
            )
        }
    }

    fun loadActiveQueue() {
        viewModelScope.launch {
            val queue = repo.getActiveQueue()
            _uiState.value = _uiState.value.copy(activeQueue = queue)
        }
    }

    // ── Items ──

    fun setQueueItems(queueId: String, items: List<AudioItem>) {
        viewModelScope.launch {
            repo.setQueueItems(queueId, items)
            if (queueId == _uiState.value.activeQueueId) loadActiveQueue()
        }
    }

    fun addToQueue(queueId: String, audio: AudioItem) {
        viewModelScope.launch {
            repo.addToQueue(queueId, audio)
            if (queueId == _uiState.value.activeQueueId) loadActiveQueue()
        }
    }

    fun removeFromQueue(itemId: Long) {
        viewModelScope.launch {
            repo.removeFromQueue(itemId)
            loadActiveQueue()
        }
    }

    // ── Playback state ──

    fun savePlaybackPosition(index: Int, positionMs: Long) {
        val activeId = _uiState.value.activeQueueId ?: return
        viewModelScope.launch { repo.savePosition(activeId, index, positionMs) }
    }

    fun setPlaybackMode(mode: PlaybackMode) {
        val activeId = _uiState.value.activeQueueId ?: return
        viewModelScope.launch { repo.setPlaybackMode(activeId, mode) }
    }

    // ── UI state ──

    fun showCreateDialog() { _uiState.value = _uiState.value.copy(showCreateDialog = true) }
    fun hideCreateDialog() { _uiState.value = _uiState.value.copy(showCreateDialog = false) }
    fun showQueueList() { _uiState.value = _uiState.value.copy(showQueueList = true) }
    fun hideQueueList() { _uiState.value = _uiState.value.copy(showQueueList = false) }
    fun clearError() { _uiState.value = _uiState.value.copy(error = null) }

    private suspend fun refreshCanCreate() {
        val count = repo.getQueueCount()
        val limit = if (_uiState.value.isPro) QueueRepository.PRO_QUEUE_LIMIT else QueueRepository.FREE_QUEUE_LIMIT
        _uiState.value = _uiState.value.copy(canCreateMore = count < limit)
    }
}
