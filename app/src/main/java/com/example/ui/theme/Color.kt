package com.example.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

// Dynamic palette colors that adapt automatically across all screens
val EmeraldHeaderStart: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.headerStart

val EmeraldHeaderEnd: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.headerEnd

val EmeraldBannerBorder: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.headerStart

val NightGreenBackground: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.background

val CardBackgroundUnselected: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.cardBackgroundUnselected

val CardBackgroundSelected: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.cardBackgroundSelected

val CardBorderUnselected: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.cardBorderUnselected

val CardBorderSelected: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.cardBorderSelected

val GoldAccent: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.cardBorderSelected

val GoldAccentDark = Color(0xFFC78F1A)

val MintText: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.subText

val SageText = Color(0xFF90B5A7)
val SoftWhiteText = Color(0xFFFFFFFF)

val ButtonGreen: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.primaryButton

val ButtonGreenPressed: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.primaryButton

val RadioUnselectedBorder: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.cardBorderUnselected

val StepperInactiveBg: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.stepperInactiveBg

val StepperInactiveText: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.stepperInactiveText

val LanguageBadgeBg: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.languageBadgeBg

val LanguageBadgeBorder: Color
  @Composable
  @ReadOnlyComposable
  get() = AppTheme.colors.languageBadgeBorder

