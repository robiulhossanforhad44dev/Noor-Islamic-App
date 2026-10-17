package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.AppLanguage
import com.example.model.DisplayMode
import com.example.model.DownloadContentRepository
import com.example.model.IslamicColorPalette
import com.example.model.LocationRepository
import com.example.model.toBengaliDigits
import com.example.model.toBengaliFormatted
import com.example.ui.screens.toArabicDigits
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Noor Islam", appName)
  }

  @Test
  fun `verify bangla step numerals`() {
    val lang = AppLanguage.BANGLA
    assertEquals("১", lang.getStepNumeral(1))
    assertEquals("২", lang.getStepNumeral(2))
    assertEquals("৩", lang.getStepNumeral(3))
    assertEquals("৪", lang.getStepNumeral(4))
    assertEquals("৫", lang.getStepNumeral(5))
    assertEquals("৬", lang.getStepNumeral(6))
  }

  @Test
  fun `verify downloadable content items`() {
    val items = DownloadContentRepository.allItems
    assertEquals(14, items.size)
    assertEquals("কুরআন মাজিদ ডাটাবেস", items[0].title)
    assertEquals("মাজমুয়ায়ে সালাওয়াত (১৬ খণ্ড)", items.last().title)
    assertTrue(items.any { it.id == "hadith_db" })
  }

  @Test
  fun `verify islamic theme color palettes and display modes`() {
    val palettes = IslamicColorPalette.values()
    assertEquals(7, palettes.size)
    assertEquals("জমুররুদ সবুজ", palettes[0].displayName)
    assertEquals("শাহানা মেরুন", palettes[1].displayName)
    assertEquals("নূরানী জলপাই", palettes[2].displayName)
    assertEquals("নীলকান্তমণি", palettes[3].displayName)
    assertEquals("শাহানা সাইপ্রেস", palettes[4].displayName)
    assertEquals("রমাদান আম্বার", palettes[5].displayName)
    assertEquals("সাগর ফিরোজা", palettes[6].displayName)

    val modes = DisplayMode.values()
    assertEquals(3, modes.size)
    assertEquals("ডিভাইস ডিফল্ট", modes[0].displayName)
    assertEquals("লাইট মোড", modes[1].displayName)
    assertEquals("নাইট মোড", modes[2].displayName)
  }

  @Test
  fun `verify location repository countries and districts`() {
    val countries = LocationRepository.allCountries
    assertTrue(countries.isNotEmpty())
    assertEquals("বাংলাদেশ", countries[0].nameBn)
    assertEquals("🇧🇩", countries[0].flagEmoji)

    val districts = LocationRepository.bangladeshDistricts
    assertTrue(districts.contains("ঢাকা"))
    assertTrue(districts.contains("কক্সবাজার"))
    assertTrue(districts.contains("কিশোরগঞ্জ"))
    assertTrue(districts.contains("কুড়িগ্রাম"))
    assertTrue(districts.contains("কুমিল্লা"))
    assertTrue(districts.contains("কুষ্টিয়া"))
    assertTrue(districts.contains("খাগড়াছড়ি"))
    assertTrue(districts.contains("খুলনা"))
    assertTrue(districts.contains("গাইবান্ধা"))
  }

  @Test
  fun `verify bengali formatting helpers`() {
    assertEquals("২১", 21.toBengaliDigits())
    assertEquals("১০০", 100.toBengaliDigits())
    assertEquals("০", 0.toBengaliDigits())
    assertEquals("০.০", 0.0.toBengaliFormatted())
    assertEquals("৯.৩", 9.33.toBengaliFormatted())
  }

  @Test
  fun `verify prayer alerts default configuration`() {
    val alerts = com.example.ui.screens.defaultPrayerAlerts
    assertEquals(5, alerts.size)
    assertEquals("fajr", alerts[0].id)
    assertEquals("ফজর", alerts[0].getName(AppLanguage.BANGLA))
    assertEquals("Fajr", alerts[0].getName(AppLanguage.ENGLISH))
    assertEquals("ইশা", alerts.last().getName(AppLanguage.BANGLA))
  }

  @Test
  fun `verify hijri arabic digits and day offset calculations`() {
    val digits = 11.toArabicDigits()
    assertEquals("۱۱", digits)
    val baseDay = 11
    val offsetPlus = (baseDay + 1).coerceIn(1, 30)
    val offsetMinus = (baseDay - 2).coerceIn(1, 30)
    assertEquals(12, offsetPlus)
    assertEquals(9, offsetMinus)
  }
}
