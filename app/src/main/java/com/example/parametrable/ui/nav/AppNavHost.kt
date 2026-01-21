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
import com.example.parametrable.ActionType
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
        if (config.features.home) composable("home") {
            HomeScreen(
                config = config,
                onActionClick = { action ->
                    handleAction(action, nav)
            }
        ) }
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

private fun handleAction(action: ActionType, navController: androidx.navigation.NavHostController) {
    when (action) {
        ActionType.NAVIGATE_MERCHANT -> navController.navigate("merchant")
        ActionType.NAVIGATE_SUPPORT -> navController.navigate("support")
        ActionType.OPEN_SCANNER -> navController.navigate("scanner")
        ActionType.MAKE_PAYMENT -> navController.navigate("payment")
        ActionType.VIEW_TRANSACTIONS -> navController.navigate("transactions")
        ActionType.OPEN_SETTINGS -> navController.navigate("settings")
        ActionType.CONTACT_SUPPORT -> navController.navigate("support")
        ActionType.EXTERNAL_LINK -> {
            // Handle external links (Intent to browser)
        }
        ActionType.NONE -> {
            // Do nothing
        }
    }
}
