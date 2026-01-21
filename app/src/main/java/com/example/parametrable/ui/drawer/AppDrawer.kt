package com.example.parametrable.ui.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.parametrable.R
import com.example.parametrable.util.Config
import com.example.parametrable.util.currentRoute

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
                DrawerItem(
                    nav = nav,
                    route = "home",
                    label = "Home",
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.home_24px),
                            contentDescription = "Home",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    onNavigate = onNavigate
                )
            }

            if (config.features.merchant) {
                DrawerItem(
                    nav = nav,
                    route = "merchant",
                    label = "Merchant",
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.store_24px),
                            contentDescription = "Merchant",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    onNavigate = onNavigate
                )
            }

            if (config.features.support) {
                DrawerItem(
                    nav = nav,
                    route = "support",
                    label = "Support",
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.support_agent_24px),
                            contentDescription = "Support",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    onNavigate = onNavigate
                )
            }
        }
    }
}

@Composable
private fun DrawerItem(
    nav: NavHostController,
    route: String,
    label: String,
    icon: @Composable () -> Unit,
    onNavigate: (String) -> Unit
) {
    NavigationDrawerItem(
        label = { Text(label) },
        selected = currentRoute(nav) == route,
        icon = { icon() },
        onClick = { onNavigate(route) }
    )
}
