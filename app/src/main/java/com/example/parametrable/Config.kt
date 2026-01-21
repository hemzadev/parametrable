package com.example.parametrable

import kotlinx.serialization.Serializable

@Serializable
data class Config (
    val appName: String = "WhiteLabel BITS",
    val primaryColor: String = "#6750A4",
    val secondaryColor: String = "",
    val logoAsset: String = "",
    val features: Features = Features(),
    val homeScreen: HomeScreenConfig = HomeScreenConfig()
)

@Serializable
data class Features (
    val home: Boolean = false,
    val merchant: Boolean = false,
    val support: Boolean = false
)

@Serializable
data class HomeScreenConfig(
    val welcomeMessage: String = "Welcome",
    val subtitle: String = "Manage your account and services",
    val sections: List<HomeSection> = emptyList(),
    val quickActions: List<QuickAction> = emptyList()
)

@Serializable
data class HomeSection(
    val title: String,
    val description: String,
    val icon: String = "",
    val enabled: Boolean = true,
    val action: ActionType = ActionType.NONE
)

@Serializable
data class QuickAction(
    val label: String,
    val icon: String = "",
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
