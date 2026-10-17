package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.ImportContacts
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.model.ContentIconType

@Composable
fun ContentItemIcon(
  type: ContentIconType,
  modifier: Modifier = Modifier,
  size: Dp = 36.dp
) {
  val (icon, tint) = when (type) {
    ContentIconType.QURAN -> Icons.AutoMirrored.Filled.MenuBook to Color(0xFF62D2A9)
    ContentIconType.HADITH -> Icons.Default.HistoryEdu to Color(0xFF7CE4BF)
    ContentIconType.FATWA -> Icons.Default.CollectionsBookmark to Color(0xFF72D8B3)
    ContentIconType.KITAB -> Icons.Default.LibraryBooks to Color(0xFF6ED1AD)
    ContentIconType.BOOKS -> Icons.Default.AutoStories to Color(0xFF7ADCB7)
    ContentIconType.NOORANI -> Icons.Default.ImportContacts to Color(0xFF6CD0AC)
    ContentIconType.KOLKATA -> Icons.Default.Book to Color(0xFF71D5B1)
    ContentIconType.TAJWEED -> Icons.Default.MenuBook to Color(0xFFE5A823)
    ContentIconType.TAFSEER -> Icons.Default.BookmarkBorder to Color(0xFF6ED1AD)
    ContentIconType.SUNNAH -> Icons.Default.VolunteerActivism to Color(0xFF76D8B4)
    ContentIconType.BIOGRAPHY -> Icons.Default.Mosque to Color(0xFF74D6B2)
    ContentIconType.LYRICS -> Icons.Default.EditNote to Color(0xFF6BD0AB)
    ContentIconType.KHUTBAH -> Icons.Default.RecordVoiceOver to Color(0xFF7ADCB7)
    ContentIconType.SALAWAT -> Icons.Default.LocalFlorist to Color(0xFFE5A823)
  }

  Box(
    modifier = modifier
      .size(size)
      .clip(RoundedCornerShape(8.dp))
      .background(Color(0xFF0C2B21)),
    contentAlignment = Alignment.Center
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = tint.copy(alpha = 0.85f),
      modifier = Modifier.size(size * 0.58f)
    )
  }
}
