package com.example.parametrable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import coil.compose.AsyncImage
import com.example.parametrable.ui.theme.replyShapes
import com.example.parametrable.ui.theme.replyTypography
import androidx.core.graphics.toColorInt

fun parseColor(hex: String, fallback: Color = Color(0xFF6750A4)): Color {
    return runCatching { Color(hex.toColorInt()) }.getOrElse { fallback }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhiteLabelApp(config: Config) {

    val primary = parseColor(config.primaryColor)

    val secondary = parseColor(config.secondaryColor)

    val scheme = lightColorScheme(primary = primary, secondary = secondary)

    MaterialTheme(colorScheme = scheme, typography = replyTypography, shapes = replyShapes) {
        val nav = rememberNavController()

        val tabs = buildList {

            if (config.features.home) add("home")
            if (config.features.merchant) add("merchant")
            if (config.features.support) add("support")
        }.ifEmpty { listOf("about") }

        Scaffold(

            topBar = {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = MaterialTheme.shapes.extraLarge
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 6.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    if (config.logoAsset.isNotBlank()) {
                                        AsyncImage(
                                            model = "file:///android_asset/${config.logoAsset}",
                                            contentDescription = "Logo",
                                            modifier = Modifier.height(36.dp)
                                        )
                                    }

                                    Text(
                                        text = config.appName,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
//                        containerColor = MaterialTheme.colorScheme.secondary
                        containerColor = Color.White
                    ),

                    actions = {
                        IconButton(onClick = { /* TODO */ }) {
                            AsyncImage(
                                model = "file:///android_asset/defaultProfile.png",
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .border(1.5.dp, Color.Black, CircleShape)
                            )
                        }
                    },

                    navigationIcon = {
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                )
            },

            bottomBar = {
                NavigationBar {
                    tabs.forEach{
                            route -> NavigationBarItem(
                                selected = currentRoute(nav) == route,
                                onClick = { nav.navigate(route) { launchSingleTop = true } },
                                icon = {
                                    Icon(
                                        imageVector = when (route) {
                                            "home" -> Icons.Filled.Home
                                            "merchant" -> Icons.Filled.ShoppingCart
                                            "support" -> Icons.Filled.Call
                                            else -> Icons.Filled.Home
                                        },
                                        contentDescription = route
                                    )
                                },
                                label = { Text(route.replaceFirstChar { it.uppercase() })}
                            )
                    }
                }
            }
        ) {
            padding -> NavHost(
                navController = nav,
                startDestination = tabs.first(),
                modifier = Modifier.padding(padding)
            ) {
                if (config.features.home) composable("home") { HomeScreen() }
                if (config.features.merchant) composable("merchant") { Center("Merchant Screen") }
                if (config.features.support) composable("support") { Center("Support Screen") }
        }
        }
    }
}

@Composable
private fun Center(text: String){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = text, style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
private fun currentRoute(nav: NavHostController): String? {
    val entry = nav.currentBackStackEntryAsState().value
    return entry?.destination?.route
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Home", style = MaterialTheme.typography.headlineMedium)
        Text("This is the home page content.", style = MaterialTheme.typography.bodyMedium)

        Button(onClick = { /* action */ }, shape = MaterialTheme.shapes.extraLarge) {
            Text("Primary action")
        }

        Card(shape = MaterialTheme.shapes.large) {
            Column(Modifier.padding(16.dp)) {
                Text("Card title", style = MaterialTheme.typography.titleMedium)
                Text("Card body text...", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
