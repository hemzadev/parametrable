package com.example.parametrable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.compose.AppTheme
import com.example.compose.ConfigTheme
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val raw = assets.open("config.json").bufferedReader().use { it.readText() }

        val config = runCatching {
            Json { ignoreUnknownKeys = true }
                .decodeFromString<Config>(raw)
        }.getOrElse { Config() }

        setContent {
            ConfigTheme(config = config) {
                WhiteLabelApp(config)
            }
        }
    }
}
