package com.example.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.IslamicColorPalette
import com.example.ui.components.FlagEmblem
import com.example.ui.components.MosqueWatermark
import com.example.ui.theme.AppTheme
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MintText
import com.example.ui.theme.SageText
import com.example.ui.theme.SoftWhiteText

@Composable
fun SettingsScreen(
  currentPalette: IslamicColorPalette = IslamicColorPalette.EMERALD_GREEN,
  onPaletteChange: (IslamicColorPalette) -> Unit = {},
  onBack: () -> Unit,
  onOpenDatabaseManager: () -> Unit = {}
) {
  val context = LocalContext.current
  val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
  val navBarPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

  val themeColors = AppTheme.colors

  // System back button handling
  BackHandler { onBack() }

  // Interactive UI States matching the 4 screenshots
  var selectedLanguage by remember { mutableStateOf(AppLanguage.BANGLA) }
  var selectedDisplayMode by remember { mutableStateOf("system") } // "system", "light", "night"
  var selectedLayoutMode by remember { mutableStateOf("tab") } // "tab", "grid"

  // Toggles
  var isKeepScreenOn by remember { mutableStateOf(false) }
  var isDailyAmalReminderOn by remember { mutableStateOf(true) }
  var isNafalSalatOn by remember { mutableStateOf(true) }
  var isLockscreenScheduleOn by remember { mutableStateOf(true) }

  // Widget preview mode
  var widgetMode by remember { mutableStateOf("full") } // "full", "compact"

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFF04130E))
      .testTag("settings_screen")
  ) {
    Column(modifier = Modifier.fillMaxSize()) {

      // =========================================================================
      // 1. TOP APP BAR (অ্যাপ সেটিংস)
      // =========================================================================
      Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFF0F644A)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = statusBarPadding + 4.dp, bottom = 12.dp, start = 8.dp, end = 16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(
            onClick = onBack,
            modifier = Modifier.testTag("settings_back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = Color.White,
              modifier = Modifier.size(24.dp)
            )
          }

          Spacer(modifier = Modifier.width(4.dp))

          Text(
            text = "অ্যাপ সেটিংস",
            color = Color.White,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      // =========================================================================
      // SCROLLABLE CONTENT BODY (10 SECTIONS MATCHING SCREENSHOTS 1 TO 4)
      // =========================================================================
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(bottom = navBarPadding + 32.dp)
      ) {

        // TOP HEADER CARD (সেটিংস ও কনফিগারেশন)
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(Color(0xFF0F644A))
            .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 26.dp)
        ) {
          // Subtle Mosque Watermark on right
          MosqueWatermark(
            modifier = Modifier
              .align(Alignment.BottomEnd)
              .size(130.dp)
              .offset(x = 10.dp, y = 14.dp),
            tint = Color(0xFF2DD4BF).copy(alpha = 0.22f)
          )

          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Surface(
              modifier = Modifier.size(52.dp),
              shape = CircleShape,
              color = Color(0xFF13503D),
              border = BorderStroke(1.dp, Color(0xFF2DD4BF).copy(alpha = 0.5f))
            ) {
              Box(contentAlignment = Alignment.Center) {
                Icon(
                  imageVector = Icons.Default.Tune,
                  contentDescription = "Settings Icon",
                  tint = Color(0xFFE5A823),
                  modifier = Modifier.size(26.dp)
                )
              }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "সেটিংস ও কনফিগারেশন",
                color = Color.White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
              )
              Spacer(modifier = Modifier.height(3.dp))
              Text(
                text = "লোকেশন, আজান, থিম, ডাটাবেস ও একাউন্ট নিয়ন্ত্রণ",
                color = Color(0xFFB2E0D0),
                fontSize = 12.5.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // =======================================================================
        // PART 1 (App Setting Upper Part.jpeg)
        // =======================================================================

        // 1. ব্যবহারকারী একাউন্ট (User Account)
        SettingsSectionHeader(title = "ব্যবহারকারী একাউন্ট")

        SettingsActionCard(
          icon = Icons.Default.Person,
          iconBgColor = Color(0xFF0C382A),
          iconTint = Color(0xFF2DD4BF),
          title = "Google দিয়ে সাইন ইন করুন",
          subtitle = "আপনার একাউন্ট সিঙ্ক করতে ও সুবিধা পেতে",
          titleColor = Color(0xFF38B2AC),
          onClick = {
            Toast.makeText(context, "Google সাইন ইন অপশন প্রস্তুত হচ্ছে...", Toast.LENGTH_SHORT).show()
          }
        )

        Spacer(modifier = Modifier.height(18.dp))

        // 2. ডাটা ও অফলাইন স্টোরেজ (Data & Offline Storage)
        SettingsSectionHeader(title = "ডাটা ও অফলাইন স্টোরেজ")

        SettingsActionCard(
          icon = Icons.Default.Storage,
          iconBgColor = Color(0xFF0C382A),
          iconTint = Color(0xFF2DD4BF),
          title = "ডাটাবেস ও ফাইল ম্যানেজার",
          subtitle = "কুরআন, হাদিস ও ইসলামিক বইয়ের ডাটাবেস ফাইল ডাউনলোড বা রিমুভ করুন",
          onClick = onOpenDatabaseManager
        )

        Spacer(modifier = Modifier.height(18.dp))

        // 3. ভাষা নির্বাচন (Language)
        SettingsSectionHeader(title = "ভাষা নির্বাচন (Language)")

        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF072118)),
          border = BorderStroke(1.dp, Color(0xFF133F31))
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            // Row of 3 language pills: বাংলা, English, اردو
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              // Bangla
              LanguageSelectPill(
                title = "বাংলা",
                isSelected = selectedLanguage == AppLanguage.BANGLA,
                flag = { FlagEmblem(language = AppLanguage.BANGLA, size = 22.dp) },
                modifier = Modifier.weight(1f),
                onClick = { selectedLanguage = AppLanguage.BANGLA }
              )

              // English
              LanguageSelectPill(
                title = "English",
                isSelected = selectedLanguage == AppLanguage.ENGLISH,
                flag = { FlagEmblem(language = AppLanguage.ENGLISH, size = 22.dp) },
                modifier = Modifier.weight(1f),
                onClick = { selectedLanguage = AppLanguage.ENGLISH }
              )

              // Urdu
              LanguageSelectPill(
                title = "اردو",
                isSelected = selectedLanguage == AppLanguage.URDU,
                flag = { FlagEmblem(language = AppLanguage.URDU, size = 22.dp) },
                modifier = Modifier.weight(1f),
                onClick = { selectedLanguage = AppLanguage.URDU }
              )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Active Language Pill Indicator
            Surface(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(12.dp),
              color = Color(0xFF0A291F),
              border = BorderStroke(1.dp, Color(0xFF164A39))
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.Language,
                  contentDescription = "Language Active",
                  tint = Color(0xFF2DD4BF),
                  modifier = Modifier.size(17.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = when (selectedLanguage) {
                    AppLanguage.BANGLA -> "সক্রিয় ভাষা: বাংলা (সকল মেনু ও ইসলামিক কন্টেন্ট)"
                    AppLanguage.ENGLISH -> "Active Language: English (All menus & content)"
                    AppLanguage.URDU -> "فعال زبان: اردو (تمام مینو اور مواد)"
                    AppLanguage.ARABIC -> "اللغة النشطة: العربية (جميع القوائم والمحتوى)"
                  },
                  fontSize = 12.sp,
                  color = Color(0xFFC0E0D4)
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ফন্ট সেটিংস (Font Settings)
        SettingsActionCard(
          icon = Icons.Default.FormatSize,
          iconBgColor = Color(0xFF0C382A),
          iconTint = Color(0xFF2DD4BF),
          title = "ফন্ট সেটিংস",
          subtitle = "বাংলা, আরবি, উর্দু ও ইংরেজি ফন্ট এবং টেক্সট সাইজ কাস্টমাইজ করুন",
          onClick = {
            Toast.makeText(context, "ফন্ট ও টেক্সট সাইজ সেটিংস খোলা হচ্ছে...", Toast.LENGTH_SHORT).show()
          }
        )

        Spacer(modifier = Modifier.height(18.dp))

        // =======================================================================
        // PART 2 (App Setting Middle Part.jpeg)
        // =======================================================================

        // 4. অ্যাপ থিম ও কালার স্কিম (App Theme & Color Scheme)
        SettingsSectionHeader(title = "অ্যাপ থিম ও কালার স্কিম")

        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF072118)),
          border = BorderStroke(1.dp, Color(0xFF133F31))
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "ইসলামিক কালার প্যালেট",
              color = Color.White,
              fontSize = 15.5.sp,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
              text = "আপনার পছন্দের আধ্যাত্মিক রঙের আবহ নির্বাচন করুন",
              color = Color(0xFF94B8A9),
              fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // 7 Color Palettes matching App Setting Middle Part.jpeg
            val palettes = listOf(
              IslamicColorPalette.EMERALD_GREEN to "জমুররুদ সবুজ • ঐতিহ্য ও প্রশান্তি",
              IslamicColorPalette.ROYAL_MAROON to "শাহানা মেরুন • গাম্ভীর্য ও মহিমা",
              IslamicColorPalette.NOORANI_OLIVE to "নুরানী জলপাই • ইলম ও सुकून",
              IslamicColorPalette.SAPPHIRE_BLUE to "নীলকান্তমণি • নূর ও গোলাবি আভা",
              IslamicColorPalette.ROYAL_CYPRESS to "শাহানা সাইপ্রেস • ধৈর্য ও গভীর প্রশান্তি",
              IslamicColorPalette.RAMADAN_AMBER to "রমাদান আম্বার • উষ্ণতা ও মাগফিরাত",
              IslamicColorPalette.OCEAN_TURQUOISE to "সাগর ফিরোজা • বিশুদ্ধতা ও আকাশী সতেজতা"
            )

            palettes.forEach { (palette, desc) ->
              val isSelected = currentPalette == palette

              Surface(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp)
                  .clickable { onPaletteChange(palette) },
                shape = RoundedCornerShape(14.dp),
                color = if (isSelected) Color(0xFF0E382A) else Color(0xFF09251C),
                border = BorderStroke(
                  width = if (isSelected) 1.5.dp else 1.dp,
                  color = if (isSelected) Color(0xFFE5A823) else Color(0xFF143F31)
                )
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  // 4 Color Swatch circles
                  PaletteCircleSwatches(palette = palette)

                  Spacer(modifier = Modifier.width(10.dp))

                  Text(
                    text = desc,
                    color = if (isSelected) Color(0xFFE5A823) else SoftWhiteText,
                    fontSize = 12.5.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.weight(1f)
                  )

                  if (isSelected) {
                    Icon(
                      imageVector = Icons.Default.CheckCircle,
                      contentDescription = "Selected",
                      tint = Color(0xFF2DD4BF),
                      modifier = Modifier.size(20.dp)
                    )
                  } else {
                    Icon(
                      imageVector = Icons.Default.RadioButtonUnchecked,
                      contentDescription = "Not Selected",
                      tint = Color(0xFF336353),
                      modifier = Modifier.size(20.dp)
                    )
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // ডিসপ্লে মোড (Display Mode)
            Text(
              text = "ডিসপ্লে মোড",
              color = Color.White,
              fontSize = 14.5.sp,
              fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              DisplayModeButton(
                title = "সিস্টেম",
                icon = Icons.Default.Star,
                isSelected = selectedDisplayMode == "system",
                onClick = { selectedDisplayMode = "system" },
                modifier = Modifier.weight(1f)
              )

              DisplayModeButton(
                title = "লাইট",
                icon = Icons.Default.WbSunny,
                isSelected = selectedDisplayMode == "light",
                onClick = { selectedDisplayMode = "light" },
                modifier = Modifier.weight(1f)
              )

              DisplayModeButton(
                title = "নাইট",
                icon = Icons.Default.NightlightRound,
                isSelected = selectedDisplayMode == "night",
                onClick = { selectedDisplayMode = "night" },
                modifier = Modifier.weight(1f)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // হোম মেনু লেআউট স্টাইল (Home Menu Layout Style)
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF072118)),
          border = BorderStroke(1.dp, Color(0xFF133F31))
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = Color(0xFF0C382A)
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(
                    imageVector = Icons.Default.GridView,
                    contentDescription = null,
                    tint = Color(0xFF2DD4BF),
                    modifier = Modifier.size(20.dp)
                  )
                }
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "হোম মেনু লেআউট স্টাইল",
                  color = Color.White,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = "হরাইজন্টাল সোয়াইপ ট্যাব অথবা অল-ইন-ওয়ান ভার্টিকাল গ্রিড নির্বাচন করুন",
                  color = Color(0xFF94B8A9),
                  fontSize = 11.5.sp,
                  lineHeight = 16.sp
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Surface(
                modifier = Modifier
                  .weight(1f)
                  .clickable { selectedLayoutMode = "tab" },
                shape = RoundedCornerShape(12.dp),
                color = if (selectedLayoutMode == "tab") Color(0xFF13503D) else Color(0xFF09251C),
                border = BorderStroke(
                  1.dp,
                  if (selectedLayoutMode == "tab") Color(0xFF2DD4BF) else Color(0xFF164A39)
                )
              ) {
                Row(
                  modifier = Modifier.padding(vertical = 10.dp),
                  horizontalArrangement = Arrangement.Center,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = null,
                    tint = if (selectedLayoutMode == "tab") Color(0xFFE5A823) else Color(0xFF86A89B),
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "ট্যাব ভিউ (সুইপ)",
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedLayoutMode == "tab") Color.White else Color(0xFF86A89B)
                  )
                }
              }

              Surface(
                modifier = Modifier
                  .weight(1f)
                  .clickable { selectedLayoutMode = "grid" },
                shape = RoundedCornerShape(12.dp),
                color = if (selectedLayoutMode == "grid") Color(0xFF13503D) else Color(0xFF09251C),
                border = BorderStroke(
                  1.dp,
                  if (selectedLayoutMode == "grid") Color(0xFF2DD4BF) else Color(0xFF164A39)
                )
              ) {
                Row(
                  modifier = Modifier.padding(vertical = 10.dp),
                  horizontalArrangement = Arrangement.Center,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(
                    imageVector = Icons.Default.GridView,
                    contentDescription = null,
                    tint = if (selectedLayoutMode == "grid") Color(0xFFE5A823) else Color(0xFF86A89B),
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "ভার্টিকাল গ্রিড",
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedLayoutMode == "grid") Color.White else Color(0xFF86A89B)
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // =======================================================================
        // PART 3 (App Setting 3rd Part.jpeg)
        // =======================================================================

        // 5. স্ক্রিন ও ডিসপ্লে নিয়ন্ত্রণ (Screen & Display Control)
        SettingsSectionHeader(title = "স্ক্রিন ও ডিসপ্লে নিয়ন্ত্রণ")

        SettingsToggleCard(
          icon = Icons.Default.Brightness7,
          iconBgColor = Color(0xFF382E0C),
          iconTint = Color(0xFFE5A823),
          title = "স্ক্রিন সর্বদা অন রাখুন",
          subtitle = "অ্যাপ ব্যবহারের সময় ফোনের স্ক্রিন স্বয়ংক্রিয়ভাবে বন্ধ হবে না",
          isChecked = isKeepScreenOn,
          onCheckedChange = { isKeepScreenOn = it }
        )

        Spacer(modifier = Modifier.height(18.dp))

        // 6. আমল এলার্ট ও সেটিংস (Amal Alert & Settings)
        SettingsSectionHeader(title = "আমল এলার্ট ও সেটিংস")

        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF072118)),
          border = BorderStroke(1.dp, Color(0xFF133F31))
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            // Item 1: দৈনিক আমল রিমাইন্ডার (Orange Bell)
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                modifier = Modifier.size(42.dp),
                shape = CircleShape,
                color = Color(0xFF3B2A0A)
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = Color(0xFFF59E0B),
                    modifier = Modifier.size(22.dp)
                  )
                }
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "দৈনিক আমল রিমাইন্ডার",
                  color = Color.White,
                  fontSize = 14.5.sp,
                  fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = "প্রতিদিন রাতে আমল হিসাবের জন্য তাগিদ নোটিফিকেশন",
                  color = Color(0xFF94B8A9),
                  fontSize = 11.5.sp
                )
              }
              Switch(
                checked = isDailyAmalReminderOn,
                onCheckedChange = { isDailyAmalReminderOn = it },
                colors = SwitchDefaults.colors(
                  checkedThumbColor = Color.White,
                  checkedTrackColor = Color(0xFFF59E0B),
                  uncheckedThumbColor = Color(0xFF86A89B),
                  uncheckedTrackColor = Color(0xFF0F382A)
                )
              )
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Color(0xFF133F31), thickness = 1.dp)
            Spacer(modifier = Modifier.height(14.dp))

            // Item 2: নফল সালাত ও অতিরিক্ত আমল (Purple Star)
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                modifier = Modifier.size(42.dp),
                shape = CircleShape,
                color = Color(0xFF28133B)
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFA855F7),
                    modifier = Modifier.size(22.dp)
                  )
                }
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "নফল সালাত ও অতিরিক্ত আমল",
                  color = Color.White,
                  fontSize = 14.5.sp,
                  fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = "ফরজ নামাজের পাশাপাশি তাহাজ্জুদ, ইশরাক ও নফল আমল প্রদর্শন",
                  color = Color(0xFF94B8A9),
                  fontSize = 11.5.sp
                )
              }
              Switch(
                checked = isNafalSalatOn,
                onCheckedChange = { isNafalSalatOn = it },
                colors = SwitchDefaults.colors(
                  checkedThumbColor = Color.White,
                  checkedTrackColor = Color(0xFFA855F7),
                  uncheckedThumbColor = Color(0xFF86A89B),
                  uncheckedTrackColor = Color(0xFF0F382A)
                )
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // 7. নামাজ ও লোকেশন সেটিংস (Prayer & Location Settings)
        SettingsSectionHeader(title = "নামাজ ও লোকেশন সেটিংস")

        SettingsActionCard(
          icon = Icons.Default.LocationOn,
          iconBgColor = Color(0xFF0C382A),
          iconTint = Color(0xFF2DD4BF),
          title = "লোকেশন ও নামাজ গণনা সেটিংস",
          subtitle = "চট্টগ্রাম 🇧🇩 (জেলা, মাযহাব, হিসাব পদ্ধতি ও সময় সমন্বয়)",
          onClick = {
            Toast.makeText(context, "লোকেশন ও সালাত সেটিংস খোলা হচ্ছে...", Toast.LENGTH_SHORT).show()
          }
        )

        Spacer(modifier = Modifier.height(18.dp))

        // 8. আজান ও ডিভাইস সেটআপ (Adhan & Device Setup)
        SettingsSectionHeader(title = "আজান ও ডিভাইস সেটআপ")

        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF072118)),
          border = BorderStroke(1.dp, Color(0xFF133F31))
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            // Lockscreen Schedule Toggle
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                modifier = Modifier.size(42.dp),
                shape = CircleShape,
                color = Color(0xFF0C382A)
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = Color(0xFF2DD4BF),
                    modifier = Modifier.size(22.dp)
                  )
                }
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "লকস্ক্রিনে সার্বক্ষণিক সময়সূচি",
                  color = Color.White,
                  fontSize = 14.5.sp,
                  fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = "লকস্ক্রিন ও নোটিফিকেশন বারে বর্তমান ওয়াক্ত ও সময় দেখুন",
                  color = Color(0xFF94B8A9),
                  fontSize = 11.5.sp
                )
              }
              Switch(
                checked = isLockscreenScheduleOn,
                onCheckedChange = { isLockscreenScheduleOn = it },
                colors = SwitchDefaults.colors(
                  checkedThumbColor = Color.White,
                  checkedTrackColor = Color(0xFF10B981),
                  uncheckedThumbColor = Color(0xFF86A89B),
                  uncheckedTrackColor = Color(0xFF0F382A)
                )
              )
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Color(0xFF133F31), thickness = 1.dp)
            Spacer(modifier = Modifier.height(14.dp))

            // আজান ও ব্যাটারি অপটিমাইজেশন (Battery Alert)
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable {
                  Toast.makeText(context, "ব্যাটারি অপটিমাইজেশন নির্দেশিকা...", Toast.LENGTH_SHORT).show()
                },
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                modifier = Modifier.size(42.dp),
                shape = CircleShape,
                color = Color(0xFF381F0C)
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(
                    imageVector = Icons.Default.BatteryAlert,
                    contentDescription = null,
                    tint = Color(0xFFF97316),
                    modifier = Modifier.size(22.dp)
                  )
                }
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "আজান ও ব্যাটারি অপটিমাইজেশন",
                  color = Color.White,
                  fontSize = 14.5.sp,
                  fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = "সঠিক সময়ে ব্যাকগ্রাউন্ড আজান পেতে ব্যাটারি অপটিমাইজেশন বন্ধ করুন",
                  color = Color(0xFF94B8A9),
                  fontSize = 11.5.sp
                )
              }
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = Color(0xFF4A7363),
                modifier = Modifier.size(16.dp)
              )
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Color(0xFF133F31), thickness = 1.dp)
            Spacer(modifier = Modifier.height(14.dp))

            // অটো-স্টার্ট অনুমতি (OPPO)
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable {
                  Toast.makeText(context, "অটো-স্টার্ট অনুমতি কনফিগার করুন...", Toast.LENGTH_SHORT).show()
                },
              verticalAlignment = Alignment.CenterVertically
            ) {
              Surface(
                modifier = Modifier.size(42.dp),
                shape = CircleShape,
                color = Color(0xFF2D163B)
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(
                    imageVector = Icons.Default.NotificationsActive,
                    contentDescription = null,
                    tint = Color(0xFFA855F7),
                    modifier = Modifier.size(22.dp)
                  )
                }
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "অটো-স্টার্ট অনুমতি (OPPO)",
                  color = Color.White,
                  fontSize = 14.5.sp,
                  fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = "OPPO ডিভাইসে সময়মতো আজান পেতে অটো-স্টার্ট চালু করুন",
                  color = Color(0xFF94B8A9),
                  fontSize = 11.5.sp
                )
              }
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = Color(0xFF4A7363),
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // =======================================================================
        // PART 4 (App Setting 4th Part.jpeg)
        // =======================================================================

        // 9. হোম স্ক্রিন উইজেট (ডেস্কটপ) (Home Screen Widget)
        SettingsSectionHeader(title = "হোম স্ক্রিন উইজেট (ডেস্কটপ)")

        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF072118)),
          border = BorderStroke(1.dp, Color(0xFF133F31))
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            // Widget Title Header
            Row(verticalAlignment = Alignment.CenterVertically) {
              Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = Color(0xFF0C382A)
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(
                    imageVector = Icons.Default.GridView,
                    contentDescription = null,
                    tint = Color(0xFF2DD4BF),
                    modifier = Modifier.size(20.dp)
                  )
                }
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "হোম স্ক্রিন নামাজের উইজেট",
                  color = Color.White,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = "অ্যাপ না খুলেই স্ক্রিনে নামাজের সময়, সেহরি-ইফতার ও লাইভ কাউন্টডাউন",
                  color = Color(0xFF94B8A9),
                  fontSize = 11.5.sp
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Widget Tabs Row (পূর্ণাঙ্গ ড্যাশবোর্ড (৪x২) / কম্প্যাক্ট বার (৪x১))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              Surface(
                modifier = Modifier
                  .weight(1f)
                  .clickable { widgetMode = "full" },
                shape = RoundedCornerShape(10.dp),
                color = if (widgetMode == "full") Color(0xFF13503D) else Color(0xFF0A291F),
                border = BorderStroke(
                  1.dp,
                  if (widgetMode == "full") Color(0xFF2DD4BF) else Color(0xFF164A39)
                )
              ) {
                Box(
                  contentAlignment = Alignment.Center,
                  modifier = Modifier.padding(vertical = 10.dp)
                ) {
                  Text(
                    text = "পূর্ণাঙ্গ ড্যাশবোর্ড (৪x২)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (widgetMode == "full") Color.White else Color(0xFF86A89B)
                  )
                }
              }

              Surface(
                modifier = Modifier
                  .weight(1f)
                  .clickable { widgetMode = "compact" },
                shape = RoundedCornerShape(10.dp),
                color = if (widgetMode == "compact") Color(0xFF13503D) else Color(0xFF0A291F),
                border = BorderStroke(
                  1.dp,
                  if (widgetMode == "compact") Color(0xFF2DD4BF) else Color(0xFF164A39)
                )
              ) {
                Box(
                  contentAlignment = Alignment.Center,
                  modifier = Modifier.padding(vertical = 10.dp)
                ) {
                  Text(
                    text = "কম্প্যাক্ট বার (৪x১)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (widgetMode == "compact") Color.White else Color(0xFF86A89B)
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // INTERACTIVE LIVE WIDGET PREVIEW FRAME matching screenshot 4
            Surface(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp),
              color = Color(0xFF061B14),
              border = BorderStroke(1.2.dp, Color(0xFF2A594A))
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                // Top Row: Shrine emblem, Date & Location, Sunrise
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🕌", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "২ জমাদিউল আউয়াল ১৪৪৮ হিজরি • চট্টগ্রাম 🇧🇩",
                      color = Color(0xFFD1FAE5),
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Medium
                    )
                  }

                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      imageVector = Icons.Default.WbSunny,
                      contentDescription = null,
                      tint = Color(0xFFE5A823),
                      modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      text = "সূর্যোদয় ০৫:৪৯",
                      color = Color(0xFFE5A823),
                      fontSize = 10.5.sp,
                      fontWeight = FontWeight.Bold
                    )
                  }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // 5 Waqts Grid Row
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                  val widgetWaqts = listOf(
                    Triple("ফজর", "০৪:৩৪", false),
                    Triple("যোহর", "১১:৪২", false),
                    Triple("আসর", "০৩:৫২", false),
                    Triple("মাগরিব", "০৫:৩০", true), // Active
                    Triple("ইশা", "০৬:৪৪", false)
                  )

                  widgetWaqts.forEach { (name, time, isActive) ->
                    Surface(
                      modifier = Modifier.weight(1f),
                      shape = RoundedCornerShape(8.dp),
                      color = if (isActive) Color(0xFF13503D) else Color(0xFF0A291F),
                      border = BorderStroke(
                        if (isActive) 1.5.dp else 0.8.dp,
                        if (isActive) Color(0xFFE5A823) else Color(0xFF174737)
                      )
                    ) {
                      Column(
                        modifier = Modifier.padding(vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                      ) {
                        Text(
                          text = name,
                          fontSize = 10.5.sp,
                          color = if (isActive) Color(0xFFE5A823) else Color(0xFF94B8A9),
                          fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                          text = time,
                          fontSize = 11.5.sp,
                          color = Color.White,
                          fontWeight = FontWeight.Bold
                        )
                      }
                    }
                  }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Waqt End, Sahur & Iftar Row
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(
                    text = "⏱️ মাগরিব শেষ: ২১:২১",
                    color = Color(0xFF6EE7B7),
                    fontSize = 10.5.sp
                  )
                  Text(
                    text = "⏱️ সাহরি শেষ: ০৪:৩১  ☀️ ইফতার: ০৫:৩০",
                    color = Color(0xFFFDE68A),
                    fontSize = 10.5.sp
                  )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Prohibited prayer time warning
                Surface(
                  modifier = Modifier.fillMaxWidth(),
                  shape = RoundedCornerShape(6.dp),
                  color = Color(0xFF0D2B20),
                  border = BorderStroke(0.6.dp, Color(0xFF1C5340))
                ) {
                  Text(
                    text = "🚫 পরবর্তী নামাজের নিষিদ্ধ সময়: সূর্যোদয় (০৫:৪৯ - ০৬:০৪)",
                    color = Color(0xFF86EFAC),
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Urs Notice strip
                Surface(
                  modifier = Modifier.fillMaxWidth(),
                  shape = RoundedCornerShape(6.dp),
                  color = Color(0xFF282008),
                  border = BorderStroke(0.6.dp, Color(0xFF6B5316))
                ) {
                  Text(
                    text = "⭐ পবিত্র উরস: হযরত গরীব উল্লাহ শাহ (রহ.): চট্টগ্রাম শহরের দামপাড়ায় শায়িত প্র...",
                    color = Color(0xFFFDE047),
                    fontSize = 10.sp,
                    maxLines = 1,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Big Green Action Button: "পূর্ণাঙ্গ উইজেট হোম স্ক্রিনে যোগ করুন"
            Button(
              onClick = {
                Toast.makeText(context, "হোম স্ক্রিনে উইজেট পিন করার অনুরোধ পাঠানো হয়েছে!", Toast.LENGTH_LONG).show()
              },
              modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("add_widget_button"),
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF13684E),
                contentColor = Color.White
              )
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.GridView,
                  contentDescription = null,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "পূর্ণাঙ্গ উইজেট হোম স্ক্রিনে যোগ করুন",
                  fontSize = 14.5.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Tip note text
            Text(
              text = "💡 টিপ: ডায়ালগ না আসলে আপনার ফোনের হোম স্ক্রিনে ফাঁকা জায়গায় চেপে ধরে (Long Press) 'Widgets' মেনু থেকে 'শাজরা শরীফ' নির্বাচন করুন।",
              color = Color(0xFF7FA899),
              fontSize = 11.5.sp,
              lineHeight = 16.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // 10. অ্যাপ তথ্য ও পরিচিতি (App Info & About)
        SettingsSectionHeader(title = "অ্যাপ তথ্য ও পরিচিতি")

        SettingsActionCard(
          icon = Icons.Default.Info,
          iconBgColor = Color(0xFF0C382A),
          iconTint = Color(0xFF2DD4BF),
          title = "অ্যাপ তথ্য ও ডেভেলপার পরিচিতি",
          subtitle = "ভার্সন, ডেভেলপার তথ্য, প্রাইভেসি পলিসি ও সোশ্যাল মিডিয়া",
          onClick = {
            Toast.makeText(context, "অ্যাপ সংস্করণ: ১.০.০ (শাজরা শরীফ - সুন্নি জীবনধারা)", Toast.LENGTH_LONG).show()
          }
        )

        Spacer(modifier = Modifier.height(20.dp))
      }
    }
  }
}

// =============================================================================
// SUB-COMPONENTS FOR REUSABLE SETTINGS TILES
// =============================================================================

@Composable
private fun SettingsSectionHeader(title: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 16.dp, end = 16.dp, bottom = 10.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .size(width = 3.5.dp, height = 16.dp)
        .clip(RoundedCornerShape(2.dp))
        .background(Color(0xFF2DD4BF))
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = title,
      color = Color(0xFF34D399),
      fontSize = 14.sp,
      fontWeight = FontWeight.Bold
    )
  }
}

@Composable
private fun SettingsActionCard(
  icon: ImageVector,
  iconBgColor: Color,
  iconTint: Color,
  title: String,
  subtitle: String,
  titleColor: Color = Color.White,
  onClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 14.dp)
      .clickable { onClick() },
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color(0xFF072118)),
    border = BorderStroke(1.dp, Color(0xFF133F31))
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Surface(
        modifier = Modifier.size(44.dp),
        shape = CircleShape,
        color = iconBgColor
      ) {
        Box(contentAlignment = Alignment.Center) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(22.dp)
          )
        }
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          color = titleColor,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = subtitle,
          color = Color(0xFF94B8A9),
          fontSize = 11.5.sp,
          lineHeight = 16.sp
        )
      }

      Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
        contentDescription = null,
        tint = Color(0xFF4A7363),
        modifier = Modifier.size(16.dp)
      )
    }
  }
}

@Composable
private fun SettingsToggleCard(
  icon: ImageVector,
  iconBgColor: Color,
  iconTint: Color,
  title: String,
  subtitle: String,
  isChecked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 14.dp),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color(0xFF072118)),
    border = BorderStroke(1.dp, Color(0xFF133F31))
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Surface(
        modifier = Modifier.size(44.dp),
        shape = CircleShape,
        color = iconBgColor
      ) {
        Box(contentAlignment = Alignment.Center) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(22.dp)
          )
        }
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          color = Color.White,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = subtitle,
          color = Color(0xFF94B8A9),
          fontSize = 11.5.sp,
          lineHeight = 16.sp
        )
      }

      Switch(
        checked = isChecked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
          checkedThumbColor = Color.White,
          checkedTrackColor = Color(0xFF10B981),
          uncheckedThumbColor = Color(0xFF86A89B),
          uncheckedTrackColor = Color(0xFF0F382A)
        )
      )
    }
  }
}

@Composable
private fun LanguageSelectPill(
  title: String,
  isSelected: Boolean,
  flag: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  onClick: () -> Unit
) {
  Surface(
    modifier = modifier.clickable { onClick() },
    shape = RoundedCornerShape(12.dp),
    color = if (isSelected) Color(0xFF104434) else Color(0xFF0A291F),
    border = BorderStroke(
      width = if (isSelected) 1.5.dp else 1.dp,
      color = if (isSelected) Color(0xFF2DD4BF) else Color(0xFF164A39)
    )
  ) {
    Row(
      modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      flag()
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = title,
        color = if (isSelected) Color.White else Color(0xFF94B8A9),
        fontSize = 13.sp,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
      )
    }
  }
}

@Composable
private fun PaletteCircleSwatches(palette: IslamicColorPalette) {
  val swatches = listOf(
    palette.primaryColor,
    palette.secondaryColor,
    palette.colors.headerStart,
    palette.colors.primaryButton
  )

  Row(horizontalArrangement = Arrangement.spacedBy((-6).dp)) {
    swatches.forEach { color ->
      Box(
        modifier = Modifier
          .size(20.dp)
          .clip(CircleShape)
          .background(color)
          .clip(CircleShape)
      ) {
        Surface(
          modifier = Modifier.fillMaxSize(),
          shape = CircleShape,
          color = color,
          border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
        ) {}
      }
    }
  }
}

@Composable
private fun DisplayModeButton(
  title: String,
  icon: ImageVector,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier.clickable { onClick() },
    shape = RoundedCornerShape(12.dp),
    color = if (isSelected) Color(0xFF13503D) else Color(0xFF0A291F),
    border = BorderStroke(
      1.dp,
      if (isSelected) Color(0xFF2DD4BF) else Color(0xFF164A39)
    )
  ) {
    Row(
      modifier = Modifier.padding(vertical = 10.dp),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = if (isSelected) Color(0xFFE5A823) else Color(0xFF86A89B),
        modifier = Modifier.size(16.dp)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = title,
        fontSize = 12.5.sp,
        fontWeight = FontWeight.Bold,
        color = if (isSelected) Color.White else Color(0xFF86A89B)
      )
    }
  }
}
