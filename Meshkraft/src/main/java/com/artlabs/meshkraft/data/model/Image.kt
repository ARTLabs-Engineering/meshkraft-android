package com.artlabs.meshkraft.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Mert Gölcü
 * @date 10.09.2021
 */
@Serializable
data class Image(
    @SerialName("alternativeText")
    val alternativeText: String?,
    @SerialName("caption")
    val caption: String?,
    @SerialName("createdAt")
    val createdAt: String?,
    @SerialName("ext")
    val ext: String?,
    @SerialName("formats")
    val formats: Formats?,
    @SerialName("hash")
    val hash: String?,
    @SerialName("height")
    val height: Int?,
    @SerialName("_id")
    val _id: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("mime")
    val mime: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("provider")
    val provider: String?,
    @SerialName("related")
    val related: List<String>?,
    @SerialName("size")
    val size: Double?,
    @SerialName("updatedAt")
    val updatedAt: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("__v")
    val v: Int?,
    @SerialName("width")
    val width: Int?
)
