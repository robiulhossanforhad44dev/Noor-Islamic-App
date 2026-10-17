package com.example.ui.components

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.ui.theme.AppTheme
import com.example.ui.theme.SoftWhiteText

@Composable
fun OnboardingHeader(
  currentStep: Int,
  totalSteps: Int = 6,
  language: AppLanguage,
  title: String,
  subtitle: String,
  onLanguageBadgeClick: () -> Unit = {}
) {
  val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
  val themeColors = AppTheme.colors

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
            colors = listOf(themeColors.headerStart, themeColors.headerEnd)
          )
        )
        .padding(
          top = statusBarPadding + 10.dp,
          bottom = 24.dp,
          start = 20.dp,
          end = 20.dp
        )
    ) {
      // Mosque silhouette watermark in the right background
      MosqueWatermark(
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .width(220.dp)
          .height(125.dp)
          .padding(end = 4.dp, bottom = 4.dp),
        tint = themeColors.watermarkTint
      )

      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Top Row: Stepper and Language Badge
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          HeaderStepper(
            currentStep = currentStep,
            totalSteps = totalSteps,
            language = language
          )

          Surface(
            modifier = Modifier
              .clip(RoundedCornerShape(12.dp))
              .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onLanguageBadgeClick
              ),
            shape = RoundedCornerShape(12.dp),
            color = themeColors.languageBadgeBg,
            border = BorderStroke(1.dp, themeColors.languageBadgeBorder)
          ) {
            Text(
              text = language.badgeText,
              color = SoftWhiteText,
              fontSize = 13.sp,
              fontWeight = FontWeight.SemiBold,
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
          text = title,
          fontSize = 23.sp,
          fontWeight = FontWeight.Bold,
          color = SoftWhiteText,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(5.dp))

        // Subtitle
        Text(
          text = subtitle,
          fontSize = 13.sp,
          fontWeight = FontWeight.Normal,
          color = themeColors.subText,
          textAlign = TextAlign.Center
        )
      }
    }
  }
}

@Composable
fun HeaderStepper(
  currentStep: Int,
  totalSteps: Int,
  language: AppLanguage,
  modifier: Modifier = Modifier
) {
  val themeColors = AppTheme.colors

  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    for (i in 1..totalSteps) {
      val isCurrent = i == currentStep
      val isCompleted = i < currentStep

      val circleBgColor by animateColorAsState(
        targetValue = when {
          isCurrent || isCompleted -> themeColors.cardBorderSelected
          else -> themeColors.stepperInactiveBg
        },
        animationSpec = tween(300),
        label = "stepBg"
      )

      val circleTextColor by animateColorAsState(
        targetValue = when {
          isCurrent || isCompleted -> Color(0xFF09241A)
          else -> themeColors.stepperInactiveText
        },
        animationSpec = tween(300),
        label = "stepText"
      )

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

      if (i < totalSteps) {
        val lineColor by animateColorAsState(
          targetValue = if (i < currentStep) themeColors.cardBorderSelected else themeColors.stepperLineInactive,
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

