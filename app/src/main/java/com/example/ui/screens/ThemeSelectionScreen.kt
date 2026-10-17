package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.DisplayMode
import com.example.model.IslamicColorPalette
import com.example.ui.components.OnboardingHeader
import com.example.ui.theme.SageText
import com.example.ui.theme.SoftWhiteText

@Composable
fun ThemeSelectionScreen(
  currentStep: Int = 3,
  totalSteps: Int = 6,
  language: AppLanguage = AppLanguage.BANGLA,
  selectedPalette: IslamicColorPalette,
  onPaletteSelected: (IslamicColorPalette) -> Unit,
  selectedDisplayMode: DisplayMode,
  onDisplayModeSelected: (DisplayMode) -> Unit,
  onBack: () -> Unit,
  onNext: () -> Unit,
  onLanguageBadgeClick: () -> Unit = {}
) {
  val navBarPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
  val themeColors = selectedPalette.colors

  val screenBg by animateColorAsState(
    targetValue = themeColors.background,
    animationSpec = tween(280),
    label = "screenBg"
  )

  val noticeCardBg by animateColorAsState(
    targetValue = themeColors.noticeCardBg,
    animationSpec = tween(280),
    label = "noticeBg"
  )

  val noticeCardBorder by animateColorAsState(
    targetValue = themeColors.noticeCardBorder,
    animationSpec = tween(280),
    label = "noticeBorder"
  )

  val noticeIconTint by animateColorAsState(
    targetValue = themeColors.noticeIconTint,
    animationSpec = tween(280),
    label = "noticeIcon"
  )

  val noticeText by animateColorAsState(
    targetValue = themeColors.noticeText,
    animationSpec = tween(280),
    label = "noticeText"
  )

  val primaryButtonBg by animateColorAsState(
    targetValue = themeColors.primaryButton,
    animationSpec = tween(280),
    label = "primaryButtonBg"
  )

  val secondaryButtonBg by animateColorAsState(
    targetValue = themeColors.secondaryButtonBg,
    animationSpec = tween(280),
    label = "secondaryButtonBg"
  )

  val secondaryButtonBorder by animateColorAsState(
    targetValue = themeColors.secondaryButtonBorder,
    animationSpec = tween(280),
    label = "secondaryButtonBorder"
  )

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(screenBg)
      .testTag("theme_selection_screen")
  ) {
    Column(modifier = Modifier.fillMaxSize()) {
      // Top Curved Islamic Header matching App 3rd Screen.jpeg
      OnboardingHeader(
        currentStep = currentStep,
        totalSteps = totalSteps,
        language = language,
        title = if (language == AppLanguage.BANGLA) "অ্যাপ থিম" else "App Theme",
        subtitle = if (language == AppLanguage.BANGLA)
          "আপনার পছন্দমতো অ্যাপের থিম নির্বাচন করুন"
        else
          "Select app theme according to your preference",
        onLanguageBadgeClick = onLanguageBadgeClick
      )

      // Scrollable content area
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .verticalScroll(rememberScrollState())
          .padding(horizontal = 16.dp, vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Section 1: ইসলামিক কালার প্যালেট
        Text(
          text = if (language == AppLanguage.BANGLA) "ইসলামিক কালার প্যালেট" else "Islamic Color Palette",
          fontSize = 15.sp,
          fontWeight = FontWeight.SemiBold,
          color = SoftWhiteText,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Row 1: 4 palettes
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          IslamicColorPalette.row1.forEach { palette ->
            ColorPaletteCard(
              palette = palette,
              currentThemePalette = selectedPalette,
              isSelected = palette == selectedPalette,
              onClick = { onPaletteSelected(palette) },
              modifier = Modifier.weight(1f)
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Row 2: 3 palettes + 1 blank slot for consistent 4-column grid
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          IslamicColorPalette.row2.forEach { palette ->
            ColorPaletteCard(
              palette = palette,
              currentThemePalette = selectedPalette,
              isSelected = palette == selectedPalette,
              onClick = { onPaletteSelected(palette) },
              modifier = Modifier.weight(1f)
            )
          }
          // Fourth slot placeholder to match the layout in App 3rd Screen.jpeg
          Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(26.dp))

        // Section 2: ডিসপ্লে মোড
        Text(
          text = if (language == AppLanguage.BANGLA) "ডিসপ্লে মোড" else "Display Mode",
          fontSize = 15.sp,
          fontWeight = FontWeight.SemiBold,
          color = SoftWhiteText,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 3 Display Modes row: ডিভাইস ডিফল্ট, লাইট মোড, নাইট মোড
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          DisplayModeCard(
            title = if (language == AppLanguage.BANGLA) "ডিভাইস ডিফল্ট" else "Device Default",
            icon = Icons.Default.BrightnessAuto,
            currentThemePalette = selectedPalette,
            isSelected = selectedDisplayMode == DisplayMode.DEVICE_DEFAULT,
            onClick = { onDisplayModeSelected(DisplayMode.DEVICE_DEFAULT) },
            modifier = Modifier.weight(1f)
          )

          DisplayModeCard(
            title = if (language == AppLanguage.BANGLA) "লাইট মোড" else "Light Mode",
            icon = Icons.Default.WbSunny,
            currentThemePalette = selectedPalette,
            isSelected = selectedDisplayMode == DisplayMode.LIGHT,
            onClick = { onDisplayModeSelected(DisplayMode.LIGHT) },
            modifier = Modifier.weight(1f)
          )

          DisplayModeCard(
            title = if (language == AppLanguage.BANGLA) "নাইট মোড" else "Night Mode",
            icon = Icons.Default.NightlightRound,
            currentThemePalette = selectedPalette,
            isSelected = selectedDisplayMode == DisplayMode.NIGHT,
            onClick = { onDisplayModeSelected(DisplayMode.NIGHT) },
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Section 3: Information notice card matching App 3rd Screen.jpeg
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          border = BorderStroke(1.dp, noticeCardBorder),
          colors = CardDefaults.cardColors(containerColor = noticeCardBg)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.Top
          ) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = null,
              tint = noticeIconTint,
              modifier = Modifier
                .size(18.dp)
                .padding(top = 1.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
              text = if (language == AppLanguage.BANGLA)
                "আপনি পরবর্তীতে অ্যাপের সেটিংস স্ক্রিন থেকে যেকোনো সময় থিম পরিবর্তন করতে পারবেন।"
              else
                "You can change the theme at any time later from the app's settings screen.",
              fontSize = 12.5.sp,
              color = noticeText,
              lineHeight = 17.sp
            )
          }
        }
      }

      // Bottom Bar Action Buttons
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(
            start = 16.dp,
            end = 16.dp,
            bottom = navBarPadding + 16.dp,
            top = 8.dp
          ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        // "← পেছনে" Button
        OutlinedButton(
          onClick = onBack,
          modifier = Modifier
            .weight(0.36f)
            .height(50.dp)
            .testTag("theme_back_button"),
          shape = RoundedCornerShape(25.dp),
          border = BorderStroke(1.dp, secondaryButtonBorder),
          colors = ButtonDefaults.outlinedButtonColors(
            containerColor = secondaryButtonBg,
            contentColor = SoftWhiteText
          )
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = SoftWhiteText,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (language == AppLanguage.BANGLA) "পেছনে" else "Back",
              fontSize = 14.5.sp,
              fontWeight = FontWeight.Medium,
              color = SoftWhiteText
            )
          }
        }

        // "পরবর্তী ধাপ ➔" Button
        Button(
          onClick = onNext,
          modifier = Modifier
            .weight(0.64f)
            .height(50.dp)
            .testTag("theme_next_button"),
          shape = RoundedCornerShape(25.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = primaryButtonBg,
            contentColor = SoftWhiteText
          )
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Text(
              text = if (language == AppLanguage.BANGLA) "পরবর্তী ধাপ" else "Next Step",
              fontSize = 15.5.sp,
              fontWeight = FontWeight.SemiBold,
              color = SoftWhiteText
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = null,
              tint = SoftWhiteText,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }
  }
}

@Composable
fun ColorPaletteCard(
  palette: IslamicColorPalette,
  currentThemePalette: IslamicColorPalette,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val borderStroke = if (isSelected) {
    BorderStroke(1.5.dp, palette.colors.cardBorderSelected)
  } else {
    BorderStroke(1.dp, currentThemePalette.colors.cardBorderUnselected)
  }

  val backgroundColor by animateColorAsState(
    targetValue = if (isSelected) palette.colors.cardBackgroundSelected else currentThemePalette.colors.cardBackgroundUnselected,
    animationSpec = tween(220),
    label = "paletteCardBg"
  )

  val textColor by animateColorAsState(
    targetValue = if (isSelected) palette.colors.activeText else SageText,
    animationSpec = tween(220),
    label = "paletteTextColor"
  )

  Card(
    modifier = modifier
      .height(78.dp)
      .clip(RoundedCornerShape(14.dp))
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
      )
      .testTag("palette_card_${palette.id}"),
    shape = RoundedCornerShape(14.dp),
    border = borderStroke,
    colors = CardDefaults.cardColors(containerColor = backgroundColor)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 4.dp, vertical = 8.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      // Row of two color circle previews
      Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Circle 1: Primary Color (with subtle white rim)
        Surface(
          modifier = Modifier.size(13.dp),
          shape = CircleShape,
          color = palette.primaryColor,
          border = BorderStroke(0.8.dp, Color.White.copy(alpha = 0.8f))
        ) {}

        Spacer(modifier = Modifier.width(4.5.dp))

        // Circle 2: Secondary Accent Color
        Surface(
          modifier = Modifier.size(13.dp),
          shape = CircleShape,
          color = palette.secondaryColor
        ) {}
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Display name in Bengali
      Text(
        text = palette.displayName,
        fontSize = 11.5.sp,
        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
        color = textColor,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}

@Composable
fun DisplayModeCard(
  title: String,
  icon: ImageVector,
  currentThemePalette: IslamicColorPalette,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val borderStroke = if (isSelected) {
    BorderStroke(1.5.dp, currentThemePalette.colors.cardBorderSelected)
  } else {
    BorderStroke(1.dp, currentThemePalette.colors.cardBorderUnselected)
  }

  val backgroundColor by animateColorAsState(
    targetValue = if (isSelected) currentThemePalette.colors.cardBackgroundSelected else currentThemePalette.colors.cardBackgroundUnselected,
    animationSpec = tween(220),
    label = "displayModeBg"
  )

  val contentColor by animateColorAsState(
    targetValue = if (isSelected) currentThemePalette.colors.activeText else currentThemePalette.colors.subText,
    animationSpec = tween(220),
    label = "displayModeContent"
  )

  Card(
    modifier = modifier
      .height(50.dp)
      .clip(RoundedCornerShape(14.dp))
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
      )
      .testTag("display_mode_${title}"),
    shape = RoundedCornerShape(14.dp),
    border = borderStroke,
    colors = CardDefaults.cardColors(containerColor = backgroundColor)
  ) {
    Row(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = contentColor,
        modifier = Modifier.size(17.dp)
      )

      Spacer(modifier = Modifier.width(5.dp))

      Text(
        text = title,
        fontSize = 12.sp,
        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
        color = contentColor,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}

