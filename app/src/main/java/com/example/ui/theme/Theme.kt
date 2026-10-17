package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.model.IslamicColorPalette
import com.example.model.ThemeColorBundle

val LocalIslamicPalette = staticCompositionLocalOf {
  IslamicColorPalette.EMERALD_GREEN
}

val LocalAppThemeColors = staticCompositionLocalOf {
  IslamicColorPalette.EMERALD_GREEN.colors
}

object AppTheme {
  val palette: IslamicColorPalette
    @Composable
    @ReadOnlyComposable
    get() = LocalIslamicPalette.current

  val colors: ThemeColorBundle
    @Composable
    @ReadOnlyComposable
    get() = LocalAppThemeColors.current
}

@Composable
fun NoorIslamTheme(
  palette: IslamicColorPalette = IslamicColorPalette.EMERALD_GREEN,
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val themeColors = palette.colors
  val colorScheme = darkColorScheme(
    primary = themeColors.primaryButton,
    onPrimary = themeColors.primaryButtonContent,
    primaryContainer = themeColors.headerStart,
    onPrimaryContainer = SoftWhiteText,
    secondary = themeColors.cardBorderSelected,
    onSecondary = themeColors.background,
    background = themeColors.background,
    onBackground = SoftWhiteText,
    surface = themeColors.cardBackgroundUnselected,
    onSurface = SoftWhiteText,
    surfaceVariant = themeColors.cardBackgroundSelected,
    onSurfaceVariant = themeColors.subText,
    outline = themeColors.cardBorderUnselected,
    outlineVariant = themeColors.cardBorderSelected
  )

  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as? Activity)?.window
      if (window != null) {
        window.statusBarColor = themeColors.headerStart.toArgb()
        window.navigationBarColor = themeColors.background.toArgb()
        val insetsController = WindowCompat.getInsetsController(window, view)
        insetsController.isAppearanceLightStatusBars = false
        insetsController.isAppearanceLightNavigationBars = false
      }
    }
  }

  CompositionLocalProvider(
    LocalIslamicPalette provides palette,
    LocalAppThemeColors provides themeColors
  ) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
  }
}

