package com.example.parametrable.ui.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.parametrable.Config
import com.example.parametrable.utils.currentRoute

@Composable
fun AppDrawer(
    config: Config,
    nav: NavHostController,
    onNavigate: (String) -> Unit,
) {
    ModalDrawerSheet {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (config.features.home) {
                DrawerItem(nav, "home", "Home", Icons.Filled.Home, onNavigate)
            }
            if (config.features.merchant) {
                DrawerItem(nav, "merchant", "Merchant", Icons.Filled.ShoppingCart, onNavigate)
            }
            if (config.features.support) {
                DrawerItem(nav, "support", "Support", Icons.Filled.Call, onNavigate)
            }
        }
    }
}

@Composable
private fun DrawerItem(
    nav: NavHostController,
    route: String,
    label: String,
    icon: ImageVector,
    onNavigate: (String) -> Unit
) {
    NavigationDrawerItem(
        label = { Text(label) },
        selected = currentRoute(nav) == route,
        icon = { Icon(icon, contentDescription = label) },
        onClick = { onNavigate(route) }
    )
}
