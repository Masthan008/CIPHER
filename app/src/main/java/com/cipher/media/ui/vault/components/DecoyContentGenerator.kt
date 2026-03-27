package com.cipher.media.ui.vault.components

import com.cipher.media.data.model.VaultItem

/**
 * Generates placeholder decoy content for the fake vault.
 * When decoy PIN is used, these items appear instead of real vault items.
 */
object DecoyContentGenerator {

    fun generateDecoyItems(): List<VaultItem> {
        val decoyNames = listOf(
            "sunset_beach.jpg",
            "mountain_view.jpg",
            "city_skyline.jpg",
            "flower_garden.jpg",
            "forest_trail.jpg",
            "ocean_waves.jpg",
            "autumn_leaves.jpg",
            "starry_night.jpg"
        )

        return decoyNames.mapIndexed { index, name ->
            VaultItem(
                id = "decoy_$index",
                originalName = name,
                encryptedPath = "", // No actual file
                fileType = VaultItem.FileType.IMAGE,
                size = (500_000L..5_000_000L).random(),
                addedDate = System.currentTimeMillis() - (index * 86_400_000L), // Spread over days
                folderId = null
            )
        }
    }
}
