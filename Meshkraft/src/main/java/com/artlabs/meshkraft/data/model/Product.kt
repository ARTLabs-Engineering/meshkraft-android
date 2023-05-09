package com.artlabs.meshkraft.data.model

data class Product(
    val _id: String?,
    val assets: Assets?,
    val name: String?,
    val SKU: String?,
)

data class Assets(
    val usdz: Asset?,
    val glb: Asset?,
    val vto: Asset?,
)

data class Asset(
    val url: String?,
    val size: Double?,
)