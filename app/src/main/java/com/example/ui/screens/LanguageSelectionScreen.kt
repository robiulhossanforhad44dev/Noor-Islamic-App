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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.ui.components.FlagEmblem
import com.example.ui.components.MosqueWatermark
import com.example.ui.theme.ButtonGreen
import com.example.ui.theme.CardBackgroundSelected
import com.example.ui.theme.CardBackgroundUnselected
import com.example.ui.theme.CardBorderSelected
import com.example.ui.theme.CardBorderUnselected
import com.example.ui.theme.EmeraldBannerBorder
import com.example.ui.theme.EmeraldHeaderEnd
import com.example.ui.theme.EmeraldHeaderStart
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.LanguageBadgeBg
import com.example.ui.theme.LanguageBadgeBorder
import com.example.ui.theme.MintText
import com.example.ui.theme.NightGreenBackground
import com.example.ui.theme.RadioUnselectedBorder
import com.example.ui.theme.SageText
import com.example.ui.theme.SoftWhiteText
import com.example.ui.theme.StepperInactiveBg
import com.example.ui.theme.StepperInactiveText

@Composable
fun LanguageSelectionScreen(
  currentStep: Int = 1,
  totalSteps: Int = 6,
  selectedLanguage: AppLanguage,
  onLanguageSelected: (AppLanguage) -> Unit,
  onNextStep: () -> Unit,
  onOpenLanguageMenu: () -> Unit = {}
) {
  val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
  val navBarPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(NightGreenBackground)
      .testTag("language_selection_screen")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
    ) {
      // Top Curved Emerald Header
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
        color = Color.Transparent
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .background(
              brush = Brush.verticalGradient(
                colors = listOf(
                  EmeraldHeaderStart,
                  EmeraldHeaderEnd
                )
              )
            )
            .padding(
              top = statusBarPadding + 12.dp,
              bottom = 28.dp,
              start = 20.dp,
              end = 20.dp
            )
        ) {
          // Mosque silhouette watermark placed in the right half background
          MosqueWatermark(
            modifier = Modifier
              .align(Alignment.BottomEnd)
              .width(220.dp)
              .height(130.dp)
              .padding(end = 4.dp, bottom = 4.dp),
            tint = Color(0xFF67CFA9).copy(alpha = 0.22f)
          )

          Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            // Top Row: Stepper (1 to 6) and Language Badge ("বাং")
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              // Stepper component
              OnboardingStepper(
                currentStep = currentStep,
                totalSteps = totalSteps,
                language = selectedLanguage
              )

              // Language Badge Pill (e.g. "বাং")
              Surface(
                modifier = Modifier
                  .clip(RoundedCornerShape(12.dp))
                  .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onOpenLanguageMenu
                  )
                  .testTag("header_language_badge"),
                shape = RoundedCornerShape(12.dp),
                color = LanguageBadgeBg,
                border = BorderStroke(1.dp, LanguageBadgeBorder)
              ) {
                Text(
                  text = selectedLanguage.badgeText,
                  color = SoftWhiteText,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.SemiBold,
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Main Title: "অ্যাপের ভাষা"
            Text(
              text = selectedLanguage.headerTitle,
              fontSize = 24.sp,
              fontWeight = FontWeight.Bold,
              color = SoftWhiteText,
              textAlign = TextAlign.Center,
              modifier = Modifier.testTag("header_title_text")
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Subtitle: "আপনার পছন্দের ভাষা নির্বাচন করুন"
            Text(
              text = selectedLanguage.headerSubtitle,
              fontSize = 13.sp,
              fontWeight = FontWeight.Normal,
              color = MintText,
              textAlign = TextAlign.Center,
              modifier = Modifier.testTag("header_subtitle_text")
            )
          }
        }
      }

      // Middle: Language Cards List
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .verticalScroll(rememberScrollState())
          .padding(horizontal = 20.dp, vertical = 22.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        AppLanguage.values().forEach { language ->
          val isSelected = language == selectedLanguage
          LanguageOptionCard(
            language = language,
            isSelected = isSelected,
            onClick = { onLanguageSelected(language) }
          )
        }
      }

      // Bottom Action Button: "পরবর্তী ধাপ ➔"
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(
            start = 20.dp,
            end = 20.dp,
            bottom = navBarPadding + 18.dp,
            top = 8.dp
          )
      ) {
        Button(
          onClick = onNextStep,
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .testTag("next_step_button"),
          shape = RoundedCornerShape(27.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = ButtonGreen,
            contentColor = SoftWhiteText
          ),
          elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            Text(
              text = selectedLanguage.nextStepButton,
              fontSize = 16.sp,
              fontWeight = FontWeight.SemiBold,
              color = SoftWhiteText
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = "Forward",
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
fun OnboardingStepper(
  currentStep: Int,
  totalSteps: Int,
  language: AppLanguage,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    for (i in 1..totalSteps) {
      val isCurrent = i == currentStep
      val isCompleted = i < currentStep

      val circleBgColor by animateColorAsState(
        targetValue = when {
          isCurrent || isCompleted -> GoldAccent
          else -> StepperInactiveBg
        },
        animationSpec = tween(300),
        label = "stepBg"
      )

      val circleTextColor by animateColorAsState(
        targetValue = when {
          isCurrent || isCompleted -> Color(0xFF09241A)
          else -> StepperInactiveText
        },
        animationSpec = tween(300),
        label = "stepText"
      )

      // Step circle
      Box(
        modifier = Modifier
          .size(24.dp)
          .clip(CircleShape)
          .background(circleBgColor),
        contentAlignment = Alignment.Center
      ) {
        if (isCompleted) {
          Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Completed",
            tint = Color(0xFF09241A),
            modifier = Modifier.size(14.dp)
          )
        } else {
          Text(
            text = language.getStepNumeral(i),
            fontSize = 11.5.sp,
            fontWeight = FontWeight.Bold,
            color = circleTextColor,
            textAlign = TextAlign.Center
          )
        }
      }

      // Connecting line between steps
      if (i < totalSteps) {
        val lineColor by animateColorAsState(
          targetValue = if (i < currentStep) GoldAccent else Color(0xFF1E614B),
          animationSpec = tween(300),
          label = "lineColor"
        )
        Box(
          modifier = Modifier
            .width(8.dp)
            .height(2.dp)
            .background(lineColor)
        )
      }
    }
  }
}

@Composable
fun LanguageOptionCard(
  language: AppLanguage,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  val borderStroke = if (isSelected) {
    BorderStroke(1.5.dp, CardBorderSelected)
  } else {
    BorderStroke(1.dp, CardBorderUnselected)
  }

  val backgroundColor by animateColorAsState(
    targetValue = if (isSelected) CardBackgroundSelected else CardBackgroundUnselected,
    animationSpec = tween(250),
    label = "cardBg"
  )

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
      )
      .testTag("language_card_${language.code}"),
    shape = RoundedCornerShape(16.dp),
    border = borderStroke,
    colors = CardDefaults.cardColors(containerColor = backgroundColor)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Flag emblem inside metallic bevel ring
      FlagEmblem(
        language = language,
        size = 46.dp
      )

      Spacer(modifier = Modifier.width(16.dp))

      // Language names
      Column(
        modifier = Modifier.weight(1f)
      ) {
        Text(
          text = language.nativeName,
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = SoftWhiteText
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = language.secondaryName,
          fontSize = 13.5.sp,
          fontWeight = FontWeight.Normal,
          color = SageText
        )
      }

      // Trailing Selection Indicator
      if (isSelected) {
        // Gold filled circle with dark checkmark
        Box(
          modifier = Modifier
            .size(22.dp)
            .clip(CircleShape)
            .background(GoldAccent),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Selected",
            tint = Color(0xFF09251D),
            modifier = Modifier.size(15.dp)
          )
        }
      } else {
        // Unselected circular radio outline
        Box(
          modifier = Modifier
            .size(22.dp)
            .clip(CircleShape)
            .background(Color.Transparent)
        ) {
          Surface(
            modifier = Modifier.size(22.dp),
            shape = CircleShape,
            color = Color.Transparent,
            border = BorderStroke(1.5.dp, RadioUnselectedBorder)
          ) {}
        }
      }
    }
  }
}
