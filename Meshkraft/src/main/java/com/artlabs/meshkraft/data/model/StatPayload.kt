package com.artlabs.meshkraft.data.model

import com.artlabs.meshkraft.Meshkraft
import java.util.*


data class StatPayload(
    val source: String = "ar-sdk",
    val token: String? = Meshkraft.getApiKey(),
    val event: MeshkraftEvent,
    val device_id: String = UUID.randomUUID().toString()
) {
    data class MeshkraftEvent(
        val key: String,
        val count: Int = 1,
        val segmentation: Segmentation
    ) {
        data class Segmentation(
            val token: String? = Meshkraft.getApiKey(),
            val sku: String?,
            val platform: String = "Android",
            val dow: Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK),
            val hour: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        )
    }
}