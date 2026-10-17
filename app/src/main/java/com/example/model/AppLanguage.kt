package com.example.model

enum class AppLanguage(
  val code: String,
  val nativeName: String,
  val secondaryName: String,
  val badgeText: String
) {
  BANGLA("bn", "বাংলা", "Bengali", "বাং"),
  ENGLISH("en", "English", "ইংরেজি", "EN"),
  URDU("ur", "اردو", "Urdu", "اردو"),
  ARABIC("ar", "العربية", "Arabic", "عرب");

  fun getStepNumeral(step: Int): String {
    return when (this) {
      BANGLA -> when (step) {
        1 -> "১"; 2 -> "২"; 3 -> "৩"; 4 -> "৪"; 5 -> "৫"; 6 -> "৬"
        else -> step.toString()
      }
      URDU, ARABIC -> when (step) {
        1 -> "۱"; 2 -> "۲"; 3 -> "۳"; 4 -> "۴"; 5 -> "۵"; 6 -> "۶"
        else -> step.toString()
      }
      ENGLISH -> step.toString()
    }
  }

  val headerTitle: String
    get() = when (this) {
      BANGLA -> "অ্যাপের ভাষা"
      ENGLISH -> "App Language"
      URDU -> "ایپ کی زبان"
      ARABIC -> "لغة التطبيق"
    }

  val headerSubtitle: String
    get() = when (this) {
      BANGLA -> "আপনার পছন্দের ভাষা নির্বাচন করুন"
      ENGLISH -> "Select your preferred language"
      URDU -> "اپنی پسندیدہ زبان منتخب کریں"
      ARABIC -> "اختر لغتك المفضلة"
    }

  val nextStepButton: String
    get() = when (this) {
      BANGLA -> "পরবর্তী ধাপ"
      ENGLISH -> "Next Step"
      URDU -> "اگلا مرحلہ"
      ARABIC -> "الخطوة التالية"
    }

  val prevStepButton: String
    get() = when (this) {
      BANGLA -> "পূর্ববর্তী ধাপ"
      ENGLISH -> "Previous"
      URDU -> "پچھلا مرحلہ"
      ARABIC -> "السابق"
    }

  val startAppButton: String
    get() = when (this) {
      BANGLA -> "অ্যাপ শুরু করুন"
      ENGLISH -> "Get Started"
      URDU -> "شروع کریں"
      ARABIC -> "ابدأ الآن"
    }
}
