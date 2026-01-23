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
import com.example.parametrable.features.common.CreateMerchant
import com.example.parametrable.screens.HomeScreen
import com.example.parametrable.screens.TourScreen
import com.example.parametrable.util.ActionType
import com.example.parametrable.util.Config

object Routes {
    const val HOME = "home"

    const val TOUR = "tour"
    const val MERCHANT = "merchant"
    const val SUPPORT = "support"
    const val CREATE_MERCHANT = "create_merchant"

    // optional (add later if you implement screens)
    const val SCANNER = "scanner"
    const val PAYMENT = "payment"
    const val TRANSACTIONS = "transactions"
    const val SETTINGS = "settings"
}

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
        if (config.features.home) {
            composable(Routes.HOME) {
                HomeScreen(
                    config = config,
                    onActionClick = { action ->
                        handleAction(action, nav, config)
                    }
                )
            }
        }

        if (config.features.tour) {
            composable(Routes.TOUR) {
                TourScreen(
                    config = config,
                    onActionClick = { action ->
                        handleAction(action, nav, config)
                    }
                )
            }
        }

        if (config.features.merchant) {
            composable(Routes.MERCHANT) { CenterScreen("Merchant Screen") }
        }

        if (config.features.support) {
            composable(Routes.SUPPORT) { CenterScreen("Support Screen") }
        }

        // ✅ Add this destination so navigation works
        composable(Routes.CREATE_MERCHANT) {
            CreateMerchant(
                config = config,
                onBack = { nav.popBackStack() }
            )
        }

        // (Optional stubs so your other actions don’t crash)
        composable(Routes.SCANNER) { CenterScreen("Scanner Screen") }
        composable(Routes.PAYMENT) { CenterScreen("Payment Screen") }
        composable(Routes.TRANSACTIONS) { CenterScreen("Transactions Screen") }
        composable(Routes.SETTINGS) { CenterScreen("Settings Screen") }
    }
}

@Composable
fun CenterScreen(text: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text, style = MaterialTheme.typography.headlineMedium)
    }
}

private fun handleAction(
    action: ActionType,
    navController: NavHostController,
    config: Config
) {
    when (action) {
        ActionType.NAVIGATE_MERCHANT ->
            navController.safeNavigate(Routes.MERCHANT, enabled = config.features.merchant)

        ActionType.NAVIGATE_SUPPORT ->
            navController.safeNavigate(Routes.SUPPORT, enabled = config.features.support)

        ActionType.OPEN_SCANNER ->
            navController.safeNavigate(Routes.SCANNER)

        ActionType.MAKE_PAYMENT ->
            navController.safeNavigate(Routes.PAYMENT)

        ActionType.VIEW_TRANSACTIONS ->
            navController.safeNavigate(Routes.TRANSACTIONS)

        ActionType.OPEN_SETTINGS ->
            navController.safeNavigate(Routes.SETTINGS)

        ActionType.CONTACT_SUPPORT ->
            navController.safeNavigate(Routes.SUPPORT, enabled = config.features.support)

        ActionType.CREATE_MERCHANT ->
            navController.safeNavigate(Routes.CREATE_MERCHANT)

        ActionType.EXTERNAL_LINK -> {
            // TODO: startActivity(Intent(ACTION_VIEW, Uri.parse(url)))
        }

        ActionType.NONE -> Unit
    }
}

// ✅ prevents crash if route not in graph or feature disabled
private fun NavHostController.safeNavigate(route: String, enabled: Boolean = true) {
    if (!enabled) return
    if (graph.findNode(route) == null) return
    navigate(route)
}
