package com.example.parametrable.features.language

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.parametrable.R

@Composable
fun LanguageDialog(
    supportedTags: List<String>,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    val currentLocale = AppCompatDelegate.getApplicationLocales()
    val currentTag = remember(currentLocale) { currentLocale[0]?.toLanguageTag() ?: "" }

    var selected by remember(currentTag, supportedTags) {
        mutableStateOf(
            if (currentTag.isNotBlank()) currentTag
            else supportedTags.firstOrNull().orEmpty()
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.select_language)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                supportedTags.forEach { tag ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selected = tag }
                            .padding(vertical = 6.dp)
                    ) {
                        RadioButton(
                            selected = selected == tag,
                            onClick = { selected = tag }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(text = languageDisplayName(tag))
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onSelect(selected) }) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}
