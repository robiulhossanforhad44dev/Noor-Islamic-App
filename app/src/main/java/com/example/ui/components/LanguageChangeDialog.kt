package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.AppLanguage
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MintText
import com.example.ui.theme.SoftWhiteText
import kotlinx.coroutines.delay

@Composable
fun LanguageChangeDialog(
  targetLanguage: AppLanguage,
  onDismiss: () -> Unit
) {
  // Automatically dismiss after animation duration (~1.8 seconds)
  LaunchedEffect(targetLanguage) {
    delay(1800)
    onDismiss()
  }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(
      dismissOnBackPress = false,
      dismissOnClickOutside = false
    )
  ) {
    Card(
      modifier = Modifier
        .fillMaxWidth(0.92f)
        .testTag("language_change_dialog"),
      shape = RoundedCornerShape(28.dp),
      border = BorderStroke(1.2.dp, GoldAccent.copy(alpha = 0.8f)),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF092219))
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 36.dp, horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Animated Spinner with Flag Emblem in center
        Box(
          modifier = Modifier.size(108.dp),
          contentAlignment = Alignment.Center
        ) {
          val infiniteTransition = rememberInfiniteTransition(label = "spinTransition")
          val rotationAngle by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
              animation = tween(1200, easing = LinearEasing),
              repeatMode = RepeatMode.Restart
            ),
            label = "spinnerAngle"
          )

          val glowingArcColor = GoldAccent

          Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 3.5.dp.toPx()
            val diameter = size.minDimension - strokeWidth
            val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
            val arcSize = Size(diameter, diameter)

            // Dark circular track
            drawArc(
              color = Color(0xFF103628),
              startAngle = 0f,
              sweepAngle = 360f,
              useCenter = false,
              topLeft = topLeft,
              size = arcSize,
              style = Stroke(width = strokeWidth)
            )

            // Glowing Golden arc
            drawArc(
              color = glowingArcColor,
              startAngle = rotationAngle,
              sweepAngle = 110f,
              useCenter = false,
              topLeft = topLeft,
              size = arcSize,
              style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
          }

          // Center flag emblem in metallic bezel
          FlagEmblem(
            language = targetLanguage,
            size = 54.dp
          )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Language Pill Badge (e.g. "• বাংলা")
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = Color(0xFF0E2F23),
          border = BorderStroke(1.dp, Color(0xFF205642))
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(Color(0xFF1DA57A))
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(
              text = targetLanguage.nativeName,
              fontSize = 13.5.sp,
              fontWeight = FontWeight.SemiBold,
              color = GoldAccent
            )
          }
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Title: "ভাষা পরিবর্তিত হচ্ছে..." / "Changing Language..."
        val titleText = when (targetLanguage) {
          AppLanguage.BANGLA -> "ভাষা পরিবর্তিত হচ্ছে..."
          AppLanguage.ENGLISH -> "Changing Language..."
          AppLanguage.URDU -> "زبان تبدیل ہو رہی ہے..."
          AppLanguage.ARABIC -> "جاري تغيير اللغة..."
        }

        Text(
          text = titleText,
          fontSize = 19.sp,
          fontWeight = FontWeight.Bold,
          color = SoftWhiteText,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Subtitle: "অনুগ্রহ করে কিছুক্ষণ অপেক্ষা করুন..." / "Please wait a moment..."
        val subtitleText = when (targetLanguage) {
          AppLanguage.BANGLA -> "অনুগ্রহ করে কিছুক্ষণ অপেক্ষা করুন..."
          AppLanguage.ENGLISH -> "Please wait a moment..."
          AppLanguage.URDU -> "براہ کرم تھوڑا انتظار فرمائیں..."
          AppLanguage.ARABIC -> "يرجى الانتظار لحظة..."
        }

        Text(
          text = subtitleText,
          fontSize = 13.5.sp,
          fontWeight = FontWeight.Normal,
          color = MintText,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(22.dp))

        // Three animated bouncing / pulsing golden dots
        AnimatedPulsingDots()
      }
    }
  }
}

@Composable
fun AnimatedPulsingDots() {
  val infiniteTransition = rememberInfiniteTransition(label = "dotsTransition")

  val dot1Scale by infiniteTransition.animateFloat(
    initialValue = 0.5f,
    targetValue = 1.2f,
    animationSpec = infiniteRepeatable(
      animation = tween(600, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "dot1"
  )

  val dot2Scale by infiniteTransition.animateFloat(
    initialValue = 0.5f,
    targetValue = 1.2f,
    animationSpec = infiniteRepeatable(
      animation = tween(600, delayMillis = 200, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "dot2"
  )

  val dot3Scale by infiniteTransition.animateFloat(
    initialValue = 0.5f,
    targetValue = 1.2f,
    animationSpec = infiniteRepeatable(
      animation = tween(600, delayMillis = 400, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "dot3"
  )

  Row(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .size(7.dp)
        .scale(dot1Scale)
        .clip(CircleShape)
        .background(GoldAccent)
    )
    Box(
      modifier = Modifier
        .size(7.dp)
        .scale(dot2Scale)
        .clip(CircleShape)
        .background(GoldAccent)
    )
    Box(
      modifier = Modifier
        .size(7.dp)
        .scale(dot3Scale)
        .clip(CircleShape)
        .background(GoldAccent)
    )
  }
}
