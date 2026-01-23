package com.example.parametrable.features.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.parametrable.util.ActionType
import com.example.parametrable.util.ButtonStyle
import com.example.parametrable.R

@Composable
fun QuickActionButton(
    actionType: ActionType,
    style: ButtonStyle,
    onClick: () -> Unit
) {
    val label = stringResource(actionType.quickActionLabelResId())

    when (style) {
        ButtonStyle.PRIMARY -> {
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Icon(
                    imageVector = getIconForAction(actionType),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(label)
            }
        }

        ButtonStyle.SECONDARY -> {
            FilledTonalButton(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Icon(
                    imageVector = getIconForAction(actionType),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(label)
            }
        }

        ButtonStyle.OUTLINED -> {
            OutlinedButton(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Icon(
                    imageVector = getIconForAction(actionType),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(label)
            }
        }
    }
}

/* --- mapping --- */

private fun ActionType.quickActionLabelResId(): Int = when (this) {
    ActionType.MAKE_PAYMENT -> R.string.action_make_payment
    ActionType.OPEN_SCANNER -> R.string.action_open_scanner
    else -> R.string.action_unknown
}

private fun getIconForAction(actionType: ActionType): ImageVector {
    // Keep your existing mapping here or import if you already extracted it elsewhere
    return when (actionType) {
        ActionType.NAVIGATE_MERCHANT -> androidx.compose.material.icons.Icons.Default.Store
        ActionType.NAVIGATE_SUPPORT -> androidx.compose.material.icons.Icons.Default.Support
        ActionType.OPEN_SCANNER -> androidx.compose.material.icons.Icons.Default.QrCodeScanner
        ActionType.MAKE_PAYMENT -> androidx.compose.material.icons.Icons.Default.Payment
        ActionType.VIEW_TRANSACTIONS -> androidx.compose.material.icons.Icons.Default.Receipt
        ActionType.CONTACT_SUPPORT -> androidx.compose.material.icons.Icons.Default.ContactSupport
        ActionType.OPEN_SETTINGS -> androidx.compose.material.icons.Icons.Default.Settings
        ActionType.EXTERNAL_LINK -> androidx.compose.material.icons.Icons.Default.OpenInNew
        ActionType.NONE -> androidx.compose.material.icons.Icons.Default.Home
        ActionType.CREATE_MERCHANT -> androidx.compose.material.icons.Icons.Default.Home

    }
}
