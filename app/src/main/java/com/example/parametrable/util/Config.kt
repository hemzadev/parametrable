package com.example.parametrable.util

import com.example.parametrable.config_wl.PerformanceCardConfig
import com.example.parametrable.config_wl.ScheduleCardConfig
import kotlinx.serialization.Serializable

@Serializable
data class Config (
    val appName: String = "WhiteLabel BITS",
    val primaryColor: String = "#6750A4",
    val secondaryColor: String = "",
    val logoAsset: String = "",
    val features: Features = Features(),
    val language: LanguageConfig? = null,
    val homeScreen: HomeScreenConfig = HomeScreenConfig()
)

@Serializable
data class Features (
    val home: Boolean = false,
    val merchant: Boolean = false,
    val support: Boolean = false
)

@Serializable
data class LanguageConfig(
    val enabled: Boolean = false,
    val supported: List<SupportedLanguage> = emptyList()
)

@Serializable
data class SupportedLanguage(
    val tag: String,
    val enabled: Boolean
)

@Serializable
data class HomeScreenConfig(
    val sections: List<HomeSection> = emptyList(),
    val quickActions: List<QuickAction> = emptyList(),

    val performanceCard: PerformanceCardConfig = PerformanceCardConfig(),
    val scheduleCard: ScheduleCardConfig = ScheduleCardConfig()
)

@Serializable
data class HomeSection(
    val enabled: Boolean = true,
    val action: ActionType = ActionType.NONE
)

@Serializable
data class QuickAction(
    val enabled: Boolean = true,
    val action: ActionType = ActionType.NONE,
    val style: ButtonStyle = ButtonStyle.PRIMARY
)

@Serializable
enum class ActionType {
    NONE,
    NAVIGATE_MERCHANT,
    NAVIGATE_SUPPORT,
    OPEN_SCANNER,
    MAKE_PAYMENT,
    VIEW_TRANSACTIONS,
    CONTACT_SUPPORT,
    OPEN_SETTINGS,
    EXTERNAL_LINK
}

@Serializable
enum class ButtonStyle {
    PRIMARY,
    SECONDARY,
    OUTLINED
}

fun Config.enabledLanguageTags(): List<String> =
    language?.takeIf { it.enabled }?.supported
        ?.filter { it.enabled }
        ?.map { it.tag }
        .orEmpty()

