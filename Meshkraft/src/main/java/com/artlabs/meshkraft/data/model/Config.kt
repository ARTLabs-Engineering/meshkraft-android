package com.artlabs.meshkraft.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Mert Gölcü
 * @date 10.09.2021
 */
@Serializable
data class Config(
    @SerialName("hdrImage")
    val hdrImage: String?,
    @SerialName("showDimensions")
    val showDimensions: Boolean?
)

