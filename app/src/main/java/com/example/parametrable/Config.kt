package com.example.parametrable

import kotlinx.serialization.Serializable

@Serializable
data class Config (

    val appName: String = "WhiteLabel BITS",
    val primaryColor: String = "#6750A4",
    val secondaryColor: String = "",
    val logoAsset: String = "",
    val features: Features = Features()

)

@Serializable
data class Features (

    val home: Boolean = false,
    val merchant: Boolean = false,
    val support: Boolean = false

)
