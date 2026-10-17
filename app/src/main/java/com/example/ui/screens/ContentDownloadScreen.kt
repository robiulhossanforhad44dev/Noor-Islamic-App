package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.DownloadContentItem
import com.example.model.DownloadContentRepository
import com.example.model.toBengaliDigits
import com.example.model.toBengaliFormatted
import com.example.ui.components.ContentItemIcon
import com.example.ui.components.OnboardingHeader
import com.example.ui.theme.ButtonGreen
import com.example.ui.theme.CardBackgroundSelected
import com.example.ui.theme.CardBackgroundUnselected
import com.example.ui.theme.CardBorderSelected
import com.example.ui.theme.CardBorderUnselected
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.MintText
import com.example.ui.theme.NightGreenBackground
import com.example.ui.theme.RadioUnselectedBorder
import com.example.ui.theme.SageText
import com.example.ui.theme.SoftWhiteText
import kotlinx.coroutines.delay

@Composable
fun ContentDownloadScreen(
  currentStep: Int = 2,
  totalSteps: Int = 6,
  language: AppLanguage = AppLanguage.BANGLA,
  onBack: () -> Unit,
  onProceedToNextStep: () -> Unit,
  onLanguageBadgeClick: () -> Unit = {}
) {
  val navBarPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

  val allItems = remember { DownloadContentRepository.allItems }
  // Map of selected items: key=id, value=boolean. Defaults to Quran DB selected or customizable
  val selectedMap = remember {
    mutableStateMapOf<String, Boolean>().apply {
      // Initially, select the first item (Quran DB) or none to match screenshot
      put("quran_db", true)
    }
  }

  val selectedCount by remember {
    derivedStateOf {
      selectedMap.values.count { it }
    }
  }

  val selectedMb by remember {
    derivedStateOf {
      allItems.filter { selectedMap[it.id] == true }.sumOf { it.sizeMb }
    }
  }

  val isAllSelected by remember {
    derivedStateOf {
      selectedCount == allItems.size
    }
  }

  // Two screen modes: "SELECTION" (App 2nd Screen.jpeg) and "DOWNLOADING" (App 2nd Screen Content Downalod On Server.jpeg)
  var isDownloadingMode by remember { mutableStateOf(false) }

  // Download simulation progress
  var downloadProgress by remember { mutableFloatStateOf(0.21f) }
  var downloadCompleted by remember { mutableStateOf(false) }
  var currentDownloadingItemName by remember { mutableStateOf("কুরআন মাজিদ ডাটাবেস") }

  // Animated download progress when in downloading mode
  LaunchedEffect(isDownloadingMode) {
    if (isDownloadingMode) {
      downloadCompleted = false
      val selectedList = allItems.filter { selectedMap[it.id] == true }.ifEmpty { listOf(allItems.first()) }

      // Start at 21% matching the exact screenshot, then smoothly progress through items
      downloadProgress = 0.21f
      currentDownloadingItemName = selectedList.first().title

      val totalStepsCount = 20
      for (i in 4..totalStepsCount) {
        delay(320)
        downloadProgress = (i.toFloat() / totalStepsCount).coerceIn(0f, 1f)
        val itemIndex = ((i - 4) * selectedList.size / (totalStepsCount - 3)).coerceIn(0, selectedList.lastIndex)
        currentDownloadingItemName = selectedList[itemIndex].title
      }
      delay(400)
      downloadProgress = 1f
      downloadCompleted = true
    }
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(NightGreenBackground)
      .testTag("content_download_screen")
  ) {
    Column(modifier = Modifier.fillMaxSize()) {
      // Top Curved Header
      OnboardingHeader(
        currentStep = currentStep,
        totalSteps = totalSteps,
        language = language,
        title = if (language == AppLanguage.BANGLA) "প্রয়োজনীয় কন্টেন্ট" else "Essential Content",
        subtitle = if (language == AppLanguage.BANGLA)
          "অ্যাপের কন্টেন্টগুলো অফলাইনে পড়ার জন্য ডাউনলোড করুন"
        else
          "Download app contents for reading offline",
        onLanguageBadgeClick = onLanguageBadgeClick
      )

      AnimatedContent(
        targetState = isDownloadingMode,
        transitionSpec = {
          fadeIn(animationSpec = tween(280)).togetherWith(fadeOut(animationSpec = tween(280)))
        },
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
        label = "downloadModeTransition"
      ) { downloading ->
        if (downloading) {
          // --- Server Downloading State (App 2nd Screen Content Downalod On Server.jpeg) ---
          ServerDownloadingView(
            progress = downloadProgress,
            isCompleted = downloadCompleted,
            currentContentName = currentDownloadingItemName,
            modifier = Modifier
              .fillMaxSize()
              .padding(horizontal = 20.dp, vertical = 20.dp)
          )
        } else {
          // --- Content Selection State (App 2nd Screen.jpeg) ---
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(horizontal = 16.dp, vertical = 12.dp)
          ) {
            // Action Row: "নির্বাচিত: ০টি (~০.০ MB)" and "সব নির্বাচন"
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              // Selected count & size pill
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF09251D),
                border = BorderStroke(1.dp, Color(0xFF144635))
              ) {
                Text(
                  text = "নির্বাচিত: ${selectedCount.toBengaliDigits()}টি (~${selectedMb.toBengaliFormatted()} MB)",
                  color = MintText,
                  fontSize = 12.5.sp,
                  fontWeight = FontWeight.Medium,
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
              }

              // "সব নির্বাচন" / Select All toggle
              Row(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .clickable {
                    val target = !isAllSelected
                    allItems.forEach { selectedMap[it.id] = target }
                  }
                  .padding(horizontal = 6.dp, vertical = 4.dp)
                  .testTag("select_all_button"),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = if (isAllSelected) Icons.Default.CheckCircle else Icons.Default.SelectAll,
                  contentDescription = null,
                  tint = if (isAllSelected) GoldAccent else SageText,
                  modifier = Modifier.size(17.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (isAllSelected) "সব বাতিল" else "সব নির্বাচন",
                  color = if (isAllSelected) GoldAccent else SageText,
                  fontSize = 13.5.sp,
                  fontWeight = FontWeight.SemiBold
                )
              }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 14 Downloadable Content Items List inside unified rounded Card
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
              shape = RoundedCornerShape(16.dp),
              border = BorderStroke(1.dp, CardBorderUnselected),
              colors = CardDefaults.cardColors(containerColor = Color(0xFF071F18))
            ) {
              LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 4.dp)
              ) {
                itemsIndexed(allItems) { index, item ->
                  val isSelected = selectedMap[item.id] == true
                  DownloadItemRow(
                    item = item,
                    isSelected = isSelected,
                    onToggle = {
                      selectedMap[item.id] = !isSelected
                    }
                  )

                  if (index < allItems.lastIndex) {
                    HorizontalDivider(
                      color = Color(0xFF0C2B22),
                      thickness = 0.6.dp,
                      modifier = Modifier.padding(horizontal = 12.dp)
                    )
                  }
                }
              }
            }
          }
        }
      }

      // Bottom Bar Navigation
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(
            start = 16.dp,
            end = 16.dp,
            bottom = navBarPadding + 16.dp,
            top = 8.dp
          )
      ) {
        if (isDownloadingMode) {
          // Bottom button on Downloading screen: "ডাউনলোড ও পরবর্তী ➔" / "পরবর্তী ধাপ ➔"
          Button(
            onClick = {
              if (downloadCompleted) {
                onProceedToNextStep()
              } else {
                // If user clicks while downloading, skip to completed
                downloadProgress = 1f
                downloadCompleted = true
              }
            },
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp)
              .testTag("download_and_next_button"),
            shape = RoundedCornerShape(26.dp),
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
                text = if (downloadCompleted) "পরবর্তী ধাপ" else "ডাউনলোড ও পরবর্তী",
                fontSize = 16.sp,
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
        } else {
          // Bottom bar on Selection screen: "← পেছনে" and "পরবর্তী ধাপ ➔"
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // "← পেছনে" Button
            OutlinedButton(
              onClick = onBack,
              modifier = Modifier
                .weight(0.36f)
                .height(50.dp)
                .testTag("back_button_step2"),
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
                  text = "পেছনে",
                  fontSize = 14.5.sp,
                  fontWeight = FontWeight.Medium,
                  color = SoftWhiteText
                )
              }
            }

            // "পরবর্তী ধাপ ➔" Button (starts download flow)
            Button(
              onClick = {
                // Ensure at least one item is chosen if none selected
                if (selectedCount == 0) {
                  selectedMap["quran_db"] = true
                }
                isDownloadingMode = true
              },
              modifier = Modifier
                .weight(0.64f)
                .height(50.dp)
                .testTag("start_download_button"),
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
                  text = "পরবর্তী ধাপ",
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
  }
}

@Composable
fun ServerDownloadingView(
  progress: Float,
  isCompleted: Boolean,
  currentContentName: String,
  modifier: Modifier = Modifier
) {
  val percent = (progress * 100).toInt().coerceIn(0, 100)

  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Large Dark Download Card matching App 2nd Screen Content Downalod On Server.jpeg
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .testTag("downloading_progress_card"),
      shape = RoundedCornerShape(18.dp),
      border = BorderStroke(1.dp, Color(0xFF13382C)),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF081E17))
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 36.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Circular Progress Ring with Percentage in center
        Box(
          modifier = Modifier.size(112.dp),
          contentAlignment = Alignment.Center
        ) {
          // Circular Track (Darker green ring)
          CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF0C3224),
            strokeWidth = 6.dp
          )

          // Glowing Green Progress Arc
          CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF1AC78D),
            strokeWidth = 6.dp,
            strokeCap = StrokeCap.Round
          )

          // Center Percentage text in Bengali (e.g. "২১%")
          Text(
            text = "${percent.toBengaliDigits()}%",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = SoftWhiteText,
            textAlign = TextAlign.Center
          )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Horizontal Linear Progress Bar
        LinearProgressIndicator(
          progress = { progress },
          modifier = Modifier
            .fillMaxWidth(0.92f)
            .height(6.dp)
            .clip(RoundedCornerShape(3.dp)),
          color = Color(0xFF1AC78D),
          trackColor = Color(0xFF0E3828),
          strokeCap = StrokeCap.Round
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Status Text below progress bar (e.g. "কুরআন মাজিদ ডাটাবেস ডাউনলোড হচ্ছে...")
        Text(
          text = if (isCompleted)
            "সমস্ত ডাটাবেস সফলভাবে ডাউনলোড সম্পন্ন হয়েছে! ✓"
          else
            "$currentContentName ডাউনলোড হচ্ছে...",
          fontSize = 13.5.sp,
          fontWeight = FontWeight.Medium,
          color = if (isCompleted) GoldAccent else MintText,
          textAlign = TextAlign.Center
        )
      }
    }
  }
}

@Composable
fun DownloadItemRow(
  item: DownloadContentItem,
  isSelected: Boolean,
  onToggle: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onToggle
      )
      .padding(horizontal = 14.dp, vertical = 11.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Custom Checkbox: square with rounded corners
    Box(
      modifier = Modifier
        .size(20.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(if (isSelected) GoldAccent else Color.Transparent)
        .clickable(
          interactionSource = remember { MutableInteractionSource() },
          indication = null,
          onClick = onToggle
        ),
      contentAlignment = Alignment.Center
    ) {
      if (isSelected) {
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = "Selected",
          tint = Color(0xFF09241A),
          modifier = Modifier.size(14.dp)
        )
      } else {
        Surface(
          modifier = Modifier.size(20.dp),
          shape = RoundedCornerShape(4.dp),
          color = Color.Transparent,
          border = BorderStroke(1.2.dp, RadioUnselectedBorder)
        ) {}
      }
    }

    Spacer(modifier = Modifier.width(12.dp))

    // Distinctive Content Item Icon
    ContentItemIcon(
      type = item.iconType,
      size = 34.dp
    )

    Spacer(modifier = Modifier.width(12.dp))

    // Title
    Text(
      text = item.title,
      fontSize = 13.5.sp,
      fontWeight = FontWeight.Medium,
      color = Color(0xFFE8F5F0),
      modifier = Modifier.weight(1f)
    )

    Spacer(modifier = Modifier.width(8.dp))

    // Size Pill (e.g. "~৯.৩৩ মেগাবাইট")
    Surface(
      shape = RoundedCornerShape(6.dp),
      color = Color(0xFF09261E),
      border = BorderStroke(0.6.dp, Color(0xFF134232))
    ) {
      Text(
        text = item.sizeText,
        fontSize = 11.sp,
        color = Color(0xFF7CB8A3),
        modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp)
      )
    }
  }
}
