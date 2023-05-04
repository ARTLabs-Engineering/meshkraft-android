package com.artlabs.meshkraft.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Mert Gölcü
 * @date 21.01.2022
 */

@Serializable
data class Product(
    @SerialName("_id")
    val _id: String?,
    val assets: Assets?,
    val name: String?,
    val SKU: String?,
)

@Serializable
data class Assets(
    val usdz: Asset?,
    val glb: Asset?,
    val vto: Asset?,
)

@Serializable
data class Asset(
    val url: String?,
    val size: Double?,
)