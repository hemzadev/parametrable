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
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.parametrable.util.currentRoute
import com.example.parametrable.R


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
                icon = { RouteIcon(route) },
                label = { Text(routeLabel(route)) }
            )
        }
    }
}

@Composable
private fun RouteIcon(route: String) {
    when (route) {
        "home" -> {
            Icon(
                painter = painterResource(R.drawable.home_24px),
                contentDescription = "Home"
            )
        }
        "tour" -> {
            Icon(
                painter = painterResource(R.drawable.mode_of_travel_24px),
                contentDescription = "Tour"
            )
        }
        "merchant" -> {
            Icon(
                painter = painterResource(R.drawable.store_24px),
                contentDescription = "Merchant"
            )
        }
        "support" -> {
            Icon(
                painter = painterResource(R.drawable.support_agent_24px),
                contentDescription = "Support"
            )
        }
        else -> {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = null
            )
        }
    }
}


private fun routeLabel(route: String) =
    route.replaceFirstChar { it.uppercase() }
