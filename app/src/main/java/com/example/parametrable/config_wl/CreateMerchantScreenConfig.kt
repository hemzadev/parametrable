package com.example.parametrable.config_wl

import kotlinx.serialization.Serializable

@Serializable
data class CreateMerchantScreenConfig(
    val enabled: Boolean = true,
    val steps: List<MerchantStepConfig> = emptyList()
)

@Serializable
data class MerchantStepConfig(
    val id: MerchantStepId,
    val enabled: Boolean = true,
    val titleKey: String,
    val sections: List<MerchantSectionConfig> = emptyList()
)

@Serializable
enum class MerchantStepId {
    CONTACT,
    MERCHANT,
    BANK,
    SEGMENTATION
}

@Serializable
data class MerchantSectionConfig(
    val id: MerchantSectionId,
    val enabled: Boolean = true,
    val titleKey: String? = null,
    val fields: Map<String, MerchantFieldConfig> = emptyMap()
)

@Serializable
enum class MerchantSectionId {
    CONTACT_FIELDS,
    BUSINESS_PROFILE,
    LOCATION,
    WORKING_HOURS,
    LEGAL_IDENTIFIERS,
    PHOTOS,
    LEGAL_DOCUMENTS,
    BANK_FIELDS,
    SEGMENTATION_FIELDS
}

@Serializable
data class MerchantFieldConfig(
    val enabled: Boolean = true,
    val required: Boolean = false,

    // Optional extras (used only when relevant)
    val defaultValue: String? = null,
    val options: List<String>? = null
)


fun CreateMerchantScreenConfig.enabledSteps(): List<MerchantStepConfig> =
    steps.filter { it.enabled }

fun MerchantStepConfig.section(id: MerchantSectionId): MerchantSectionConfig? =
    sections.firstOrNull { it.id == id && it.enabled }

fun MerchantSectionConfig.fieldEnabled(key: String): Boolean =
    fields[key]?.enabled ?: true

fun MerchantSectionConfig.fieldRequired(key: String): Boolean =
    fields[key]?.required ?: false

