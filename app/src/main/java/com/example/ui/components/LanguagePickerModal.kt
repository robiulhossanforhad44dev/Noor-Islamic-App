package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.AppLanguage
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.SageText
import com.example.ui.theme.SoftWhiteText

@Composable
fun LanguagePickerModal(
  currentLanguage: AppLanguage,
  onDismiss: () -> Unit,
  onSelectLanguage: (AppLanguage) -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth(0.92f)
        .testTag("language_picker_modal"),
      shape = RoundedCornerShape(22.dp),
      border = BorderStroke(1.dp, Color(0xFF1D5543)),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF081E17))
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
      ) {
        Text(
          text = when (currentLanguage) {
            AppLanguage.BANGLA -> "ভাষা নির্বাচন করুন"
            AppLanguage.ENGLISH -> "Select Language"
            AppLanguage.URDU -> "زبان منتخب کریں"
            AppLanguage.ARABIC -> "اختر اللغة"
          },
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          color = SoftWhiteText
        )

        Spacer(modifier = Modifier.height(14.dp))

        AppLanguage.values().forEachIndexed { index, lang ->
          val isCurrent = lang == currentLanguage
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .clickable {
                onDismiss()
                onSelectLanguage(lang)
              }
              .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            FlagEmblem(language = lang, size = 36.dp)

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = lang.nativeName,
                fontSize = 16.sp,
                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                color = if (isCurrent) GoldAccent else SoftWhiteText
              )
              Text(
                text = lang.secondaryName,
                fontSize = 12.sp,
                color = SageText
              )
            }

            if (isCurrent) {
              Text(
                text = "✓",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = GoldAccent
              )
            }
          }

          if (index < AppLanguage.values().lastIndex) {
            HorizontalDivider(
              color = Color(0xFF0F3227),
              thickness = 0.8.dp,
              modifier = Modifier.padding(horizontal = 8.dp)
            )
          }
        }
      }
    }
  }
}
