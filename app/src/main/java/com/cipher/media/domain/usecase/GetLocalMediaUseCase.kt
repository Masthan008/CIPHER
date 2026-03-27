package com.cipher.media.domain.usecase

import com.cipher.media.data.model.MediaItem
import com.cipher.media.data.repository.MediaRepository
import javax.inject.Inject

/**
 * Use case for fetching locally stored video files.
 * Encapsulates the data layer access for clean architecture.
 */
class GetLocalMediaUseCase @Inject constructor(
    private val repository: MediaRepository
) {
    suspend operator fun invoke(): List<MediaItem> {
        return repository.getLocalVideos()
    }
}
