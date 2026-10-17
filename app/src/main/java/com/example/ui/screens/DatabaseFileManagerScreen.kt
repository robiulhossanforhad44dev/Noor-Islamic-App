package com.example.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.DownloadContentItem
import com.example.model.DownloadContentRepository
import com.example.ui.components.ContentItemIcon
import com.example.ui.components.MosqueWatermark
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DatabaseFileManagerScreen(
  onBack: () -> Unit
) {
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()
  val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
  val navBarPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

  BackHandler { onBack() }

  val allItems = DownloadContentRepository.allItems

  // Download states: itemId -> isDownloaded
  val downloadedMap = remember {
    mutableStateMapOf<String, Boolean>()
  }

  // Downloading state: itemId -> isDownloading
  val downloadingMap = remember {
    mutableStateMapOf<String, Boolean>()
  }

  // Progress state: itemId -> progress Float (0f to 1f)
  val progressMap = remember {
    mutableStateMapOf<String, Float>()
  }

  // Pending item for Image 3's Download Confirmation Dialog
  var pendingDownloadItem by remember { mutableStateOf<DownloadContentItem?>(null) }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFF04130E))
      .testTag("database_file_manager_screen")
  ) {
    Column(modifier = Modifier.fillMaxSize()) {

      // =========================================================================
      // 1. TOP APP BAR (ডাটাবেস ও ফাইল ম্যানেজার)
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
            modifier = Modifier.testTag("db_manager_back_button")
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
            text = "ডাটাবেস ও ফাইল ম্যানেজার",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
          )

          // Refresh button on top right (as in screenshots 1 & 2)
          IconButton(
            onClick = {
              Toast.makeText(context, "ডাটাবেস তালিকা রিফ্রেশ করা হচ্ছে...", Toast.LENGTH_SHORT).show()
            }
          ) {
            Icon(
              imageVector = Icons.Default.Refresh,
              contentDescription = "Refresh",
              tint = Color.White,
              modifier = Modifier.size(24.dp)
            )
          }
        }
      }

      // =========================================================================
      // SCROLLABLE LIST OF CONTENT ITEMS
      // =========================================================================
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(bottom = navBarPadding + 32.dp)
      ) {

        // TOP HEADER BANNER (ডেটাবেজ ও অফলাইন ফাইল ম্যানেজার)
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(Color(0xFF0F644A))
            .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 26.dp)
        ) {
          // Mosque Watermark on right
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
                  imageVector = Icons.Default.FileDownload,
                  contentDescription = "Download Icon",
                  tint = Color(0xFFE5A823),
                  modifier = Modifier.size(26.dp)
                )
              }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "ডেটাবেজ ও অফলাইন ফাইল ম্যানেজার",
                color = Color.White,
                fontSize = 18.5.sp,
                fontWeight = FontWeight.Bold
              )
              Spacer(modifier = Modifier.height(3.dp))
              Text(
                text = "কুরআন, হাদিস ও ইসলামিক কিতাবসমূহের অফলাইন ফাইল ম্যানেজমেন্ট",
                color = Color(0xFFB2E0D0),
                fontSize = 12.sp,
                lineHeight = 17.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // GOLDEN WARNING / NOTICE CARD (জরুরি পরামর্শ: ডাটাবেস নিয়মিত আপডেট রাখুন)
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF1A2215)),
          border = BorderStroke(1.2.dp, Color(0xFFC5A059))
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Surface(
                modifier = Modifier.size(38.dp),
                shape = CircleShape,
                color = Color(0xFF383212)
              ) {
                Box(contentAlignment = Alignment.Center) {
                  Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    tint = Color(0xFFE5A823),
                    modifier = Modifier.size(20.dp)
                  )
                }
              }

              Spacer(modifier = Modifier.width(12.dp))

              Text(
                text = "জরুরি পরামর্শ: ডাটাবেস নিয়মিত আপডেট রাখুন",
                color = Color(0xFFE5A823),
                fontSize = 14.5.sp,
                fontWeight = FontWeight.Bold
              )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
              text = "ওয়াইফাই (Wi-Fi) বা ভালো নেটওয়ার্ক সংযোগ পেলে অনুগ্রহ করে আপনার ডাউনলোডকৃত ডাটাবেসগুলো আপডেট করে নিন। কুরআন মাজিদ, হাদিস শরীফ ও বিভিন্ন সুন্নাহর তথ্যে কোনো প্রকার মুদ্রণপ্রমাদ বা তথ্যগত ভুলত্রুটি নজরে আসামাত্রই আমরা তা দ্রুত সংশোধন করে ডাটাবেস আপডেট দিয়ে থাকি। নিয়মিত আপডেটের ফলে আপনি সবসময় সম্পূর্ণ নির্ভুল ও নির্ভরযোগ্য তথ্য পাঠ করতে পারবেন।",
              color = Color(0xFFD6DEC9),
              fontSize = 12.5.sp,
              lineHeight = 19.sp
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // CONTENT ITEMS LIST (matching Image 1 & Image 2)
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          allItems.forEach { item ->
            val isDownloaded = downloadedMap[item.id] == true
            val isDownloading = downloadingMap[item.id] == true
            val progress = progressMap[item.id] ?: 0f

            Card(
              modifier = Modifier
                .fillMaxWidth()
                .clickable {
                  if (!isDownloaded && !isDownloading) {
                    pendingDownloadItem = item
                  }
                },
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = Color(0xFF072118)),
              border = BorderStroke(1.dp, Color(0xFF133F31))
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  // Left Icon
                  Surface(
                    modifier = Modifier.size(46.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF0C2B21)
                  ) {
                    Box(contentAlignment = Alignment.Center) {
                      ContentItemIcon(type = item.iconType, size = 32.dp)
                    }
                  }

                  Spacer(modifier = Modifier.width(14.dp))

                  // Title & Size Column
                  Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                      Text(
                        text = item.title,
                        color = Color.White,
                        fontSize = 14.5.sp,
                        fontWeight = FontWeight.Bold
                      )

                      // Image 5: Green Checkmark when downloaded
                      if (isDownloaded) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                          imageVector = Icons.Default.CheckCircle,
                          contentDescription = "Downloaded",
                          tint = Color(0xFF10B981),
                          modifier = Modifier.size(17.dp)
                        )
                      }
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                      text = item.sizeText,
                      color = Color(0xFF94B8A9),
                      fontSize = 12.sp
                    )
                  }

                  Spacer(modifier = Modifier.width(8.dp))

                  // Right Side Actions depending on state
                  when {
                    // Image 4: Downloading Spinner on the right
                    isDownloading -> {
                      CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color(0xFF2DD4BF),
                        strokeWidth = 2.5.dp
                      )
                    }

                    // Image 5: Downloaded -> Refresh & Delete Buttons
                    isDownloaded -> {
                      Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                          onClick = {
                            coroutineScope.launch {
                              downloadedMap[item.id] = false
                              downloadingMap[item.id] = true
                              progressMap[item.id] = 0f
                              for (step in 1..10) {
                                delay(180)
                                progressMap[item.id] = step / 10f
                              }
                              downloadingMap[item.id] = false
                              downloadedMap[item.id] = true
                              Toast.makeText(context, "${item.title} আপডেট সম্পন্ন হয়েছে!", Toast.LENGTH_SHORT).show()
                            }
                          },
                          modifier = Modifier.size(34.dp)
                        ) {
                          Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Update",
                            tint = Color(0xFF2DD4BF),
                            modifier = Modifier.size(22.dp)
                          )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        IconButton(
                          onClick = {
                            downloadedMap[item.id] = false
                            progressMap[item.id] = 0f
                            Toast.makeText(context, "${item.title} ডাটাবেস মুছে ফেলা হয়েছে।", Toast.LENGTH_SHORT).show()
                          },
                          modifier = Modifier.size(34.dp)
                        ) {
                          Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color(0xFFF43F5E),
                            modifier = Modifier.size(20.dp)
                          )
                        }
                      }
                    }

                    // Image 1 & 2: Not Downloaded -> Download Arrow Button
                    else -> {
                      IconButton(
                        onClick = {
                          pendingDownloadItem = item
                        },
                        modifier = Modifier.size(34.dp)
                      ) {
                        Icon(
                          imageVector = Icons.Default.FileDownload,
                          contentDescription = "Download",
                          tint = Color(0xFF2DD4BF),
                          modifier = Modifier.size(24.dp)
                        )
                      }
                    }
                  }
                }

                // Image 4: Horizontal Progress Bar & "ডাউনলোড হচ্ছে..." text when downloading
                if (isDownloading) {
                  Spacer(modifier = Modifier.height(10.dp))

                  LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                      .fillMaxWidth()
                      .height(4.dp)
                      .clip(RoundedCornerShape(2.dp)),
                    color = Color(0xFF2DD4BF),
                    trackColor = Color(0xFF0F3E30),
                    strokeCap = StrokeCap.Round
                  )

                  Spacer(modifier = Modifier.height(6.dp))

                  Text(
                    text = "ডাউনলোড হচ্ছে...",
                    color = Color(0xFF2DD4BF),
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.Medium
                  )
                }
              }
            }
          }
        }
      }
    }

    // =========================================================================
    // IMAGE 3: DOWNLOAD POPUP / MODAL DIALOG
    // =========================================================================
    pendingDownloadItem?.let { item ->
      Dialog(onDismissRequest = { pendingDownloadItem = null }) {
        Card(
          shape = RoundedCornerShape(24.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2623)),
          border = BorderStroke(1.2.dp, Color(0xFF2D5A4A)),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .testTag("download_confirm_dialog")
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp)
          ) {
            Text(
              text = "ডাউনলোড",
              color = Color.White,
              fontSize = 21.sp,
              fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
              text = "আপনি কি '${item.title}' ডাউনলোড করতে চান? (${item.sizeText})",
              color = Color(0xFFD1E7DD),
              fontSize = 14.5.sp,
              lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.End,
              verticalAlignment = Alignment.CenterVertically
            ) {
              // বাতিল (Cancel) Text Button
              TextButton(
                onClick = { pendingDownloadItem = null }
              ) {
                Text(
                  text = "বাতিল",
                  color = Color(0xFFB0D0C5),
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Medium
                )
              }

              Spacer(modifier = Modifier.width(12.dp))

              // হ্যাঁ, ডাউনলোড করুন (Yes, Download) Filled Button
              Button(
                onClick = {
                  val targetItem = item
                  pendingDownloadItem = null

                  // Launch download simulation coroutine
                  coroutineScope.launch {
                    downloadingMap[targetItem.id] = true
                    progressMap[targetItem.id] = 0.05f

                    for (step in 1..10) {
                      delay(220)
                      progressMap[targetItem.id] = (step * 0.1f).coerceAtMost(1f)
                    }

                    downloadingMap[targetItem.id] = false
                    downloadedMap[targetItem.id] = true
                    Toast.makeText(context, "${targetItem.title} ডাউনলোড সম্পন্ন হয়েছে!", Toast.LENGTH_SHORT).show()
                  }
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = Color(0xFF157B5A),
                  contentColor = Color.White
                ),
                modifier = Modifier.height(42.dp)
              ) {
                Text(
                  text = "হ্যাঁ, ডাউনলোড করুন",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }
      }
    }
  }
}
