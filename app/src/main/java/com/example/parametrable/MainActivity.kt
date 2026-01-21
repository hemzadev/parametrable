package com.example.parametrable

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.compose.AppTheme
import com.example.compose.ConfigTheme
import com.example.parametrable.util.Config
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val raw = assets.open("config.json").bufferedReader().use { it.readText() }

        val config = runCatching {
            Json { ignoreUnknownKeys = true }
                .decodeFromString<Config>(raw)
        }.getOrElse { Config() }

        val currentLocale = AppCompatDelegate.getApplicationLocales()[0]?.toLanguageTag() ?: "system"
        android.util.Log.d("LanguageChange", "MainActivity onCreate - Current locale: $currentLocale")

//        setContent {
//            ConfigTheme(config = config) {
//                WhiteLabelApp(config)
//            }
//        }

        setContent {
            AppTheme() {
                WhiteLabelApp(config)
            }
        }
    }
}
