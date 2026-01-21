package com.example.parametrable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.*
import androidx.core.graphics.toColorInt
import com.example.parametrable.ui.drawer.AppDrawer
import com.example.parametrable.ui.nav.AppNavHost
import com.example.parametrable.ui.shell.AppScaffold
import com.example.parametrable.util.Config
import kotlinx.coroutines.launch

fun parseColor(hex: String, fallback: Color = Color(0xFF6750A4)): Color {
    return runCatching { Color(hex.toColorInt()) }.getOrElse { fallback }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhiteLabelApp(config: Config) {
    val nav = rememberNavController()
    val tabs = rememberTabs(config)

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                config = config,
                nav = nav,
                onNavigate = { route ->
                    scope.launch { drawerState.close() }
                    nav.navigate(route) { launchSingleTop = true }
                }
            )
        }
    ) {
        AppScaffold(
            config = config,
            nav = nav,
            tabs = tabs,
            onOpenDrawer = { scope.launch { drawerState.open() } },
            onTabClick = { route -> nav.navigate(route) { launchSingleTop = true } }
        ) { innerPadding ->
            AppNavHost(
                config = config,
                nav = nav,
                startDestination = tabs.first(),
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun rememberTabs(config: Config): List<String> =
    buildList {
        if (config.features.home) add("home")
        if (config.features.merchant) add("merchant")
        if (config.features.support) add("support")
    }.ifEmpty { listOf("about") }
