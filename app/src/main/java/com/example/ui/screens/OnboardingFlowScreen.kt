package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mosque
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.example.ui.components.LanguageChangeDialog
import com.example.ui.components.LanguagePickerModal
import com.example.ui.components.MosqueWatermark
import com.example.ui.theme.ButtonGreen
import com.example.ui.theme.CardBackgroundSelected
import com.example.ui.theme.CardBackgroundUnselected
import com.example.ui.theme.CardBorderSelected
import com.example.ui.theme.CardBorderUnselected
import com.example.ui.theme.EmeraldHeaderEnd
import com.example.ui.theme.EmeraldHeaderStart
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.LanguageBadgeBg
import com.example.ui.theme.LanguageBadgeBorder
import com.example.ui.theme.MintText
import com.example.ui.theme.NightGreenBackground
import com.example.ui.theme.SageText
import com.example.ui.theme.SoftWhiteText

@Composable
fun OnboardingFlowScreen(
  selectedPalette: com.example.model.IslamicColorPalette = com.example.model.IslamicColorPalette.EMERALD_GREEN,
  onPaletteChange: (com.example.model.IslamicColorPalette) -> Unit = {},
  onComplete: () -> Unit
) {
  var currentStep by remember { mutableStateOf(1) }
  var selectedLanguage by remember { mutableStateOf(AppLanguage.BANGLA) }

  // Step 3 state: Display Mode
  var selectedDisplayMode by remember { mutableStateOf(com.example.model.DisplayMode.DEVICE_DEFAULT) }

  // Step 4 state: Location (Country & City)
  var selectedCountry by remember { mutableStateOf(com.example.model.LocationRepository.allCountries.first()) }
  var selectedCity by remember { mutableStateOf("ঢাকা") }

  // Step 5 state: Adhan & Alerts (Fajr and Dhuhr highlighted ON by default matching App 5th Screen On.jpeg)
  var prayerAlertsMap by remember {
    mutableStateOf(
      mapOf(
        "fajr" to true,
        "dhuhr" to true,
        "asr" to false,
        "maghrib" to false,
        "isha" to false
      )
    )
  }

  // Step 6 state: Hijri Calendar adjustment
  var hijriOffset by remember { mutableIntStateOf(0) }

  // Quran preferences
  var selectedScript by remember { mutableStateOf("উসমানী লিপি (Madani/Uthmani)") }
  var selectedTranslation by remember { mutableStateOf("মাওলানা মুহিউদ্দীন খান") }

  // Language Change Modal & Animation Dialog State
  var showLanguagePickerModal by remember { mutableStateOf(false) }
  var pendingLanguageChange by remember { mutableStateOf<AppLanguage?>(null) }

  BackHandler(enabled = currentStep > 1) {
    currentStep -= 1
  }

  AnimatedContent(
    targetState = currentStep,
    transitionSpec = {
      if (targetState > initialState) {
        (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
          slideOutHorizontally { width -> -width } + fadeOut()
        )
      } else {
        (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
          slideOutHorizontally { width -> width } + fadeOut()
        )
      }
    },
    label = "stepAnimation"
  ) { step ->
    when (step) {
      1 -> {
        LanguageSelectionScreen(
          currentStep = 1,
          totalSteps = 6,
          selectedLanguage = selectedLanguage,
          onLanguageSelected = { selectedLanguage = it },
          onNextStep = { currentStep = 2 },
          onOpenLanguageMenu = {
            val nextOrdinal = (selectedLanguage.ordinal + 1) % AppLanguage.values().size
            selectedLanguage = AppLanguage.values()[nextOrdinal]
          }
        )
      }

      2 -> {
        ContentDownloadScreen(
          currentStep = 2,
          totalSteps = 6,
          language = selectedLanguage,
          onBack = { currentStep = 1 },
          onProceedToNextStep = { currentStep = 3 },
          onLanguageBadgeClick = {
            showLanguagePickerModal = true
          }
        )
      }

      3 -> {
        ThemeSelectionScreen(
          currentStep = 3,
          totalSteps = 6,
          language = selectedLanguage,
          selectedPalette = selectedPalette,
          onPaletteSelected = onPaletteChange,
          selectedDisplayMode = selectedDisplayMode,
          onDisplayModeSelected = { selectedDisplayMode = it },
          onBack = { currentStep = 2 },
          onNext = { currentStep = 4 },
          onLanguageBadgeClick = {
            showLanguagePickerModal = true
          }
        )
      }

      4 -> {
        LocationSelectionScreen(
          currentStep = 4,
          totalSteps = 6,
          language = selectedLanguage,
          selectedCountry = selectedCountry,
          onCountrySelected = { selectedCountry = it },
          selectedCity = selectedCity,
          onCitySelected = { selectedCity = it },
          onBack = { currentStep = 3 },
          onNext = { currentStep = 5 },
          onLanguageBadgeClick = {
            showLanguagePickerModal = true
          }
        )
      }

      5 -> {
        AdhanAlertScreen(
          currentStep = 5,
          totalSteps = 6,
          language = selectedLanguage,
          prayerToggles = prayerAlertsMap,
          onPrayerToggleChange = { id, enabled ->
            prayerAlertsMap = prayerAlertsMap.toMutableMap().apply { put(id, enabled) }
          },
          onToggleAll = { enabled ->
            prayerAlertsMap = defaultPrayerAlerts.associate { it.id to enabled }
          },
          onBack = { currentStep = 4 },
          onNext = { currentStep = 6 },
          onLanguageBadgeClick = {
            showLanguagePickerModal = true
          }
        )
      }

      6 -> {
        HijriCalendarScreen(
          currentStep = 6,
          totalSteps = 6,
          language = selectedLanguage,
          hijriOffset = hijriOffset,
          onOffsetChange = { newOffset ->
            hijriOffset = newOffset
          },
          onBack = { currentStep = 5 },
          onFinish = onComplete,
          onLanguageBadgeClick = { showLanguagePickerModal = true }
        )
      }
    }

    if (showLanguagePickerModal) {
      LanguagePickerModal(
        currentLanguage = selectedLanguage,
        onDismiss = { showLanguagePickerModal = false },
        onSelectLanguage = { newLang ->
          showLanguagePickerModal = false
          if (newLang != selectedLanguage) {
            pendingLanguageChange = newLang
          }
        }
      )
    }

    pendingLanguageChange?.let { targetLang ->
      LanguageChangeDialog(
        targetLanguage = targetLang,
        onDismiss = {
          selectedLanguage = targetLang
          pendingLanguageChange = null
        }
      )
    }
  }
}

@Composable
fun StepScaffold(
  currentStep: Int,
  totalSteps: Int,
  language: AppLanguage,
  title: String,
  subtitle: String,
  onBack: () -> Unit,
  onNext: () -> Unit,
  onLanguageBadgeClick: () -> Unit = {},
  nextButtonText: String = language.nextStepButton,
  content: @Composable () -> Unit
) {
  val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
  val navBarPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(NightGreenBackground)
  ) {
    Column(modifier = Modifier.fillMaxSize()) {
      // Top Curved Header
      Surface(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
        color = Color.Transparent
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .background(
              brush = Brush.verticalGradient(
                colors = listOf(EmeraldHeaderStart, EmeraldHeaderEnd)
              )
            )
            .padding(
              top = statusBarPadding + 10.dp,
              bottom = 24.dp,
              start = 16.dp,
              end = 16.dp
            )
        ) {
          MosqueWatermark(
            modifier = Modifier
              .align(Alignment.BottomEnd)
              .width(200.dp)
              .height(110.dp),
            tint = Color(0xFF67CFA9).copy(alpha = 0.20f)
          )

          Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              IconButton(onClick = onBack) {
                Icon(
                  imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                  contentDescription = "Back",
                  tint = SoftWhiteText
                )
              }

              OnboardingStepper(
                currentStep = currentStep,
                totalSteps = totalSteps,
                language = language
              )

              Surface(
                modifier = Modifier
                  .clip(RoundedCornerShape(12.dp))
                  .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onLanguageBadgeClick
                  ),
                shape = RoundedCornerShape(12.dp),
                color = LanguageBadgeBg,
                border = BorderStroke(1.dp, LanguageBadgeBorder)
              ) {
                Text(
                  text = language.badgeText,
                  color = SoftWhiteText,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.SemiBold,
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
              }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
              text = title,
              fontSize = 22.sp,
              fontWeight = FontWeight.Bold,
              color = SoftWhiteText,
              textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
              text = subtitle,
              fontSize = 13.sp,
              color = MintText,
              textAlign = TextAlign.Center
            )
          }
        }
      }

      // Scrollable content
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .verticalScroll(rememberScrollState())
          .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        content()
      }

      // Bottom bar
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(
            start = 20.dp,
            end = 20.dp,
            bottom = navBarPadding + 16.dp,
            top = 6.dp
          ),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        OutlinedButton(
          onClick = onBack,
          modifier = Modifier
            .weight(0.35f)
            .height(52.dp),
          shape = RoundedCornerShape(26.dp),
          border = BorderStroke(1.dp, CardBorderUnselected),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = MintText)
        ) {
          Text(text = language.prevStepButton, fontSize = 14.sp)
        }

        Button(
          onClick = onNext,
          modifier = Modifier
            .weight(0.65f)
            .height(52.dp),
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
              text = nextButtonText,
              fontSize = 15.sp,
              fontWeight = FontWeight.SemiBold,
              color = SoftWhiteText
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = null,
              tint = SoftWhiteText,
              modifier = Modifier.size(16.dp)
            )
          }
        }
      }
    }
  }
}

@Composable
fun SelectionOptionCard(
  title: String,
  subtitle: String,
  isSelected: Boolean,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  onClick: () -> Unit
) {
  val border = if (isSelected) BorderStroke(1.5.dp, CardBorderSelected) else BorderStroke(1.dp, CardBorderUnselected)
  val bg = if (isSelected) CardBackgroundSelected else CardBackgroundUnselected

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
      ),
    shape = RoundedCornerShape(16.dp),
    border = border,
    colors = CardDefaults.cardColors(containerColor = bg)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(if (isSelected) GoldAccent else Color(0xFF143B2E)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = if (isSelected) Color(0xFF09251D) else MintText,
          modifier = Modifier.size(20.dp)
        )
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          fontSize = 16.sp,
          fontWeight = FontWeight.SemiBold,
          color = SoftWhiteText
        )
        Text(
          text = subtitle,
          fontSize = 12.5.sp,
          color = SageText
        )
      }

      if (isSelected) {
        Box(
          modifier = Modifier
            .size(22.dp)
            .clip(CircleShape)
            .background(GoldAccent),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = Color(0xFF09251D),
            modifier = Modifier.size(15.dp)
          )
        }
      } else {
        Surface(
          modifier = Modifier.size(22.dp),
          shape = CircleShape,
          color = Color.Transparent,
          border = BorderStroke(1.5.dp, Color(0xFF1C4D3D))
        ) {}
      }
    }
  }
}

@Composable
fun PrayerNotificationToggle(
  title: String,
  time: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(14.dp),
    border = BorderStroke(1.dp, CardBorderUnselected),
    colors = CardDefaults.cardColors(containerColor = CardBackgroundUnselected)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.NotificationsActive,
          contentDescription = null,
          tint = if (checked) GoldAccent else SageText,
          modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = SoftWhiteText)
          Text(text = time, fontSize = 12.sp, color = SageText)
        }
      }

      Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
          checkedThumbColor = Color(0xFF09251D),
          checkedTrackColor = GoldAccent,
          uncheckedThumbColor = SageText,
          uncheckedTrackColor = Color(0xFF143B2E)
        )
      )
    }
  }
}

@Composable
fun SummaryRow(label: String, value: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(text = label, color = SageText, fontSize = 13.5.sp)
    Text(text = value, color = SoftWhiteText, fontWeight = FontWeight.SemiBold, fontSize = 13.5.sp)
  }
}
