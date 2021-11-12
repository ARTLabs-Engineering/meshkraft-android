package com.artlabs.meshkraft.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Mert Gölcü
 * @date 10.09.2021
 */
@Serializable
data class Formats(
    @SerialName("large")
    val large: Large?,
    @SerialName("medium")
    val medium: Medium?,
    @SerialName("small")
    val small: Small?,
    @SerialName("thumbnail")
    val thumbnail: Thumbnail?
)