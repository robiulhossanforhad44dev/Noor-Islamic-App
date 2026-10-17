package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.IslamicColorPalette
import com.example.model.LocationRepository
import com.example.ui.theme.AppTheme
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MintText
import com.example.ui.theme.SageText
import com.example.ui.theme.SoftWhiteText

data class SurahPreviewItem(
  val number: Int,
  val nameArabic: String,
  val nameBangla: String,
  val meaningBangla: String,
  val totalVerses: Int,
  val isMakki: Boolean
)

val sampleSurahList = listOf(
  SurahPreviewItem(1, "الفاتحة", "আল-ফাতিহা", "সূচনা", 7, true),
  SurahPreviewItem(2, "البقرة", "আল-বাকারা", "গাভী", 286, false),
  SurahPreviewItem(3, "آل عمران", "আল-ইমরান", "ইমরানের পরিবার", 200, false),
  SurahPreviewItem(18, "الكهف", "আল-কাহফ", "গুহা", 110, true),
  SurahPreviewItem(36, "يس", "ইয়াসীন", "ইয়াসীন", 83, true),
  SurahPreviewItem(55, "الرحمن", "আর-রহমান", "পরম করুণাময়", 78, false),
  SurahPreviewItem(56, "الواقعة", "আল-ওয়াকিয়া", "নিশ্চিত ঘটনা", 96, true),
  SurahPreviewItem(67, "الملك", "আল-মুলক", "সার্বভৌম কর্তৃত্ব", 30, true),
  SurahPreviewItem(112, "الإخلاص", "আল-ইখলাস", "একনিষ্ঠতা", 4, true),
  SurahPreviewItem(113, "الفلق", "আল-ফালাক", "নিশিভোর", 5, true),
  SurahPreviewItem(114, "الناس", "আন-নাস", "মানবজাতি", 6, true)
)

@Composable
fun QiblaCompassDialog(
  onDismiss: () -> Unit
) {
  var compassHeading by remember { mutableStateOf(278f) }
  val animatedAngle by animateFloatAsState(
    targetValue = -compassHeading,
    animationSpec = tween(600),
    label = "qiblaRotation"
  )

  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = AppTheme.colors.cardBackgroundSelected),
      border = BorderStroke(1.5.dp, AppTheme.colors.cardBorderSelected)
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.CompassCalibration,
              contentDescription = null,
              tint = GoldAccent,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "কিবলা কম্পাস",
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              color = SoftWhiteText
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = SageText)
          }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = "মক্কার পবিত্র কাবার সঠিক দিকনির্দেশনা",
          fontSize = 13.sp,
          color = MintText
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Compass Dial Visual
        Box(
          modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .background(
              brush = Brush.radialGradient(
                listOf(
                  AppTheme.colors.cardBackgroundUnselected,
                  AppTheme.colors.background
                )
              )
            ),
          contentAlignment = Alignment.Center
        ) {
          // Compass ring
          Surface(
            modifier = Modifier.size(196.dp),
            shape = CircleShape,
            color = Color.Transparent,
            border = BorderStroke(2.dp, AppTheme.colors.cardBorderSelected.copy(alpha = 0.5f))
          ) {}

          // Rotating needle
          Column(
            modifier = Modifier
              .size(170.dp)
              .rotate(animatedAngle),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
          ) {
            // Kaaba Pointer (North / Kaaba)
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Box(
                modifier = Modifier
                  .size(28.dp)
                  .clip(RoundedCornerShape(4.dp))
                  .background(Color.Black),
                contentAlignment = Alignment.Center
              ) {
                Text(text = "🕋", fontSize = 16.sp)
              }
              Text(
                text = "ক্বিবলা",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = GoldAccent
              )
            }

            // Pivot Center
            Box(
              modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(GoldAccent)
            )

            // South Pointer
            Text(
              text = "S",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = SageText
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = AppTheme.colors.cardBackgroundUnselected,
          border = BorderStroke(1.dp, AppTheme.colors.cardBorderUnselected)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Explore, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "কিবলার দিক: ২৭৮° পশ্চিম-উত্তর-পশ্চিম",
              fontSize = 13.5.sp,
              fontWeight = FontWeight.SemiBold,
              color = SoftWhiteText
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
          onClick = onDismiss,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("ঠিক আছে", color = GoldAccent, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun QuranBrowserDialog(
  onDismiss: () -> Unit
) {
  var searchQuery by remember { mutableStateOf("") }
  val filteredSurahs = remember(searchQuery) {
    if (searchQuery.isBlank()) sampleSurahList
    else sampleSurahList.filter {
      it.nameBangla.contains(searchQuery, ignoreCase = true) ||
        it.meaningBangla.contains(searchQuery, ignoreCase = true) ||
        it.nameArabic.contains(searchQuery)
    }
  }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .height(520.dp)
        .padding(12.dp),
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = AppTheme.colors.cardBackgroundSelected),
      border = BorderStroke(1.5.dp, AppTheme.colors.cardBorderSelected)
    ) {
      Column(modifier = Modifier.padding(18.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.MenuBook,
              contentDescription = null,
              tint = GoldAccent,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "আল-কুরআন মাজিদ",
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              color = SoftWhiteText
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = SageText)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = searchQuery,
          onValueChange = { searchQuery = it },
          placeholder = { Text("সূরা খুঁজুন (যেমন: ইয়াসীন, রহমান)...", color = SageText, fontSize = 13.sp) },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
          modifier = Modifier.weight(1f),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          items(filteredSurahs) { surah ->
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = AppTheme.colors.cardBackgroundUnselected,
              border = BorderStroke(1.dp, AppTheme.colors.cardBorderUnselected),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier
                  .padding(12.dp)
                  .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(34.dp)
                      .clip(CircleShape)
                      .background(AppTheme.colors.primary.copy(alpha = 0.35f)),
                    contentAlignment = Alignment.Center
                  ) {
                    Text(
                      text = "${surah.number}",
                      fontSize = 13.sp,
                      fontWeight = FontWeight.Bold,
                      color = GoldAccent
                    )
                  }
                  Spacer(modifier = Modifier.width(10.dp))
                  Column {
                    Text(
                      text = surah.nameBangla,
                      fontSize = 14.5.sp,
                      fontWeight = FontWeight.Bold,
                      color = SoftWhiteText
                    )
                    Text(
                      text = "${surah.meaningBangla} • ${surah.totalVerses} আয়াত (${if (surah.isMakki) "মাক্কী" else "মাদানী"})",
                      fontSize = 11.5.sp,
                      color = MintText
                    )
                  }
                }

                Text(
                  text = surah.nameArabic,
                  fontSize = 18.sp,
                  fontWeight = FontWeight.Bold,
                  color = GoldAccent,
                  textAlign = TextAlign.Right
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
          onClick = onDismiss,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("বন্ধ করুন", color = GoldAccent, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun QuickDistrictPickerModal(
  selectedDistrict: String,
  onDistrictSelected: (String) -> Unit,
  onDismiss: () -> Unit
) {
  var searchQuery by remember { mutableStateOf("") }
  val districts = remember(searchQuery) {
    if (searchQuery.isBlank()) LocationRepository.bangladeshDistricts
    else LocationRepository.bangladeshDistricts.filter { it.contains(searchQuery) }
  }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .height(480.dp)
        .padding(12.dp),
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = AppTheme.colors.cardBackgroundSelected),
      border = BorderStroke(1.5.dp, AppTheme.colors.cardBorderSelected)
    ) {
      Column(modifier = Modifier.padding(18.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.LocationOn,
              contentDescription = null,
              tint = GoldAccent,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "জেলা নির্বাচন করুন",
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold,
              color = SoftWhiteText
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = SageText)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = searchQuery,
          onValueChange = { searchQuery = it },
          placeholder = { Text("জেলা খুঁজুন (যেমন: ঢাকা, চট্টগ্রাম)...", color = SageText, fontSize = 13.sp) },
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
          modifier = Modifier.weight(1f),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          items(districts) { district ->
            val isSelected = district == selectedDistrict
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = if (isSelected) AppTheme.colors.primary.copy(alpha = 0.4f) else AppTheme.colors.cardBackgroundUnselected,
              border = BorderStroke(
                1.dp,
                if (isSelected) AppTheme.colors.cardBorderSelected else AppTheme.colors.cardBorderUnselected
              ),
              modifier = Modifier
                .fillMaxWidth()
                .clickable {
                  onDistrictSelected(district)
                  onDismiss()
                }
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Text(
                  text = district,
                  fontSize = 14.5.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  color = if (isSelected) GoldAccent else SoftWhiteText
                )
                if (isSelected) {
                  Text("✓ নির্বাচিত", fontSize = 12.sp, color = GoldAccent, fontWeight = FontWeight.Bold)
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun QuickPalettePickerModal(
  currentPalette: IslamicColorPalette,
  onPaletteSelected: (IslamicColorPalette) -> Unit,
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = AppTheme.colors.cardBackgroundSelected),
      border = BorderStroke(1.5.dp, AppTheme.colors.cardBorderSelected)
    ) {
      Column(modifier = Modifier.padding(18.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Palette, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "কালার প্যালেট থিম",
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold,
              color = SoftWhiteText
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = SageText)
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          IslamicColorPalette.values().forEach { palette ->
            val isSelected = palette == currentPalette
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = if (isSelected) palette.colors.primary.copy(alpha = 0.35f) else AppTheme.colors.cardBackgroundUnselected,
              border = BorderStroke(
                if (isSelected) 1.5.dp else 1.dp,
                if (isSelected) palette.colors.cardBorderSelected else AppTheme.colors.cardBorderUnselected
              ),
              modifier = Modifier
                .fillMaxWidth()
                .clickable {
                  onPaletteSelected(palette)
                  onDismiss()
                }
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Box(
                    modifier = Modifier
                      .size(22.dp)
                      .clip(CircleShape)
                      .background(palette.primaryColor)
                  )
                  Spacer(modifier = Modifier.width(10.dp))
                  Text(
                    text = palette.displayName,
                    fontSize = 14.5.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = SoftWhiteText
                  )
                }
                if (isSelected) {
                  Text("সক্রিয়", fontSize = 12.sp, color = GoldAccent, fontWeight = FontWeight.Bold)
                }
              }
            }
          }
        }
      }
    }
  }
}
