package com.example.ui.screens

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.AppLanguage
import com.example.model.CountryInfo
import com.example.model.LocationRepository
import com.example.ui.components.OnboardingHeader
import com.example.ui.theme.ButtonGreen
import com.example.ui.theme.CardBackgroundUnselected
import com.example.ui.theme.CardBorderUnselected
import com.example.ui.theme.MintText
import com.example.ui.theme.NightGreenBackground
import com.example.ui.theme.SageText
import com.example.ui.theme.SoftWhiteText

@Composable
fun LocationSelectionScreen(
  currentStep: Int = 4,
  totalSteps: Int = 6,
  language: AppLanguage = AppLanguage.BANGLA,
  selectedCountry: CountryInfo,
  onCountrySelected: (CountryInfo) -> Unit,
  selectedCity: String,
  onCitySelected: (String) -> Unit,
  onBack: () -> Unit,
  onNext: () -> Unit,
  onLanguageBadgeClick: () -> Unit = {}
) {
  val navBarPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

  var showCountryDialog by remember { mutableStateOf(false) }
  var showCityDialog by remember { mutableStateOf(false) }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(NightGreenBackground)
      .testTag("location_selection_screen")
  ) {
    Column(modifier = Modifier.fillMaxSize()) {
      // Top Curved Islamic Header matching App 4th Screen.jpeg
      OnboardingHeader(
        currentStep = currentStep,
        totalSteps = totalSteps,
        language = language,
        title = if (language == AppLanguage.BANGLA) "আপনার লোকেশন" else "Your Location",
        subtitle = if (language == AppLanguage.BANGLA)
          "সঠিক নামাজের সময় ও কিবলার জন্য শহর নির্বাচন করুন"
        else
          "Select your city for accurate prayer times and Qibla",
        onLanguageBadgeClick = onLanguageBadgeClick
      )

      Column(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        // Main Location Selector Card (Country & City rows with dividers)
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          border = BorderStroke(1.dp, CardBorderUnselected),
          colors = CardDefaults.cardColors(containerColor = CardBackgroundUnselected)
        ) {
          Column(modifier = Modifier.fillMaxWidth()) {
            // Row 1: Country Picker
            LocationPickerRow(
              icon = Icons.Default.Public,
              label = if (language == AppLanguage.BANGLA) "দেশ" else "Country",
              value = "${selectedCountry.nameBn} ${selectedCountry.flagEmoji}",
              testTag = "country_picker_row",
              onClick = { showCountryDialog = true }
            )

            HorizontalDivider(
              color = Color(0xFF0F3227),
              thickness = 0.8.dp,
              modifier = Modifier.padding(horizontal = 14.dp)
            )

            // Row 2: City / District Picker
            LocationPickerRow(
              icon = Icons.Default.Apartment,
              label = if (language == AppLanguage.BANGLA) "শহর / জেলা" else "City / District",
              value = selectedCity,
              testTag = "city_picker_row",
              onClick = { showCityDialog = true }
            )
          }
        }

        // Status / Confirmation Notice Card with Green Checkmark
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(14.dp),
          border = BorderStroke(1.dp, CardBorderUnselected),
          colors = CardDefaults.cardColors(containerColor = CardBackgroundUnselected)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = "Confirmed",
              tint = Color(0xFF1DA57A),
              modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
              text = if (language == AppLanguage.BANGLA)
                "লোকেশন সেট: $selectedCity, ${selectedCountry.nameBn} ${selectedCountry.flagEmoji} (নামাজের সময় ও কিবলা নির্ধারিত)"
              else
                "Location set: $selectedCity, ${selectedCountry.nameEn} ${selectedCountry.flagEmoji} (Prayer times & Qibla configured)",
              fontSize = 13.sp,
              color = Color(0xFFD6EFE5),
              lineHeight = 18.sp
            )
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
            .testTag("location_back_button"),
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
            .testTag("location_next_button"),
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

    // Country Spinner Popup Dialog (App 4th Screen Countrt Spinner.jpeg)
    if (showCountryDialog) {
      CountrySelectionDialog(
        currentCountry = selectedCountry,
        onDismiss = { showCountryDialog = false },
        onSelect = { country ->
          onCountrySelected(country)
          // If previous city doesn't belong to newly selected country, pick first city of that country
          if (!country.cities.contains(selectedCity)) {
            onCitySelected(country.cities.firstOrNull() ?: "")
          }
          showCountryDialog = false
        }
      )
    }

    // City Spinner Popup Dialog (App 4th Screen City Spinner popup.jpeg)
    if (showCityDialog) {
      CitySelectionDialog(
        cities = selectedCountry.cities,
        onDismiss = { showCityDialog = false },
        onSelect = { city ->
          onCitySelected(city)
          showCityDialog = false
        }
      )
    }
  }
}

@Composable
fun LocationPickerRow(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  label: String,
  value: String,
  testTag: String,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
      )
      .padding(horizontal = 16.dp, vertical = 16.dp)
      .testTag(testTag),
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Leading circular dark green disc
    Box(
      modifier = Modifier
        .size(42.dp)
        .clip(CircleShape)
        .background(Color(0xFF0C2B21)),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = Color(0xFF1DA57A),
        modifier = Modifier.size(22.dp)
      )
    }

    Spacer(modifier = Modifier.width(14.dp))

    // Middle Column
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = label,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = SageText
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = value,
        fontSize = 15.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF56C39F)
      )
    }

    // Trailing Chevron arrow
    Icon(
      imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
      contentDescription = "Expand",
      tint = Color(0xFF2F7660),
      modifier = Modifier.size(22.dp)
    )
  }
}

@Composable
fun CountrySelectionDialog(
  currentCountry: CountryInfo,
  onDismiss: () -> Unit,
  onSelect: (CountryInfo) -> Unit
) {
  var searchQuery by remember { mutableStateOf("") }
  val allCountries = remember { LocationRepository.allCountries }

  val filteredCountries = remember(searchQuery) {
    if (searchQuery.isBlank()) {
      allCountries
    } else {
      allCountries.filter {
        it.nameBn.contains(searchQuery, ignoreCase = true) ||
          it.nameEn.contains(searchQuery, ignoreCase = true)
      }
    }
  }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth(0.96f)
        .heightIn(max = 520.dp)
        .testTag("country_selection_dialog"),
      shape = RoundedCornerShape(20.dp),
      border = BorderStroke(1.dp, Color(0xFF194939)),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF081E17))
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
      ) {
        // Dialog Title
        Text(
          text = "দেশ নির্বাচন করুন",
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          color = SoftWhiteText
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Search Input Box
        SearchInputField(
          query = searchQuery,
          onQueryChange = { searchQuery = it },
          placeholder = "দেশ খুঁজুন..."
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Country List
        LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f, fill = false)
        ) {
          items(filteredCountries) { country ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable { onSelect(country) }
                .padding(vertical = 12.dp, horizontal = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "${country.nameBn} ${country.flagEmoji}",
                fontSize = 15.sp,
                color = SoftWhiteText,
                fontWeight = if (country.id == currentCountry.id) FontWeight.Bold else FontWeight.Normal
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun CitySelectionDialog(
  cities: List<String>,
  onDismiss: () -> Unit,
  onSelect: (String) -> Unit
) {
  var searchQuery by remember { mutableStateOf("") }

  val filteredCities = remember(cities, searchQuery) {
    if (searchQuery.isBlank()) {
      cities
    } else {
      cities.filter { it.contains(searchQuery, ignoreCase = true) }
    }
  }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth(0.96f)
        .heightIn(max = 520.dp)
        .testTag("city_selection_dialog"),
      shape = RoundedCornerShape(20.dp),
      border = BorderStroke(1.dp, Color(0xFF194939)),
      colors = CardDefaults.cardColors(containerColor = Color(0xFF081E17))
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
      ) {
        // Dialog Title
        Text(
          text = "শহর / জেলা নির্বাচন করুন",
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          color = SoftWhiteText
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Search Input Box
        SearchInputField(
          query = searchQuery,
          onQueryChange = { searchQuery = it },
          placeholder = "জেলা বা শহর খুঁজুন..."
        )

        Spacer(modifier = Modifier.height(12.dp))

        // City / District List
        LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f, fill = false)
        ) {
          items(filteredCities) { city ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable { onSelect(city) }
                .padding(vertical = 12.dp, horizontal = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = city,
                fontSize = 15.sp,
                color = SoftWhiteText
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun SearchInputField(
  query: String,
  onQueryChange: (String) -> Unit,
  placeholder: String
) {
  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .height(48.dp),
    shape = RoundedCornerShape(12.dp),
    color = Color(0xFF061B14),
    border = BorderStroke(1.dp, Color(0xFF1B5543))
  ) {
    Row(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Search",
        tint = Color(0xFF7CB8A3),
        modifier = Modifier.size(18.dp)
      )

      Spacer(modifier = Modifier.width(10.dp))

      Box(modifier = Modifier.weight(1f)) {
        if (query.isEmpty()) {
          Text(
            text = placeholder,
            fontSize = 14.sp,
            color = Color(0xFF7CB8A3)
          )
        }
        BasicTextField(
          value = query,
          onValueChange = onQueryChange,
          singleLine = true,
          textStyle = TextStyle(
            color = SoftWhiteText,
            fontSize = 14.sp
          ),
          cursorBrush = SolidColor(Color(0xFF56C39F)),
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
  }
}
