package com.artlabs.meshkraft.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Mert Gölcü
 * @date 10.09.2021
 */
@Serializable
data class Large(
    @SerialName("ext")
    val ext: String?,
    @SerialName("hash")
    val hash: String?,
    @SerialName("height")
    val height: Int?,
    @SerialName("mime")
    val mime: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("path")
    val path: Any?,
    @SerialName("size")
    val size: Double?,
    @SerialName("url")
    val url: String?,
    @SerialName("width")
    val width: Int?
)