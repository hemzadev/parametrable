package com.example.parametrable.config_wl

import kotlinx.serialization.Serializable

@Serializable
data class HomeScreenConfig(
    val performanceCard: PerformanceCardConfig = PerformanceCardConfig(),
    val scheduleCard: ScheduleCardConfig = ScheduleCardConfig()
)

@Serializable
data class PerformanceCardConfig(
    val enabled: Boolean = true,
    val progressBarEnabled: Boolean = true,
    val indicatorsEnabled: Boolean = true,
    val styling: PerformanceCardStyling = PerformanceCardStyling.STYLE_1
)

@Serializable
enum class PerformanceCardStyling {
    STYLE_1,
    STYLE_2
}

@Serializable
data class ScheduleCardConfig(
    val enabled: Boolean = true,
    val styling: ScheduleCardStyling = ScheduleCardStyling.STYLE_1
)

@Serializable
enum class ScheduleCardStyling {
    STYLE_1,
    STYLE_2
}

