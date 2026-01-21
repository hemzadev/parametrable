package com.example.parametrable.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.parametrable.util.ActionType
import com.example.parametrable.R

@Composable
fun HomeSectionCard(
    actionType: ActionType,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = getIconForAction(actionType),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(actionType.sectionTitleResId()),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(actionType.sectionDescResId()),
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

/* --- mapping --- */

private fun ActionType.sectionTitleResId(): Int = when (this) {
    ActionType.NAVIGATE_MERCHANT -> R.string.section_merchant_title
    ActionType.VIEW_TRANSACTIONS -> R.string.section_transactions_title
    ActionType.NAVIGATE_SUPPORT -> R.string.section_support_title
    ActionType.OPEN_SETTINGS -> R.string.section_settings_title
    else -> R.string.section_unknown_title
}

private fun ActionType.sectionDescResId(): Int = when (this) {
    ActionType.NAVIGATE_MERCHANT -> R.string.section_merchant_desc
    ActionType.VIEW_TRANSACTIONS -> R.string.section_transactions_desc
    ActionType.NAVIGATE_SUPPORT -> R.string.section_support_desc
    ActionType.OPEN_SETTINGS -> R.string.section_settings_desc
    else -> R.string.section_unknown_desc
}

@Composable
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
        ActionType.NONE -> Icon(
            painter = painterResource(id = R.drawable.home_24px),
            contentDescription = "Home",
            modifier = Modifier.size(24.dp)
        )
    } as ImageVector
}
