package com.example.parametrable.screens

import PerformanceCard
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.parametrable.*
import com.example.parametrable.R
import com.example.parametrable.features.home.HomeSectionCard
import com.example.parametrable.features.home.QuickActionButton
import com.example.parametrable.features.home.ScheduleCard
import com.example.parametrable.features.language.LanguageCard
import com.example.parametrable.features.language.LanguageDialog
import com.example.parametrable.features.language.setAppLanguage
import com.example.parametrable.util.ActionType
import com.example.parametrable.util.Config
import com.example.parametrable.util.findActivity

@Composable
fun HomeScreen(
    config: Config,
    onActionClick: (ActionType) -> Unit
) {
    val homeConfig = config.homeScreen

    val languageCfg = config.language
    val languageEnabled = languageCfg?.enabled == true
    val enabledLanguageTags = remember(languageCfg) {
        languageCfg?.supported?.filter { it.enabled }?.map { it.tag }.orEmpty()
    }

    var showLanguageDialog by remember { mutableStateOf(false) }
    val ctx = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = stringResource(R.string.home_welcome, config.appName),
                    style = MaterialTheme.typography.headlineLarge,
                    color = parseColor(config.primaryColor)
                )
                Text(
                    text = stringResource(R.string.home_subtitle),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (homeConfig.performanceCard.enabled) {
            item {
                PerformanceCard(
                    cardConfig = homeConfig.performanceCard,
                    appPrimary = parseColor(config.primaryColor)
                )
            }
        }

        if (languageEnabled && enabledLanguageTags.isNotEmpty()) {
            item {
                LanguageCard(onClick = { showLanguageDialog = true })
            }
        }

        val enabledQuickActions = homeConfig.quickActions.filter { it.enabled }
        if (enabledQuickActions.isNotEmpty()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = stringResource(R.string.quick_actions),
                        style = MaterialTheme.typography.titleMedium
                    )
                    enabledQuickActions.forEach { qa ->
                        QuickActionButton(
                            actionType = qa.action,
                            style = qa.style,
                            onClick = { onActionClick(qa.action) }
                        )
                    }
                }
            }
        }

        items(homeConfig.sections.filter { it.enabled }) { section ->
            HomeSectionCard(
                actionType = section.action,
                onClick = { onActionClick(section.action) }
            )
        }
    }

    if (showLanguageDialog) {
        LanguageDialog(
            supportedTags = enabledLanguageTags,
            onDismiss = { showLanguageDialog = false },
            onSelect = { tag ->
                showLanguageDialog = false
                setAppLanguage(tag)
                // If you're still using ComponentActivity, force it:
                ctx.findActivity()?.recreate()
            }
        )
    }
}
