package com.example.parametrable.ui.shell

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.parametrable.Config

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    config: Config,
    nav: NavHostController,
    tabs: List<String>,
    onOpenDrawer: () -> Unit,
    onTabClick: (String) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = { AppTopBar(config = config, onOpenDrawer = onOpenDrawer) },
        bottomBar = {
            AppBottomBar(
                nav = nav,
                tabs = tabs,
                onTabClick = onTabClick
            )
        },
        content = content
    )
}
