package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.ui.components.OnboardingHeader
import com.example.ui.theme.ButtonGreen
import com.example.ui.theme.CardBackgroundUnselected
import com.example.ui.theme.CardBorderUnselected
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MintText
import com.example.ui.theme.NightGreenBackground
import com.example.ui.theme.SoftWhiteText

data class PrayerAlertItem(
  val id: String,
  val nameBn: String,
  val nameEn: String,
  val nameUr: String,
  val nameAr: String
) {
  fun getName(language: AppLanguage): String {
    return when (language) {
      AppLanguage.BANGLA -> nameBn
      AppLanguage.ENGLISH -> nameEn
      AppLanguage.URDU -> nameUr
      AppLanguage.ARABIC -> nameAr
    }
  }
}

val defaultPrayerAlerts = listOf(
  PrayerAlertItem("fajr", "ফজর", "Fajr", "فجر", "الفجر"),
  PrayerAlertItem("dhuhr", "যোহর", "Dhuhr", "ظہر", "الظهر"),
  PrayerAlertItem("asr", "আসর", "Asr", "عصر", "العصر"),
  PrayerAlertItem("maghrib", "মাগরিব", "Maghrib", "مغرب", "المغرب"),
  PrayerAlertItem("isha", "ইশা", "Isha", "عشاء", "العشاء")
)

@Composable
fun AdhanAlertScreen(
  currentStep: Int = 5,
  totalSteps: Int = 6,
  language: AppLanguage = AppLanguage.BANGLA,
  prayerToggles: Map<String, Boolean>,
  onPrayerToggleChange: (String, Boolean) -> Unit,
  onToggleAll: (Boolean) -> Unit,
  onBack: () -> Unit,
  onNext: () -> Unit,
  onLanguageBadgeClick: () -> Unit = {}
) {
  val navBarPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

  val allOn = defaultPrayerAlerts.all { prayerToggles[it.id] == true }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(NightGreenBackground)
      .testTag("adhan_alert_screen")
  ) {
    Column(modifier = Modifier.fillMaxSize()) {
      // Top Curved Islamic Header matching App 5th Screen.jpeg
      OnboardingHeader(
        currentStep = currentStep,
        totalSteps = totalSteps,
        language = language,
        title = when (language) {
          AppLanguage.BANGLA -> "আজান ও এলার্ট"
          AppLanguage.ENGLISH -> "Adhan & Alerts"
          AppLanguage.URDU -> "اذان اور الرٹس"
          AppLanguage.ARABIC -> "الأذان والتنبيهات"
        },
        subtitle = when (language) {
          AppLanguage.BANGLA -> "নামাজের ওয়াক্তে আজান বা নোটিফিকেশন পেতে চান কি না তা নির্বাচন করুন"
          AppLanguage.ENGLISH -> "Choose whether to receive Adhan or notifications at prayer times"
          AppLanguage.URDU -> "نماز کے اوقات میں اذان یا نوٹیفکیشن حاصل کرنے کے لیے منتخب کریں"
          AppLanguage.ARABIC -> "اختر ما إذا كنت ترغب في تلقي الأذان أو الإشعارات في أوقات الصلاة"
        },
        onLanguageBadgeClick = onLanguageBadgeClick
      )

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .verticalScroll(rememberScrollState())
          .padding(horizontal = 16.dp, vertical = 18.dp)
      ) {
        // Section Header Row: "ওয়াক্ত ভিত্তিক আজান নোটিফিকেশন:" and "সব চালু"
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 6.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = when (language) {
              AppLanguage.BANGLA -> "ওয়াক্ত ভিত্তিক আজান নোটিফিকেশন:"
              AppLanguage.ENGLISH -> "Prayer-based Adhan Alerts:"
              AppLanguage.URDU -> "وقت کے مطابق اذان نوٹیفکیشن:"
              AppLanguage.ARABIC -> "تنبيهات الأذان حسب الوقت:"
            },
            fontSize = 13.5.sp,
            fontWeight = FontWeight.Medium,
            color = MintText
          )

          // "সব চালু" / "সব বন্ধ" Toggle Button
          Text(
            text = if (allOn) {
              when (language) {
                AppLanguage.BANGLA -> "সব চালু"
                AppLanguage.ENGLISH -> "All Enabled"
                AppLanguage.URDU -> "سب فعال"
                AppLanguage.ARABIC -> "الكل مفعل"
              }
            } else {
              when (language) {
                AppLanguage.BANGLA -> "সব চালু করুন"
                AppLanguage.ENGLISH -> "Enable All"
                AppLanguage.URDU -> "سب فعال کریں"
                AppLanguage.ARABIC -> "تفعيل الكل"
              }
            },
            fontSize = 13.5.sp,
            fontWeight = FontWeight.SemiBold,
            color = GoldAccent,
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .clickable { onToggleAll(!allOn) }
              .padding(horizontal = 4.dp, vertical = 2.dp)
              .testTag("toggle_all_prayers_button")
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Unified Card with 5 Prayer Rows matching App 5th Screen.jpeg
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          border = BorderStroke(1.dp, CardBorderUnselected),
          colors = CardDefaults.cardColors(containerColor = CardBackgroundUnselected)
        ) {
          Column(modifier = Modifier.fillMaxWidth()) {
            defaultPrayerAlerts.forEachIndexed { index, prayer ->
              val isEnabled = prayerToggles[prayer.id] != false

              PrayerAlertRow(
                prayer = prayer,
                language = language,
                isEnabled = isEnabled,
                onToggle = { onPrayerToggleChange(prayer.id, !isEnabled) }
              )

              if (index < defaultPrayerAlerts.lastIndex) {
                HorizontalDivider(
                  color = Color(0xFF0F3227),
                  thickness = 0.8.dp,
                  modifier = Modifier.padding(horizontal = 14.dp)
                )
              }
            }
          }
        }
      }

      // Bottom Bar Navigation ("← পেছনে" and "পরবর্তী ধাপ ➔")
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
        OutlinedButton(
          onClick = onBack,
          modifier = Modifier
            .weight(0.36f)
            .height(50.dp)
            .testTag("adhan_back_button"),
          shape = RoundedCornerShape(25.dp),
          border = BorderStroke(1.dp, CardBorderUnselected),
          colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color(0xFF08221A),
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

        Button(
          onClick = onNext,
          modifier = Modifier
            .weight(0.64f)
            .height(50.dp)
            .testTag("adhan_next_button"),
          shape = RoundedCornerShape(25.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = ButtonGreen,
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
fun PrayerAlertRow(
  prayer: PrayerAlertItem,
  language: AppLanguage,
  isEnabled: Boolean,
  onToggle: () -> Unit
) {
  // Highlight animations for text, icon, and switch matching App 5th Screen On.jpeg
  val animatedTextColor by animateColorAsState(
    targetValue = if (isEnabled) Color.White else Color(0xFF6E9484),
    animationSpec = tween(durationMillis = 220),
    label = "textColor"
  )
  val animatedIconTint by animateColorAsState(
    targetValue = if (isEnabled) Color(0xFF4ADE80) else Color(0xFF446557),
    animationSpec = tween(durationMillis = 220),
    label = "iconTint"
  )
  val animatedDiscBg by animateColorAsState(
    targetValue = if (isEnabled) Color(0xFF0F3D2E) else Color(0xFF072118),
    animationSpec = tween(durationMillis = 220),
    label = "discBg"
  )
  val animatedDiscBorder by animateColorAsState(
    targetValue = if (isEnabled) Color(0xFF267D5E) else Color(0xFF103326),
    animationSpec = tween(durationMillis = 220),
    label = "discBorder"
  )
  val animatedRowBg by animateColorAsState(
    targetValue = if (isEnabled) Color(0xFF0D3226).copy(alpha = 0.45f) else Color.Transparent,
    animationSpec = tween(durationMillis = 220),
    label = "rowBg"
  )

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(animatedRowBg)
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onToggle
      )
      .padding(horizontal = 16.dp, vertical = 15.dp)
      .testTag("prayer_row_${prayer.id}"),
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Leading circular dark green disc with Bell Icon
    Box(
      modifier = Modifier
        .size(38.dp)
        .clip(CircleShape)
        .background(animatedDiscBg)
        .border(
          BorderStroke(1.dp, animatedDiscBorder),
          CircleShape
        ),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = if (isEnabled) Icons.Default.Notifications else Icons.Default.NotificationsOff,
        contentDescription = null,
        tint = animatedIconTint,
        modifier = Modifier.size(20.dp)
      )
    }

    Spacer(modifier = Modifier.width(16.dp))

    // Prayer Name (ফজর, যোহর, আসর, মাগরিব, ইশা) - Highlighted when ON
    Text(
      text = prayer.getName(language),
      fontSize = if (isEnabled) 16.sp else 15.5.sp,
      fontWeight = if (isEnabled) FontWeight.Bold else FontWeight.Normal,
      color = animatedTextColor,
      modifier = Modifier.weight(1f)
    )

    // Custom Switch Toggle matching App 5th Screen On.jpeg (Green track and bright thumb when ON)
    CustomIslamicSwitch(
      checked = isEnabled,
      onCheckedChange = { onToggle() }
    )
  }
}

@Composable
fun CustomIslamicSwitch(
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  // Track dimensions: 48dp width, 26dp height, 20dp thumb
  val trackWidth = 48.dp
  val trackHeight = 26.dp
  val thumbSize = 20.dp

  val thumbOffsetAnim by animateFloatAsState(
    targetValue = if (checked) 22f else 0f,
    animationSpec = tween(durationMillis = 220),
    label = "switchThumbOffset"
  )

  // Switch Track & Border colors: Vibrant green when ON, subdued dark teal when OFF
  val trackColorAnim by animateColorAsState(
    targetValue = if (checked) Color(0xFF22C55E) else Color(0xFF072118),
    animationSpec = tween(durationMillis = 220),
    label = "switchTrackColor"
  )

  val trackBorderColorAnim by animateColorAsState(
    targetValue = if (checked) Color(0xFF34D399) else Color(0xFF153E30),
    animationSpec = tween(durationMillis = 220),
    label = "switchBorderColor"
  )

  val thumbColorAnim by animateColorAsState(
    targetValue = if (checked) Color.White else Color(0xFF9EBCB0),
    animationSpec = tween(durationMillis = 220),
    label = "switchThumbColor"
  )

  Box(
    modifier = Modifier
      .size(width = trackWidth, height = trackHeight)
      .clip(RoundedCornerShape(13.dp))
      .background(trackColorAnim)
      .border(
        BorderStroke(1.2.dp, trackBorderColorAnim),
        RoundedCornerShape(13.dp)
      )
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = { onCheckedChange(!checked) }
      )
      .padding(3.dp),
    contentAlignment = Alignment.CenterStart
  ) {
    // Thumb: Pure white circle when ON, soft off-white when OFF
    Box(
      modifier = Modifier
        .offset { IntOffset(x = thumbOffsetAnim.dp.roundToPx(), y = 0) }
        .size(thumbSize)
        .clip(CircleShape)
        .background(thumbColorAnim)
    )
  }
}
