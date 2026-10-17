package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.model.toBengaliDigits
import com.example.ui.components.MosqueWatermark
import com.example.ui.components.OnboardingHeader
import com.example.ui.theme.ButtonGreen
import com.example.ui.theme.CardBackgroundSelected
import com.example.ui.theme.CardBackgroundUnselected
import com.example.ui.theme.CardBorderSelected
import com.example.ui.theme.CardBorderUnselected
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MintText
import com.example.ui.theme.NightGreenBackground
import com.example.ui.theme.SageText
import com.example.ui.theme.SoftWhiteText

fun Int.toArabicDigits(): String {
  val arabicNumerals = charArrayOf('۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹')
  val isNegative = this < 0
  val absStr = kotlin.math.abs(this).toString()
  val converted = absStr.map { arabicNumerals[it - '0'] }.joinToString("")
  return if (isNegative) "-$converted" else converted
}

@Composable
fun HijriCalendarScreen(
  currentStep: Int = 6,
  totalSteps: Int = 6,
  language: AppLanguage = AppLanguage.BANGLA,
  hijriOffset: Int = 0,
  onOffsetChange: (Int) -> Unit = {},
  onBack: () -> Unit,
  onFinish: () -> Unit,
  onLanguageBadgeClick: () -> Unit = {}
) {
  val navBarPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

  // Base Hijri Date: 11 Rabi' al-Thani, 1446 AH
  val baseDay = 11
  val currentHijriDay = (baseDay + hijriOffset).coerceIn(1, 30)

  // Hijri Month & Year localized strings
  val formattedDate = when (language) {
    AppLanguage.BANGLA -> "${currentHijriDay.toBengaliDigits()} রবিউস সানি, ১৪৪৬ হিজরি"
    AppLanguage.ENGLISH -> "$currentHijriDay Rabi' al-Thani, 1446 AH"
    AppLanguage.URDU -> "${currentHijriDay.toArabicDigits()} ربیع الثانی، ۱۴۴۶ ہجری"
    AppLanguage.ARABIC -> "${currentHijriDay.toArabicDigits()} ربيع الثاني ١٤٤٦ هـ"
  }

  val gregorianDate = when (language) {
    AppLanguage.BANGLA -> "বুধবার, ১৪ অক্টোবর ২০২৬"
    AppLanguage.ENGLISH -> "Wednesday, 14 October 2026"
    AppLanguage.URDU -> "بدھ، ۱۴ اکتوبر ۲۰২৬"
    AppLanguage.ARABIC -> "الأربعاء، ١٤ أكتوبر ٢٠٢٦"
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(NightGreenBackground)
      .testTag("hijri_calendar_screen")
  ) {
    Column(modifier = Modifier.fillMaxSize()) {
      // Top Curved Islamic Header matching App 6th Screeen Hijiri Calender.jpeg
      OnboardingHeader(
        currentStep = currentStep,
        totalSteps = totalSteps,
        language = language,
        title = when (language) {
          AppLanguage.BANGLA -> "হিজরি ক্যালেন্ডার"
          AppLanguage.ENGLISH -> "Hijri Calendar"
          AppLanguage.URDU -> "ہجری کیلنڈر"
          AppLanguage.ARABIC -> "التقويم الهجري"
        },
        subtitle = when (language) {
          AppLanguage.BANGLA -> "চাঁদ দেখার ওপর ভিত্তি করে হিজরি তারিখ নির্ধারণ করুন।"
          AppLanguage.ENGLISH -> "Adjust Hijri date according to local moon sighting in your area."
          AppLanguage.URDU -> "اپنے علاقے میں چاند دیکھنے کے مطابق ہجری تاریخ سیٹ کریں۔"
          AppLanguage.ARABIC -> "تعديل التاريخ الهجري بناءً على رؤية الهلال في منطقتك."
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
        // 1. Prominent Today's Hijri Date Card
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("hijri_date_preview_card"),
          shape = RoundedCornerShape(18.dp),
          border = BorderStroke(1.2.dp, GoldAccent.copy(alpha = 0.7f)),
          colors = CardDefaults.cardColors(containerColor = CardBackgroundSelected)
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(18.dp)
          ) {
            // Subtle watermark
            MosqueWatermark(
              modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(110.dp),
              tint = Color(0xFF67CFA9).copy(alpha = 0.08f)
            )

            Column(
              modifier = Modifier.fillMaxWidth(),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              // Top badge row
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                // Crescent & Badge
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(28.dp)
                      .clip(CircleShape)
                      .background(Color(0xFF13503D)),
                    contentAlignment = Alignment.Center
                  ) {
                    Icon(
                      imageVector = Icons.Default.NightlightRound,
                      contentDescription = null,
                      tint = GoldAccent,
                      modifier = Modifier.size(16.dp)
                    )
                  }
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = when (language) {
                      AppLanguage.BANGLA -> "আজকের হিজরি তারিখ"
                      AppLanguage.ENGLISH -> "Today's Hijri Date"
                      AppLanguage.URDU -> "آج کی ہجری تاریخ"
                      AppLanguage.ARABIC -> "تاريخ اليوم الهجري"
                    },
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MintText
                  )
                }

                // Moon phase indicator
                Surface(
                  shape = RoundedCornerShape(12.dp),
                  color = Color(0xFF164D3B),
                  border = BorderStroke(1.dp, Color(0xFF2B7D64))
                ) {
                  Text(
                    text = when (language) {
                      AppLanguage.BANGLA -> "শুক্লপক্ষ"
                      AppLanguage.ENGLISH -> "Waxing"
                      AppLanguage.URDU -> "چاند کی چودھویں"
                      AppLanguage.ARABIC -> "الأيام البيض"
                    },
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Medium,
                    color = GoldAccent,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(14.dp))

              // Large Date Display
              Text(
                text = formattedDate,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = SoftWhiteText,
                textAlign = TextAlign.Center
              )

              Spacer(modifier = Modifier.height(4.dp))

              Text(
                text = gregorianDate,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Normal,
                color = MintText,
                textAlign = TextAlign.Center
              )

              Spacer(modifier = Modifier.height(14.dp))
              HorizontalDivider(
                color = Color(0xFF1D5A46),
                thickness = 1.dp
              )
              Spacer(modifier = Modifier.height(10.dp))

              // Status Summary row
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = when (language) {
                    AppLanguage.BANGLA -> "সমন্বয় অবস্থা:"
                    AppLanguage.ENGLISH -> "Adjustment:"
                    AppLanguage.URDU -> "ایڈجسٹمنٹ:"
                    AppLanguage.ARABIC -> "حالة التعديل:"
                  },
                  fontSize = 12.5.sp,
                  color = SageText
                )

                Text(
                  text = when {
                    hijriOffset == 0 -> when (language) {
                      AppLanguage.BANGLA -> "স্বাভাবিক (০ দিন)"
                      AppLanguage.ENGLISH -> "Normal (0 days)"
                      AppLanguage.URDU -> "نارمل (۰ دن)"
                      AppLanguage.ARABIC -> "طبيعي (٠ يوم)"
                    }
                    hijriOffset > 0 -> when (language) {
                      AppLanguage.BANGLA -> "+${hijriOffset.toBengaliDigits()} দিন এগিয়ে"
                      AppLanguage.ENGLISH -> "+$hijriOffset Day(s) Ahead"
                      AppLanguage.URDU -> "+${hijriOffset.toArabicDigits()} دن آگے"
                      AppLanguage.ARABIC -> "+${hijriOffset.toArabicDigits()} يوم متقدم"
                    }
                    else -> when (language) {
                      AppLanguage.BANGLA -> "${hijriOffset.toBengaliDigits()} দিন পিছিয়ে"
                      AppLanguage.ENGLISH -> "$hijriOffset Day(s) Behind"
                      AppLanguage.URDU -> "${hijriOffset.toArabicDigits()} دن پیچھے"
                      AppLanguage.ARABIC -> "${hijriOffset.toArabicDigits()} يوم متأخر"
                    }
                  },
                  fontSize = 12.5.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = if (hijriOffset == 0) MintText else GoldAccent
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Hijri Date Adjustment Card
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("hijri_offset_card"),
          shape = RoundedCornerShape(18.dp),
          border = BorderStroke(1.dp, CardBorderUnselected),
          colors = CardDefaults.cardColors(containerColor = CardBackgroundUnselected)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.fillMaxWidth()
            ) {
              Box(
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .background(Color(0xFF13503D)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Tune,
                  contentDescription = null,
                  tint = GoldAccent,
                  modifier = Modifier.size(18.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = when (language) {
                    AppLanguage.BANGLA -> "হিজরি তারিখ সমন্বয়:"
                    AppLanguage.ENGLISH -> "Hijri Date Adjustment:"
                    AppLanguage.URDU -> "ہجری تاریخ ایڈجسٹمنٹ:"
                    AppLanguage.ARABIC -> "تعديل التاريخ الهجري:"
                  },
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold,
                  color = SoftWhiteText
                )
                Text(
                  text = when (language) {
                    AppLanguage.BANGLA -> "চাঁদ দেখার ওপর ভিত্তি করে ১-২ দিন পরিবর্তন করুন"
                    AppLanguage.ENGLISH -> "Adjust 1-2 days based on local moon sighting"
                    AppLanguage.URDU -> "چاند دیکھنے کی بنیاد پر ۱-۲ دن ایڈجسٹ کریں"
                    AppLanguage.ARABIC -> "تعديل ١-٢ يوم بناءً على رؤية الهلال"
                  },
                  fontSize = 12.sp,
                  color = SageText
                )
              }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Stepper Row: [-] [ Current Offset Display ] [+]
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              // Minus Button
              IconButton(
                onClick = {
                  if (hijriOffset > -2) onOffsetChange(hijriOffset - 1)
                },
                enabled = hijriOffset > -2,
                modifier = Modifier
                  .size(46.dp)
                  .clip(CircleShape)
                  .background(if (hijriOffset > -2) Color(0xFF18644D) else Color(0xFF103629))
                  .testTag("hijri_offset_minus_button")
              ) {
                Icon(
                  imageVector = Icons.Default.Remove,
                  contentDescription = "Decrease",
                  tint = if (hijriOffset > -2) SoftWhiteText else SageText.copy(alpha = 0.5f),
                  modifier = Modifier.size(22.dp)
                )
              }

              // Center display pill
              Surface(
                modifier = Modifier
                  .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFF072118),
                border = BorderStroke(1.dp, GoldAccent.copy(alpha = 0.5f))
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = when {
                      hijriOffset == 0 -> when (language) {
                        AppLanguage.BANGLA -> "০ দিন (ডিফল্ট)"
                        AppLanguage.ENGLISH -> "0 Days (Default)"
                        AppLanguage.URDU -> "۰ دن (ڈیفالٹ)"
                        AppLanguage.ARABIC -> "٠ يوم (افتراضي)"
                      }
                      hijriOffset > 0 -> when (language) {
                        AppLanguage.BANGLA -> "+${hijriOffset.toBengaliDigits()} দিন"
                        AppLanguage.ENGLISH -> "+$hijriOffset Day(s)"
                        AppLanguage.URDU -> "+${hijriOffset.toArabicDigits()} دن"
                        AppLanguage.ARABIC -> "+${hijriOffset.toArabicDigits()} يوم"
                      }
                      else -> when (language) {
                        AppLanguage.BANGLA -> "${hijriOffset.toBengaliDigits()} দিন"
                        AppLanguage.ENGLISH -> "$hijriOffset Day(s)"
                        AppLanguage.URDU -> "${hijriOffset.toArabicDigits()} دن"
                        AppLanguage.ARABIC -> "${hijriOffset.toArabicDigits()} يوم"
                      }
                    },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldAccent
                  )
                }
              }

              // Plus Button
              IconButton(
                onClick = {
                  if (hijriOffset < 2) onOffsetChange(hijriOffset + 1)
                },
                enabled = hijriOffset < 2,
                modifier = Modifier
                  .size(46.dp)
                  .clip(CircleShape)
                  .background(if (hijriOffset < 2) Color(0xFF18644D) else Color(0xFF103629))
                  .testTag("hijri_offset_plus_button")
              ) {
                Icon(
                  imageVector = Icons.Default.Add,
                  contentDescription = "Increase",
                  tint = if (hijriOffset < 2) SoftWhiteText else SageText.copy(alpha = 0.5f),
                  modifier = Modifier.size(22.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Quick select chip buttons: -2, -1, 0, +1, +2
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              val options = listOf(-2, -1, 0, 1, 2)
              options.forEach { opt ->
                val isSelected = hijriOffset == opt
                val label = when (opt) {
                  -2 -> if (language == AppLanguage.BANGLA) "-২" else "-2"
                  -1 -> if (language == AppLanguage.BANGLA) "-১" else "-1"
                  0 -> if (language == AppLanguage.BANGLA) "০" else "0"
                  1 -> if (language == AppLanguage.BANGLA) "+১" else "+1"
                  2 -> if (language == AppLanguage.BANGLA) "+২" else "+2"
                  else -> "$opt"
                }

                val chipBg by animateColorAsState(
                  targetValue = if (isSelected) Color(0xFF1E6F56) else Color(0xFF09291E),
                  animationSpec = tween(200),
                  label = "chipBg"
                )
                val chipBorder by animateColorAsState(
                  targetValue = if (isSelected) GoldAccent else Color(0xFF1D5A46),
                  animationSpec = tween(200),
                  label = "chipBorder"
                )

                Surface(
                  modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onOffsetChange(opt) }
                    .testTag("hijri_chip_$opt"),
                  shape = RoundedCornerShape(10.dp),
                  color = chipBg,
                  border = BorderStroke(if (isSelected) 1.5.dp else 1.dp, chipBorder)
                ) {
                  Box(
                    modifier = Modifier.padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(
                      text = label,
                      fontSize = 13.sp,
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                      color = if (isSelected) GoldAccent else SoftWhiteText
                    )
                  }
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Islamic Transition Rule Notice Card (সূর্যাস্ত / মাগরিবের পর নতুন তারিখ)
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("maghrib_info_card"),
          shape = RoundedCornerShape(18.dp),
          border = BorderStroke(1.dp, Color(0xFFD4AF37).copy(alpha = 0.35f)),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF0D2D22))
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
          ) {
            Box(
              modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(Color(0xFF234433)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = GoldAccent,
                modifier = Modifier.size(18.dp)
              )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
              Text(
                text = when (language) {
                  AppLanguage.BANGLA -> "হিজরি দিন পরিবর্তনের নিয়ম:"
                  AppLanguage.ENGLISH -> "Hijri Day Transition Rule:"
                  AppLanguage.URDU -> "ہجری دن کی تبدیلی کا اصول:"
                  AppLanguage.ARABIC -> "قاعدة تغير اليوم الهجري:"
                },
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold,
                color = SoftWhiteText
              )

              Spacer(modifier = Modifier.height(4.dp))

              Text(
                text = when (language) {
                  AppLanguage.BANGLA -> "ইসলামি শরীয়ত মোতাবেক সূর্যাস্তের (মাগরিবের নামাজের) সাথে সাথেই নতুন হিজরি দিনের সূচনা হয়। তাই মাগরিবের পর স্বয়ংক্রিয়ভাবে পরের দিনের হিজরি তারিখ প্রদর্শিত হবে।"
                  AppLanguage.ENGLISH -> "According to Islamic law, every new Hijri day begins at sunset (Maghrib prayer). Therefore, the date updates automatically after Maghrib."
                  AppLanguage.URDU -> "اسلامی شریعت کے مطابق سورج غروب ہوتے ہی (مغرب کی نماز کے بعد) نیا ہجری دن شروع ہوتا ہے۔"
                  AppLanguage.ARABIC -> "وفقاً للشريعة الإسلامية، يبدأ اليوم الهجري الجديد مع غروب الشمس (صلاة المغرب)، لذا يتغير التاريخ تلقائياً بعد المغرب."
                },
                fontSize = 12.sp,
                color = MintText,
                lineHeight = 17.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Fasting Observances Card (আইয়ামে বীজ)
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("ayyam_al_bid_card"),
          shape = RoundedCornerShape(18.dp),
          border = BorderStroke(1.dp, CardBorderUnselected),
          colors = CardDefaults.cardColors(containerColor = CardBackgroundUnselected)
        ) {
          Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(Color(0xFF13503D)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                tint = MintText,
                modifier = Modifier.size(18.dp)
              )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
              Text(
                text = when (language) {
                  AppLanguage.BANGLA -> "আইয়ামে বীজের সুন্নত রোজা"
                  AppLanguage.ENGLISH -> "Sunnah Fasting of Ayyam al-Bid"
                  AppLanguage.URDU -> "ایام بیض کے سنت روزے"
                  AppLanguage.ARABIC -> "صيام الأيام البيض المسنونة"
                },
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold,
                color = SoftWhiteText
              )

              Text(
                text = when (language) {
                  AppLanguage.BANGLA -> "প্রতি হিজরি মাসের ১৩, ১৪ ও ১৫ তারিখ রোজা রাখা সুন্নত। ক্যালেন্ডারে রোজাগুলোর জন্য বিশেষ সংকেত থাকবে।"
                  AppLanguage.ENGLISH -> "Fasting on the 13th, 14th & 15th of each lunar month is Sunnah."
                  AppLanguage.URDU -> "ہر قمری مہینے کی ۱۳، ۱۴ اور ۱۵ تاریخ کو روزہ رکھنا سنت ہے۔"
                  AppLanguage.ARABIC -> "يُسن صيام الأيام ١٣ و١٤ و١٥ من كل شهر هجري."
                },
                fontSize = 12.sp,
                color = SageText,
                lineHeight = 16.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))
      }

      // Bottom Navigation Bar
      Surface(
        modifier = Modifier.fillMaxWidth(),
        color = NightGreenBackground,
        border = BorderStroke(1.dp, Color(0xFF1A4A38))
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(
              start = 16.dp,
              end = 16.dp,
              top = 12.dp,
              bottom = navBarPadding + 14.dp
            ),
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Back Button: returns to Step 5 (Adhan & Alerts)
          OutlinedButton(
            onClick = onBack,
            modifier = Modifier
              .weight(1f)
              .height(50.dp)
              .testTag("hijri_back_button"),
            shape = RoundedCornerShape(14.dp),
            border = BorderStroke(1.dp, Color(0xFF2B7D64)),
            colors = ButtonDefaults.outlinedButtonColors(
              contentColor = MintText
            )
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = null,
              modifier = Modifier.size(17.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = language.prevStepButton,
              fontSize = 14.sp,
              fontWeight = FontWeight.Medium
            )
          }

          // Finish / Get Started Button: completes onboarding and opens the app!
          Button(
            onClick = onFinish,
            modifier = Modifier
              .weight(1.3f)
              .height(50.dp)
              .testTag("finish_onboarding_button"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = ButtonGreen
            )
          ) {
            Text(
              text = when (language) {
                AppLanguage.BANGLA -> "সম্পন্ন করুন ✓"
                AppLanguage.ENGLISH -> "Finish Setup ✓"
                AppLanguage.URDU -> "مکمل کریں ✓"
                AppLanguage.ARABIC -> "إتمام البدء ✓"
              },
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF072118)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = null,
              tint = Color(0xFF072118),
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }
    }
  }
}
