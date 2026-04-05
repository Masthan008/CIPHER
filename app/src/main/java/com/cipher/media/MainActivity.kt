package com.cipher.media

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.cipher.media.ui.navigation.CIPHERNavigation
import com.cipher.media.ui.theme.CIPHERTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.cipher.media.ui.settings.SettingsRepository
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.isSystemInDarkTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settings by settingsRepository.state.collectAsState()
            
            val isDark = when (settings.themeId) {
                "light" -> false
                "dark" -> true
                else -> isSystemInDarkTheme()
            }

            CIPHERTheme(darkTheme = isDark) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    CIPHERNavigation()
                }
            }
        }
    }
}
