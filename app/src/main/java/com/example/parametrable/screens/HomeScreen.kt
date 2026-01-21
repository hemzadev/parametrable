package com.example.parametrable.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.parametrable.*

@Composable
fun HomeScreen(
    config: Config,
    onActionClick: (ActionType) -> Unit
) {
    val homeConfig = config.homeScreen

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Section
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = homeConfig.welcomeMessage,
                    style = MaterialTheme.typography.headlineLarge,
                    //color = MaterialTheme.colorScheme.primary
                    color = parseColor(config.primaryColor)
                )
                Text(
                    text = homeConfig.subtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Quick Actions Section
        if (homeConfig.quickActions.isNotEmpty()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Quick Actions",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    homeConfig.quickActions.filter { it.enabled }.forEach { action ->
                        QuickActionButton(
                            action = action,
                            onClick = { onActionClick(action.action) }
                        )
                    }
                }
            }
        }

        // Home Sections (Cards)
        items(homeConfig.sections.filter { it.enabled }) { section ->
            HomeSectionCard(
                section = section,
                onClick = { onActionClick(section.action) }
            )
        }
    }
}

@Composable
private fun QuickActionButton(
    action: QuickAction,
    onClick: () -> Unit
) {
    when (action.style) {
        ButtonStyle.PRIMARY -> {
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Icon(
                    imageVector = getIconForAction(action.action),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(action.label)
            }
        }
        ButtonStyle.SECONDARY -> {
            FilledTonalButton(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Icon(
                    imageVector = getIconForAction(action.action),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(action.label)
            }
        }
        ButtonStyle.OUTLINED -> {
            OutlinedButton(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Icon(
                    imageVector = getIconForAction(action.action),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(action.label)
            }
        }
    }
}

@Composable
private fun HomeSectionCard(
    section: HomeSection,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = getIconForAction(section.action),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = section.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = section.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun getIconForAction(actionType: ActionType): ImageVector {
    return when (actionType) {
        ActionType.NAVIGATE_MERCHANT -> Icons.Default.Store
        ActionType.NAVIGATE_SUPPORT -> Icons.Default.Support
        ActionType.OPEN_SCANNER -> Icons.Default.QrCodeScanner
        ActionType.MAKE_PAYMENT -> Icons.Default.Payment
        ActionType.VIEW_TRANSACTIONS -> Icons.Default.Receipt
        ActionType.CONTACT_SUPPORT -> Icons.Default.ContactSupport
        ActionType.OPEN_SETTINGS -> Icons.Default.Settings
        ActionType.EXTERNAL_LINK -> Icons.Default.OpenInNew
        ActionType.NONE -> Icons.Default.Home
    }
}