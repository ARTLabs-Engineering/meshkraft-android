package com.artlabs.meshkraft.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Mert Gölcü
 * @date 10.09.2021
 */
@Serializable
data class Model(
    @SerialName("config")
    val config: Any?,
    @SerialName("createdAt")
    val createdAt: String?,
    @SerialName("dracoAvailable")
    val dracoAvailable: Boolean?,
    @SerialName("file")
    val `file`: File?,
    @SerialName("_id")
    val _id: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("product")
    val product: String?,
    @SerialName("size")
    val size: String?,
    @SerialName("status")
    val status: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("updatedAt")
    val updatedAt: String?,
    @SerialName("__v")
    val v: Int?
)
