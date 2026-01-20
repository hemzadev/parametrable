package com.example.parametrable.ui.shell

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.parametrable.utils.currentRoute


@Composable
fun AppBottomBar(
    nav: NavHostController,
    tabs: List<String>,
    onTabClick: (String) -> Unit
) {
    NavigationBar {
        tabs.forEach { route ->
            NavigationBarItem(
                selected = currentRoute(nav) == route,
                onClick = { onTabClick(route) },
                icon = { Icon(routeIcon(route), contentDescription = route) },
                label = { Text(routeLabel(route)) }
            )
        }
    }
}

private fun routeIcon(route: String) = when (route) {
    "home" -> Icons.Filled.Home
    "merchant" -> Icons.Filled.ShoppingCart
    "support" -> Icons.Filled.Call
    else -> Icons.Filled.Home
}

private fun routeLabel(route: String) =
    route.replaceFirstChar { it.uppercase() }
