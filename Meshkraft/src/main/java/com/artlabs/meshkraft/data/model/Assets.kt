package com.artlabs.meshkraft.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Mert Gölcü
 * @date 12.11.2021
 */

@Serializable
data class Assets(
    @SerialName("glb")
    val glb: Glb?
)

@Serializable
data class Glb(
    val url: String,
    val size: Double
)