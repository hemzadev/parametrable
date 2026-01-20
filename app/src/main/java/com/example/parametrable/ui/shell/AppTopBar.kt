package com.example.parametrable.ui.shell

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.parametrable.Config

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    config: Config,
    onOpenDrawer: () -> Unit
) {
    TopAppBar(
        title = { AppTitle(config) },
        actions = { ProfileButton() },
        navigationIcon = {
            IconButton(onClick = onOpenDrawer) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
private fun AppTitle(config: Config) {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
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
                Text(text = config.appName, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
private fun ProfileButton() {
    IconButton(onClick = {}) {
        AsyncImage(
            model = "file:///android_asset/defaultProfile.png",
            contentDescription = "Profile",
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.outline, CircleShape)
        )
    }
}
