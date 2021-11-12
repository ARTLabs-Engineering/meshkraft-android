package com.mertgolcu.meshkraft.AR

import com.artlabs.meshkraft.data.model.Assets
import com.artlabs.meshkraft.data.model.Config
import com.artlabs.meshkraft.data.model.Image
import com.artlabs.meshkraft.data.model.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Mert Gölcü
 * @date 10.09.2021
 */
@Serializable
data class Product(
    @SerialName("category")
    val category: String?,
    @SerialName("config")
    val config: Config?,
    @SerialName("createdAt")
    val createdAt: String?,
    @SerialName("_id")
    val _id: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("images")
    val images: List<Image>?,
    @SerialName("models")
    val models: List<Model>?,
    @SerialName("name")
    val name: String?,
    @SerialName("preview")
    val preview: String?,
    @SerialName("published_at")
    val publishedAt: String?,
    @SerialName("SKU")
    val sKU: String?,
    @SerialName("updatedAt")
    val updatedAt: String?,
    @SerialName("__v")
    val v: Int?,
    @SerialName("assets")
    val assets:Assets?
)














