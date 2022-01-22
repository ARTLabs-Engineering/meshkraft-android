package com.artlabs.meshkraft.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Mert Gölcü
 * @date 21.01.2022
 */

@Serializable
data class Product(
    val _id: String?,
    @SerialName("id")
    val models: List<Model>?,
    @SerialName("name")
    val name: String?,
    @SerialName("SKU")
    val sKU: String?,
)

@Serializable
data class Model(
    @SerialName("file")
    val `file`: File?,
    @SerialName("_id")
    val size: String?,
    @SerialName("status")
    val status: String?,
    @SerialName("type")
    val type: String?,
)

@Serializable
data class File(
    @SerialName("ext")
    val ext: String?,
    @SerialName("hash")
    val hash: String?,
    @SerialName("height")
    val height: Any?,
    @SerialName("size")
    val size: Double?,
    @SerialName("url")
    val url: String?,
    @SerialName("width")
    val width: Any?
)