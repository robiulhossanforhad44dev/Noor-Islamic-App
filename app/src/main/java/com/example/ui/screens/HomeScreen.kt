package com.example.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.NightlightRound
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.AppLanguage
import com.example.model.IslamicColorPalette
import com.example.ui.components.BishoybhittikQuranArt
import com.example.ui.components.CalcuttaQuranArt
import com.example.ui.components.ColorQuranArt
import com.example.ui.components.DaroodSharifArt
import com.example.ui.components.HafeziQuranArt
import com.example.ui.components.HifzulQuranArt
import com.example.ui.components.IslamicArchFrame
import com.example.ui.components.IslamicArchShape
import com.example.ui.components.LanguageChangeDialog
import com.example.ui.components.LanguagePickerModal
import com.example.ui.components.MosqueWatermark
import com.example.ui.components.NooraniQaidaArt
import com.example.ui.components.PrayerTimeArt
import com.example.ui.components.QiblaCompassDialog
import com.example.ui.components.QuickDistrictPickerModal
import com.example.ui.components.QuickPalettePickerModal
import com.example.ui.components.QuranBrowserDialog
import com.example.ui.components.QuranRehalArt
import com.example.ui.components.QuranTelawatArt
import com.example.ui.components.ShajraHeaderEmblem
import com.example.ui.components.ShajraSharifArt
import com.example.ui.components.TafseerQuranArt
import com.example.ui.components.TajweedQuranArt
import com.example.ui.components.TasbihArt
import com.example.ui.theme.AppTheme
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MintText
import com.example.ui.theme.SageText
import com.example.ui.theme.SoftWhiteText

data class PrayerPillInfo(
  val id: String,
  val name: String,
  val time: String,
  val isActive: Boolean = false
)

data class PrioAmalItem(
  val id: String,
  val title: String
)

data class SubCategoryArchItem(
  val id: String,
  val title: String
)

@Composable
fun HomeScreen(
  currentPalette: IslamicColorPalette = IslamicColorPalette.EMERALD_GREEN,
  onPaletteChange: (IslamicColorPalette) -> Unit = {},
  onOpenSettings: () -> Unit = {}
) {
  val context = LocalContext.current
  val clipboardManager = LocalClipboardManager.current
  val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
  val navBarPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

  val themeColors = AppTheme.colors

  // Screen States
  var selectedDistrict by remember { mutableStateOf("চট্টগ্রাম") }
  var currentLanguage by remember { mutableStateOf(AppLanguage.BANGLA) }

  // Modal Dialog Controllers
  var showDistrictPickerModal by remember { mutableStateOf(false) }
  var showPalettePickerModal by remember { mutableStateOf(false) }
  var showLanguagePickerModal by remember { mutableStateOf(false) }
  var pendingLanguageChange by remember { mutableStateOf<AppLanguage?>(null) }
  var showQiblaModal by remember { mutableStateOf(false) }
  var showQuranModal by remember { mutableStateOf(false) }

  // Quick category tabs selection
  var activeCategoryTab by remember { mutableStateOf("quran") }

  // 5 Waqts matching user's screenshot: Fajr, Dhuhr, Asr, Maghrib (Active), Isha
  val prayerPills = listOf(
    PrayerPillInfo("fajr", "ফজর", "০৪:৩৪", false),
    PrayerPillInfo("dhuhr", "যোহর", "১১:৪২", false),
    PrayerPillInfo("asr", "আসর", "০৩:৫২", false),
    PrayerPillInfo("maghrib", "মাগরিব", "০৫:৩০", true), // Highlighted active waqt
    PrayerPillInfo("isha", "ইশা", "০৬:৪৪", false)
  )

  // 5 Prio Amal Items matching the screenshot
  val prioAmalList = listOf(
    PrioAmalItem("prayer", "নামাজের সময়"),
    PrioAmalItem("quran", "কুরআন মাজিদ"),
    PrioAmalItem("hadith", "হাদিস শরীফ"),
    PrioAmalItem("shajra", "শাজরা শরীফ"),
    PrioAmalItem("darood", "দরোদে শরীফ")
  )

  // 10 Quran Sub-items matching App Main Activity Lower Part.jpeg
  val quranSubItems = listOf(
    SubCategoryArchItem("quran_majid", "কুরআন মাজিদ"),
    SubCategoryArchItem("tafseer", "তাফসীরুল কুরআন"),
    SubCategoryArchItem("hafezi", "হাফেজী কুরআন"),
    SubCategoryArchItem("kolikata", "কলিকাতা কুরআন"),
    SubCategoryArchItem("color_quran", "কালার কুরআন মাজিদ"),
    SubCategoryArchItem("hifzul", "হিফজুল কুরআন"),
    SubCategoryArchItem("bishoy", "বিষয়ভিত্তিক কুরআন"),
    SubCategoryArchItem("telawat", "কুরআন তেলাওয়াত"),
    SubCategoryArchItem("tajweed", "তাজভীদুল কুরআন"),
    SubCategoryArchItem("noorani", "নূরানী কায়দা")
  )

  val hadithSubItems = listOf(
    SubCategoryArchItem("bukhari", "সহীহুল বুখারী"),
    SubCategoryArchItem("muslim", "সহীহ মুসলিম"),
    SubCategoryArchItem("abu_dawood", "সুনানে আবু দাউদ"),
    SubCategoryArchItem("tirmidhi", "জামে তিরমিযী"),
    SubCategoryArchItem("nasai", "সুনানে নাসায়ী"),
    SubCategoryArchItem("ibn_majah", "সুনানে ইবনে মাজাহ"),
    SubCategoryArchItem("mishkat", "মিশকাত শরীফ"),
    SubCategoryArchItem("riyad", "রিয়াদুস সালেহীন"),
    SubCategoryArchItem("fatwa", "ফতোয়ায়ে রেজভিয়া"),
    SubCategoryArchItem("nurun_nabi", "নুরুন নবী (ﷺ)")
  )

  val ibadatSubItems = listOf(
    SubCategoryArchItem("namaz_niyom", "নামাজের নিয়ম"),
    SubCategoryArchItem("tasbih_namaz", "সালাতুল তাসবীহ"),
    SubCategoryArchItem("tahajjud", "তাহাজ্জুদ ও নফল"),
    SubCategoryArchItem("tarabi", "তারাবীহ নামাজ"),
    SubCategoryArchItem("roza", "রোজার বিধান"),
    SubCategoryArchItem("hajj", "হজ ও ওমরাহ"),
    SubCategoryArchItem("zakat_calc", "যাকাত হিসাব"),
    SubCategoryArchItem("khawajegan", "খতমে খাজেগান"),
    SubCategoryArchItem("milad", "মিলাদ ও কিয়াম"),
    SubCategoryArchItem("dua_zikr", "দু'আ ও যিকির")
  )

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(themeColors.background)
      .testTag("home_screen")
  ) {
    // Continuous Scrollable Column matching user screenshot
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(bottom = 20.dp + navBarPadding)
    ) {
      // =========================================================================
      // 1. TOP HEADER APP BAR (শাজরা শরীফ - সুন্নি জীবনধারা)
      // =========================================================================
      Surface(
        modifier = Modifier.fillMaxWidth(),
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
              top = statusBarPadding + 6.dp,
              bottom = 14.dp,
              start = 14.dp,
              end = 14.dp
            )
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            // Left: Shrine / Mosque Dome emblem
            ShajraHeaderEmblem(
              modifier = Modifier
                .size(42.dp)
                .clickable {
                  Toast.makeText(context, "শাজরা শরীফ ও সিলসিলায়ে আলিয়া কাদেরিয়া", Toast.LENGTH_SHORT).show()
                },
              tint = Color.White
            )

            // Center Column: Title, Subtitle, Location
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              modifier = Modifier.weight(1f)
            ) {
              Text(
                text = "শাজরা শরীফ - সুন্নি জীবনধারা",
                color = Color.White,
                fontSize = 16.5.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
              )

              Spacer(modifier = Modifier.height(2.dp))

              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
              ) {
                Text(text = "—", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "ইসলামী জ্ঞান ও হিদায়াত",
                  color = Color.White.copy(alpha = 0.92f),
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "—", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
              }

              Spacer(modifier = Modifier.height(3.dp))

              // Location chip with pin & Bangladesh flag
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.clickable { showDistrictPickerModal = true }
              ) {
                Icon(
                  imageVector = Icons.Default.LocationOn,
                  contentDescription = "Location",
                  tint = GoldAccent,
                  modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                  text = selectedDistrict,
                  color = Color.White,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "🇧🇩", fontSize = 12.sp)
              }
            }

            // Right: Settings gear icon button
            IconButton(
              onClick = onOpenSettings,
              modifier = Modifier.size(38.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings / Theme Palette",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
              )
            }
          }
        }
      }

      // =========================================================================
      // 2. HERO IMAGE BANNER & FLOATING 5 WAQT PILLS
      // =========================================================================
      Box(
        modifier = Modifier
          .fillMaxWidth()
      ) {
        // Hero Photo of the Sunni Shrine Complex in Chittagong
        Image(
          painter = painterResource(id = R.drawable.chittagong_sunni_complex_1792225248074),
          contentDescription = "Chittagong Sunni Complex",
          modifier = Modifier
            .fillMaxWidth()
            .height(188.dp),
          contentScale = ContentScale.Crop
        )

        // Bottom subtle gradient overlay for smooth transition
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .align(Alignment.BottomCenter)
            .background(
              brush = Brush.verticalGradient(
                colors = listOf(
                  Color.Transparent,
                  themeColors.background.copy(alpha = 0.92f)
                )
              )
            )
        )
      }

      // 5-Waqt Horizontal Cards (Floating just below the photo)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .offset(y = (-14).dp)
          .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        prayerPills.forEach { waqt ->
          val isActive = waqt.isActive
          val cardBg = if (isActive) Color(0xFF0F3E30) else Color(0xFF09251C)
          val cardBorder = if (isActive) Color(0xFF2DD4BF) else Color(0xFF1B4E3E)

          Surface(
            modifier = Modifier
              .weight(1f)
              .clickable {
                Toast.makeText(context, "${waqt.name}: ${waqt.time}", Toast.LENGTH_SHORT).show()
              },
            shape = RoundedCornerShape(10.dp),
            color = cardBg,
            border = BorderStroke(if (isActive) 1.8.dp else 1.dp, cardBorder)
          ) {
            Column(
              modifier = Modifier.padding(vertical = 6.dp, horizontal = 2.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                text = waqt.name,
                fontSize = 11.5.sp,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
                color = if (isActive) Color(0xFF2DD4BF) else MintText
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = waqt.time,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = if (isActive) Color.White else SoftWhiteText
              )
            }
          }
        }
      }

      // =========================================================================
      // 3. PRAYER COUNTDOWN & SAHUR / IFTAR / CALENDAR CARD
      // =========================================================================
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 14.dp)
      ) {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF082018)),
          border = BorderStroke(1.dp, Color(0xFF163E30))
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            // Row 1: Next prayer countdown
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center,
              modifier = Modifier.fillMaxWidth()
            ) {
              Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = null,
                tint = GoldAccent,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "পরবর্তী ওয়াক্ত: ",
                color = MintText,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Medium
              )
              Text(
                text = "ইশা",
                color = GoldAccent,
                fontSize = 14.5.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = " • বাকি সময়: ",
                color = MintText,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Medium
              )
              Text(
                text = "০০:২২:০৩",
                color = GoldAccent,
                fontSize = 14.5.sp,
                fontWeight = FontWeight.Bold
              )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Row 2: Sahur & Iftar side-by-side pills
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              // Sahur Pill
              Surface(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFF0A291F),
                border = BorderStroke(1.dp, Color(0xFF1B4F3D))
              ) {
                Row(
                  modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.NightlightRound,
                    contentDescription = "Sahur",
                    tint = GoldAccent,
                    modifier = Modifier.size(15.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "পরবর্তী সাহরি: ",
                    color = MintText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                  )
                  Text(
                    text = "০৪:৩১",
                    color = GoldAccent,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }

              // Iftar Pill
              Surface(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFF0A291F),
                border = BorderStroke(1.dp, Color(0xFF1B4F3D))
              ) {
                Row(
                  modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.Center
                ) {
                  Icon(
                    imageVector = Icons.Default.Brightness7,
                    contentDescription = "Iftar",
                    tint = GoldAccent,
                    modifier = Modifier.size(15.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "পরবর্তী ইফতার: ",
                    color = MintText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                  )
                  Text(
                    text = "০৫:২৯",
                    color = GoldAccent,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Row 3: Hijri & Bangla Calendar Dates
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Mosque,
                  contentDescription = "Hijri",
                  tint = GoldAccent,
                  modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "২ জমাদিউল আউয়াল ১৪৪৮ ... |",
                  color = SoftWhiteText,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Medium
                )
              }

              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.CalendarMonth,
                  contentDescription = "Bangla Date",
                  tint = GoldAccent,
                  modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "২৮ আশ্বিন ১৪৩৩ বঙ্গাব্দ",
                  color = SoftWhiteText,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Medium
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // =========================================================================
        // 4. URS & SPECIAL OCCASION NOTICE CARD
        // =========================================================================
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF082018)),
          border = BorderStroke(1.dp, Color(0xFF163E30))
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = null,
                tint = GoldAccent,
                modifier = Modifier.size(15.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "পবিত্র উরস: হযরত গরীব উল্লাহ শাহ (রহ.)",
                color = GoldAccent,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold
              )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
              text = "চট্টগ্রাম শহরের দামপাড়ায় শায়িত প্রখ্যাত ওলীউল্লাহ হযরত গরীব উল্লাহ শাহ (রহ.)-এর বাৎসরিক পবিত্র উরস মোবারক।",
              color = SoftWhiteText.copy(alpha = 0.88f),
              fontSize = 12.5.sp,
              lineHeight = 18.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // =========================================================================
        // 5. SEARCH BAR & GRID ACTION BUTTON
        // =========================================================================
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Pill search bar
          Surface(
            modifier = Modifier
              .weight(1f)
              .height(48.dp)
              .clickable {
                showQuranModal = true
              },
            shape = RoundedCornerShape(24.dp),
            color = Color(0xFF16282E),
            border = BorderStroke(1.dp, Color(0xFF24454E))
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 14.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color(0xFF2DD4BF),
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = "কুরআন, হাদিস, দু'আ বা ফিচার খুঁজুন...",
                color = Color(0xFF6B8B94),
                fontSize = 13.sp
              )
            }
          }

          // Grid action button
          Surface(
            modifier = Modifier
              .size(48.dp)
              .clickable {
                Toast.makeText(context, "সকল ফিচার ও মেনু তালিকা", Toast.LENGTH_SHORT).show()
              },
            shape = CircleShape,
            color = Color(0xFF16282E),
            border = BorderStroke(1.5.dp, Color(0xFF2DD4BF))
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.GridView,
                contentDescription = "Features Grid",
                tint = Color(0xFF2DD4BF),
                modifier = Modifier.size(22.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // =========================================================================
        // 6. প্রিয় আমল (FAVORITE DEEDS) WITH 5 ISLAMIC ARCH CARDS
        // =========================================================================
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Left golden bar indicator
          Box(
            modifier = Modifier
              .size(width = 3.dp, height = 16.dp)
              .clip(RoundedCornerShape(1.5.dp))
              .background(GoldAccent)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "প্রিয় আমল",
            color = SoftWhiteText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.width(10.dp))
          HorizontalDivider(
            color = Color(0xFF163E30),
            thickness = 1.dp,
            modifier = Modifier.weight(1f)
          )
          Spacer(modifier = Modifier.width(10.dp))
          // Edit pencil button
          Surface(
            shape = CircleShape,
            color = Color(0xFF113628),
            modifier = Modifier
              .size(30.dp)
              .clickable {
                Toast.makeText(context, "আমল তালিকা পরিবর্তন করুন", Toast.LENGTH_SHORT).show()
              }
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit Deeds",
                tint = Color(0xFF34D399),
                modifier = Modifier.size(15.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 5 Islamic Mihrab Arch Cards
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          prioAmalList.forEach { item ->
            Column(
              modifier = Modifier
                .weight(1f)
                .clickable {
                  when (item.id) {
                    "prayer" -> Toast.makeText(context, "নামাজের সময়সূচি", Toast.LENGTH_SHORT).show()
                    "quran" -> showQuranModal = true
                    "hadith" -> Toast.makeText(context, "হাদিস ও সুন্নাহ সংকলন", Toast.LENGTH_SHORT).show()
                    "shajra" -> Toast.makeText(context, "শাজরা শরীফ মোবারক", Toast.LENGTH_SHORT).show()
                    "darood" -> Toast.makeText(context, "দরূদ শরীফ সংকলন", Toast.LENGTH_SHORT).show()
                  }
                },
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              // Mihrab Arch Container
              Surface(
                modifier = Modifier
                  .size(width = 62.dp, height = 66.dp),
                shape = IslamicArchShape(cornerRadius = 8f),
                color = Color(0xFF09251C),
                border = BorderStroke(1.2.dp, Color(0xFFC5A059).copy(alpha = 0.75f))
              ) {
                IslamicArchFrame(borderColor = Color(0xFFC5A059)) {
                  when (item.id) {
                    "prayer" -> PrayerTimeArt(tint = Color(0xFFD6B575))
                    "quran" -> QuranRehalArt(tint = Color(0xFFD6B575))
                    "hadith" -> TafseerQuranArt(tint = Color(0xFFD6B575))
                    "shajra" -> ShajraSharifArt(tint = Color(0xFFD6B575))
                    "darood" -> DaroodSharifArt(tint = Color(0xFFD6B575))
                    else -> QuranRehalArt(tint = Color(0xFFD6B575))
                  }
                }
              }

              Spacer(modifier = Modifier.height(6.dp))

              Text(
                text = item.title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = SoftWhiteText,
                textAlign = TextAlign.Center,
                maxLines = 1
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // =========================================================================
        // 7. বিজ্ঞপ্তি (NOTICE) SECTION
        // =========================================================================
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Campaign,
            contentDescription = null,
            tint = GoldAccent,
            modifier = Modifier.size(17.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "বিজ্ঞপ্তি",
            color = GoldAccent,
            fontSize = 13.5.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = " • ৯ অক্টোবর ২০২৬",
            color = MintText,
            fontSize = 12.sp
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "মনীষীদের জীবনী ডাটাবেজ আপডেট করা হয়েছে; আপনারা সেটিংস > ডাটাবেজ > মনীষীদের জীবনী ডাটাবেজ আপডেট করুন।",
          color = SoftWhiteText.copy(alpha = 0.85f),
          fontSize = 12.sp,
          lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        // =========================================================================
        // 8. QUICK CATEGORY TABS / BAR
        // =========================================================================
        Surface(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          color = Color(0xFF09221A),
          border = BorderStroke(1.dp, Color(0xFF1A4638))
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            // Tab 1: কুরআন মাজিদ
            Surface(
              modifier = Modifier
                .weight(1f)
                .clickable {
                  activeCategoryTab = "quran"
                  showQuranModal = true
                },
              shape = RoundedCornerShape(12.dp),
              color = if (activeCategoryTab == "quran") Color(0xFF175642) else Color.Transparent,
              border = if (activeCategoryTab == "quran") BorderStroke(1.2.dp, Color(0xFF2DD4BF)) else null
            ) {
              Row(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
              ) {
                Icon(
                  imageVector = Icons.AutoMirrored.Filled.MenuBook,
                  contentDescription = null,
                  tint = if (activeCategoryTab == "quran") Color.White else MintText,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                  text = "কুরআন মাজিদ",
                  color = if (activeCategoryTab == "quran") Color.White else MintText,
                  fontSize = 12.sp,
                  fontWeight = if (activeCategoryTab == "quran") FontWeight.Bold else FontWeight.Medium
                )
              }
            }

            // Tab 2: হাদিস ও ফিকহ
            Surface(
              modifier = Modifier
                .weight(1f)
                .clickable {
                  activeCategoryTab = "hadith"
                  Toast.makeText(context, "হাদিস ও ফিকহ সংকলন", Toast.LENGTH_SHORT).show()
                },
              shape = RoundedCornerShape(12.dp),
              color = if (activeCategoryTab == "hadith") Color(0xFF175642) else Color.Transparent,
              border = if (activeCategoryTab == "hadith") BorderStroke(1.2.dp, Color(0xFF2DD4BF)) else null
            ) {
              Row(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
              ) {
                Icon(
                  imageVector = Icons.AutoMirrored.Filled.LibraryBooks,
                  contentDescription = null,
                  tint = if (activeCategoryTab == "hadith") Color.White else MintText,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                  text = "হাদিস ও ফিকহ",
                  color = if (activeCategoryTab == "hadith") Color.White else MintText,
                  fontSize = 12.sp,
                  fontWeight = if (activeCategoryTab == "hadith") FontWeight.Bold else FontWeight.Medium
                )
              }
            }

            // Tab 3: ইবাদত ও সালাত
            Surface(
              modifier = Modifier
                .weight(1f)
                .clickable {
                  activeCategoryTab = "ibadat"
                  Toast.makeText(context, "ইবাদত ও সালাত নির্দেশনা", Toast.LENGTH_SHORT).show()
                },
              shape = RoundedCornerShape(12.dp),
              color = if (activeCategoryTab == "ibadat") Color(0xFF175642) else Color.Transparent,
              border = if (activeCategoryTab == "ibadat") BorderStroke(1.2.dp, Color(0xFF2DD4BF)) else null
            ) {
              Row(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Mosque,
                  contentDescription = null,
                  tint = if (activeCategoryTab == "ibadat") Color.White else MintText,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                  text = "ইবাদত ও সালাত",
                  color = if (activeCategoryTab == "ibadat") Color.White else MintText,
                  fontSize = 12.sp,
                  fontWeight = if (activeCategoryTab == "ibadat") FontWeight.Bold else FontWeight.Medium
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // =========================================================================
        // 9. SUB-CATEGORY ISLAMIC ARCH CARDS GRID (10 Items in 4 Columns)
        // matching App Main Activity Lower Part.jpeg
        // =========================================================================
        val currentSubItems = when (activeCategoryTab) {
          "hadith" -> hadithSubItems
          "ibadat" -> ibadatSubItems
          else -> quranSubItems
        }
        val subRows = currentSubItems.chunked(4)

        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          subRows.forEach { rowItems ->
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              rowItems.forEach { item ->
                Column(
                  modifier = Modifier
                    .weight(1f)
                    .clickable {
                      if (item.id.contains("quran") || item.id == "tafseer" || item.id == "hafezi" ||
                        item.id == "kolikata" || item.id == "tajweed" || item.id == "noorani"
                      ) {
                        showQuranModal = true
                      } else {
                        Toast.makeText(context, "${item.title} খোলা হচ্ছে...", Toast.LENGTH_SHORT).show()
                      }
                    },
                  horizontalAlignment = Alignment.CenterHorizontally
                ) {
                  Surface(
                    modifier = Modifier
                      .fillMaxWidth()
                      .height(68.dp),
                    shape = IslamicArchShape(cornerRadius = 8f),
                    color = Color(0xFF09251C),
                    border = BorderStroke(1.2.dp, Color(0xFFC5A059).copy(alpha = 0.75f))
                  ) {
                    IslamicArchFrame(borderColor = Color(0xFFC5A059)) {
                      when (item.id) {
                        "quran_majid" -> QuranRehalArt(tint = Color(0xFFD6B575))
                        "tafseer" -> TafseerQuranArt(tint = Color(0xFFD6B575))
                        "hafezi" -> HafeziQuranArt(tint = Color(0xFFD6B575))
                        "kolikata" -> CalcuttaQuranArt(tint = Color(0xFFD6B575))
                        "color_quran" -> ColorQuranArt(tint = Color(0xFFD6B575))
                        "hifzul" -> HifzulQuranArt(tint = Color(0xFFD6B575))
                        "bishoy" -> BishoybhittikQuranArt(tint = Color(0xFFD6B575))
                        "telawat" -> QuranTelawatArt(tint = Color(0xFFD6B575))
                        "tajweed" -> TajweedQuranArt(tint = Color(0xFFD6B575))
                        "noorani" -> NooraniQaidaArt(tint = Color(0xFFD6B575))
                        // Fallbacks for other tabs
                        "bukhari", "muslim", "abu_dawood", "tirmidhi", "nasai", "ibn_majah",
                        "mishkat", "riyad", "fatwa", "nurun_nabi" -> TafseerQuranArt(tint = Color(0xFFD6B575))
                        else -> QuranRehalArt(tint = Color(0xFFD6B575))
                      }
                    }
                  }

                  Spacer(modifier = Modifier.height(6.dp))

                  Text(
                    text = item.title,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = SoftWhiteText,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    lineHeight = 14.sp,
                    modifier = Modifier.fillMaxWidth()
                  )
                }
              }

              // Fill remaining spaces in row if rowItems.size < 4 (Row 3 has 2 items)
              repeat(4 - rowItems.size) {
                Spacer(modifier = Modifier.weight(1f))
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // =========================================================================
        // 10. HUZUR KEBLA HAZRAT SYED MUHAMMAD TAYYAB SHAH (RAH.) ZAKAT QUOTE CARD
        // matching App Main Activity Lower Part.jpeg
        // =========================================================================
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF104E3B)),
          border = BorderStroke(1.2.dp, Color(0xFFC5A059))
        ) {
          Box(modifier = Modifier.fillMaxWidth()) {
            // Watermark in bottom-left
            MosqueWatermark(
              modifier = Modifier
                .align(Alignment.BottomStart)
                .size(width = 160.dp, height = 110.dp)
                .offset(x = (-10).dp, y = 10.dp),
              tint = Color(0xFF6ED8B4).copy(alpha = 0.20f)
            )

            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
            ) {
              // Top row with quote mark in center and share on right
              Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                  text = "❝",
                  fontSize = 28.sp,
                  color = Color(0xFF7DE4BD),
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.align(Alignment.Center)
                )

                IconButton(
                  onClick = {
                    val sendIntent: Intent = Intent().apply {
                      action = Intent.ACTION_SEND
                      putExtra(
                        Intent.EXTRA_TEXT,
                        "«তোমাদের যাকাতকে চার ভাগ করে এক ভাগ জামেয়ার অসচ্ছল ও গরীব শিক্ষার্থীদের (তালাবাদের) দাও, আর বাকি তিন ভাগ তোমাদের এলাকার অন্যান্য হকদার অভাবীদের মাঝে বণ্টন করো।»\n— হযরত সৈয়দ মুহাম্মদ তৈয়ব শাহ্ (রহ.)"
                      )
                      type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(sendIntent, null))
                  },
                  modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterEnd)
                ) {
                  Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share Quote",
                    tint = Color.White.copy(alpha = 0.85f),
                    modifier = Modifier.size(18.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(14.dp))

              // Center quote text
              Text(
                text = "তোমাদের যাকাতকে চার ভাগ করে এক ভাগ জামেয়ার অসচ্ছল ও গরীব শিক্ষার্থীদের (তালাবাদের) দাও, আর বাকি তিন ভাগ তোমাদের এলাকার অন্যান্য হকদার অভাবীদের মাঝে বণ্টন করো।",
                fontSize = 14.5.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 23.sp,
                modifier = Modifier.fillMaxWidth()
              )

              Spacer(modifier = Modifier.height(14.dp))

              // Right-aligned author attribution
              Text(
                text = "- হযরত সৈয়দ মুহাম্মদ তৈয়ব শাহ্ (রহ.)",
                fontSize = 13.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFE2B755),
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))
      }
    }

    // Modal Dialogs
    if (showDistrictPickerModal) {
      QuickDistrictPickerModal(
        selectedDistrict = selectedDistrict,
        onDistrictSelected = { selectedDistrict = it },
        onDismiss = { showDistrictPickerModal = false }
      )
    }

    if (showPalettePickerModal) {
      QuickPalettePickerModal(
        currentPalette = currentPalette,
        onPaletteSelected = onPaletteChange,
        onDismiss = { showPalettePickerModal = false }
      )
    }

    if (showQiblaModal) {
      QiblaCompassDialog(onDismiss = { showQiblaModal = false })
    }

    if (showQuranModal) {
      QuranBrowserDialog(onDismiss = { showQuranModal = false })
    }

    if (showLanguagePickerModal) {
      LanguagePickerModal(
        currentLanguage = currentLanguage,
        onDismiss = { showLanguagePickerModal = false },
        onSelectLanguage = { newLang ->
          showLanguagePickerModal = false
          if (newLang != currentLanguage) {
            pendingLanguageChange = newLang
          }
        }
      )
    }

    pendingLanguageChange?.let { targetLang ->
      LanguageChangeDialog(
        targetLanguage = targetLang,
        onDismiss = {
          currentLanguage = targetLang
          pendingLanguageChange = null
        }
      )
    }
  }
}
