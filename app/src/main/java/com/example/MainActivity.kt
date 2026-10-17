package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.model.IslamicColorPalette
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OnboardingFlowScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.AppTheme
import com.example.ui.theme.NightGreenBackground
import com.example.ui.theme.NoorIslamTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      var currentPalette by remember { mutableStateOf(IslamicColorPalette.EMERALD_GREEN) }
      NoorIslamTheme(palette = currentPalette) {
        MainAppContent(
          currentPalette = currentPalette,
          onPaletteChange = { currentPalette = it }
        )
      }
    }
  }
}

@Composable
fun MainAppContent(
  currentPalette: IslamicColorPalette = IslamicColorPalette.EMERALD_GREEN,
  onPaletteChange: (IslamicColorPalette) -> Unit = {}
) {
  var showHome by remember { mutableStateOf(false) }
  var showSettings by remember { mutableStateOf(false) }

  Surface(
    modifier = Modifier.fillMaxSize(),
    color = AppTheme.colors.background
  ) {
    if (showSettings) {
      SettingsScreen(
        currentPalette = currentPalette,
        onPaletteChange = onPaletteChange,
        onBack = { showSettings = false }
      )
    } else {
      Crossfade(targetState = showHome, label = "screenTransition") { isHome ->
        if (isHome) {
          HomeScreen(
            currentPalette = currentPalette,
            onPaletteChange = onPaletteChange,
            onOpenSettings = { showSettings = true }
          )
        } else {
          OnboardingFlowScreen(
            selectedPalette = currentPalette,
            onPaletteChange = onPaletteChange,
            onComplete = {
              showHome = true
            }
          )
        }
      }
    }
  }
}

