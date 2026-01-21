package com.example.parametrable.features.language

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

fun setAppLanguage(tag: String) {
    val locales = if (tag.isNotBlank()) {
        LocaleListCompat.forLanguageTags(tag)
    } else {
        LocaleListCompat.getEmptyLocaleList()
    }
    AppCompatDelegate.setApplicationLocales(locales)
}

fun languageDisplayName(tag: String): String = when (tag) {
    "en" -> "English"
    "fr" -> "Français"
    "ar" -> "العربية"
    "ar-MA" -> "الدارجة المغربية"
    else -> tag
}
