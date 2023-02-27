package dev.amalhanaja.weatherman

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.amalhanaja.weatherman.core.designsystem.foundation.WMTheme
import dev.amalhanaja.weatherman.feature.home.HomeRoute

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WMTheme {
                HomeRoute()
            }
        }
    }
}
