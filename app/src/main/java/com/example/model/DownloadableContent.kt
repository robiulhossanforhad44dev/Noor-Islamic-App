package com.example.model

enum class ContentIconType {
  QURAN,
  HADITH,
  FATWA,
  KITAB,
  BOOKS,
  NOORANI,
  KOLKATA,
  TAJWEED,
  TAFSEER,
  SUNNAH,
  BIOGRAPHY,
  LYRICS,
  KHUTBAH,
  SALAWAT
}

data class DownloadContentItem(
  val id: String,
  val title: String,
  val sizeText: String,
  val sizeMb: Double,
  val iconType: ContentIconType,
  val defaultSelected: Boolean = false
)

object DownloadContentRepository {
  val allItems: List<DownloadContentItem> = listOf(
    DownloadContentItem(
      id = "quran_db",
      title = "কুরআন মাজিদ ডাটাবেস",
      sizeText = "~৯.৩৩ মেগাবাইট",
      sizeMb = 9.33,
      iconType = ContentIconType.QURAN,
      defaultSelected = true
    ),
    DownloadContentItem(
      id = "hadith_db",
      title = "হাদিস শরীফ ডাটাবেস",
      sizeText = "~৮৮.৪ মেগাবাইট",
      sizeMb = 88.4,
      iconType = ContentIconType.HADITH
    ),
    DownloadContentItem(
      id = "fatwa_db",
      title = "ফতোয়া-এ রেজভিয়া ডাটাবেস",
      sizeText = "~৭৩.২ মেগাবাইট",
      sizeMb = 73.2,
      iconType = ContentIconType.FATWA
    ),
    DownloadContentItem(
      id = "bahar_db",
      title = "বাহারে শরীয়ত ডাটাবেস (৩ খণ্ড)",
      sizeText = "~৩৯.৯ মেগাবাইট",
      sizeMb = 39.9,
      iconType = ContentIconType.KITAB
    ),
    DownloadContentItem(
      id = "books_db",
      title = "ইসলামী বই সমগ্র ডাটাবেস",
      sizeText = "~৩০.০ মেগাবাইট",
      sizeMb = 30.0,
      iconType = ContentIconType.BOOKS
    ),
    DownloadContentItem(
      id = "noorani_quran",
      title = "নূরানী হাফেজী কুরআন",
      sizeText = "~৩৫ মেগাবাইট",
      sizeMb = 35.0,
      iconType = ContentIconType.NOORANI
    ),
    DownloadContentItem(
      id = "kolkata_quran",
      title = "কলিকাতা ছাপা কুরআন শরীফ",
      sizeText = "~১৫ মেগাবাইট",
      sizeMb = 15.0,
      iconType = ContentIconType.KOLKATA
    ),
    DownloadContentItem(
      id = "tajweed_quran",
      title = "তাজভীদ কালার হাফেজী কুরআন মাজিদ",
      sizeText = "~৪৬ মেগাবাইট",
      sizeMb = 46.0,
      iconType = ContentIconType.TAJWEED
    ),
    DownloadContentItem(
      id = "tafseer_irfan",
      title = "তাফসীর খাযাইনুল ইরফান",
      sizeText = "~১৮.০ মেগাবাইট",
      sizeMb = 18.0,
      iconType = ContentIconType.TAFSEER
    ),
    DownloadContentItem(
      id = "sunnah_db",
      title = "সুন্নাহ ডাটাবেস",
      sizeText = "~৮.০০ মেগাবাইট",
      sizeMb = 8.0,
      iconType = ContentIconType.SUNNAH
    ),
    DownloadContentItem(
      id = "biography_db",
      title = "মনীষীদের জীবনী ডাটাবেস",
      sizeText = "~৭.৫০ মেগাবাইট",
      sizeMb = 7.5,
      iconType = ContentIconType.BIOGRAPHY
    ),
    DownloadContentItem(
      id = "lyrics_db",
      title = "ইসলামী লিরিক্স ডাটাবেস",
      sizeText = "~১.০ মেগাবাইট",
      sizeMb = 1.0,
      iconType = ContentIconType.LYRICS
    ),
    DownloadContentItem(
      id = "khutbah_db",
      title = "জুমু'আর খুতবাহ ডাটাবেস",
      sizeText = "~৩.০ মেগাবাইট",
      sizeMb = 3.0,
      iconType = ContentIconType.KHUTBAH
    ),
    DownloadContentItem(
      id = "salawat_db",
      title = "মাজমুয়ায়ে সালাওয়াত (১৬ খণ্ড)",
      sizeText = "~২৫.৪ মেগাবাইট",
      sizeMb = 25.4,
      iconType = ContentIconType.SALAWAT
    )
  )
}

fun Int.toBengaliDigits(): String {
  val bengaliDigits = charArrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
  return this.toString().map { if (it in '0'..'9') bengaliDigits[it - '0'] else it }.joinToString("")
}

fun Double.toBengaliFormatted(): String {
  val str = String.format(java.util.Locale.US, "%.1f", this)
  val bengaliDigits = charArrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
  return str.map { if (it in '0'..'9') bengaliDigits[it - '0'] else it }.joinToString("")
}
