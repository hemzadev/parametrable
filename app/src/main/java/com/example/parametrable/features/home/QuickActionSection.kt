package com.example.parametrable.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // ✅ Single source of truth for Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class QuickActionUi(
    val label: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun QuickActionSection(
    actions: List<QuickActionUi>,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            // "Add" action - Semantic "Outlined" variant
            item {
                ActionChip(
                    label = "Add",
                    icon = Icons.Outlined.Add,
                    onClick = onAddClick,
                    isProminent = false
                )
            }

            // User actions - Semantic "Filled/Tonal" variant
            items(actions) { action ->
                ActionChip(
                    label = action.label,
                    icon = action.icon,
                    onClick = action.onClick,
                    isProminent = true
                )
            }
        }
    }
}

@Composable
private fun ActionChip(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    isProminent: Boolean,
    modifier: Modifier = Modifier
) {
    // M3 Guidance: Use SecondaryContainer for tonal actions
    // and Surface with a border for secondary/utility actions.
    val containerColor = if (isProminent) {
        MaterialTheme.colorScheme.secondaryContainer
    } else {
        Color.Transparent // Better for Outlined style
    }

    val contentColor = if (isProminent) {
        MaterialTheme.colorScheme.onSecondaryContainer
    } else {
        MaterialTheme.colorScheme.primary
    }

    AssistChip(
        onClick = onClick,
        modifier = modifier,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge // M3 default for chips
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(AssistChipDefaults.IconSize)
            )
        },
        shape = MaterialTheme.shapes.medium, // More modern "Squircle" look
        colors = AssistChipDefaults.assistChipColors(
            containerColor = containerColor,
            labelColor = contentColor,
            leadingIconContentColor = contentColor
        ),
        border = AssistChipDefaults.assistChipBorder(
            enabled = true,
            borderColor = MaterialTheme.colorScheme.outlineVariant
        )
    )
}