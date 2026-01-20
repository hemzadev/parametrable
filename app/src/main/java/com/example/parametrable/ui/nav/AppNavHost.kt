package com.example.parametrable.ui.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.parametrable.Config
import com.example.parametrable.screens.HomeScreen

@Composable
fun AppNavHost(
    config: Config,
    nav: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = nav,
        startDestination = startDestination,
        modifier = modifier
    ) {
        if (config.features.home) composable("home") { HomeScreen() }
        if (config.features.merchant) composable("merchant") { CenterScreen("Merchant Screen") }
        if (config.features.support) composable("support") { CenterScreen("Support Screen") }
    }
}

@Composable
fun CenterScreen(text: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text, style = MaterialTheme.typography.headlineMedium)
    }
}
