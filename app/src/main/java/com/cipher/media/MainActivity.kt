package com.cipher.media

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.cipher.media.ui.navigation.CIPHERNavigation
import com.cipher.media.ui.theme.CIPHERTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CIPHERTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = com.cipher.media.ui.theme.CIPHERBackground
                ) {
                    CIPHERNavigation()
                }
            }
        }
    }
}
