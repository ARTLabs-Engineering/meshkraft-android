package com.artlabs.meshkraft.data.network.service

import com.artlabs.meshkraft.EVENTS_API_KEY
import com.artlabs.meshkraft.EVENTS_API_URL
import com.artlabs.meshkraft.data.model.StatPayload
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object Events {
    private val JSON = "application/json; charset=utf-8".toMediaType()

    fun sendAnalyticsEvent(payload: StatPayload) {
        val client = OkHttpClient()

        val jsonPayload = Gson().toJson(payload)
        val body = jsonPayload.toRequestBody(JSON)

        val request = Request.Builder()
            .url(EVENTS_API_URL)
            .addHeader("Content-Type", "application/json")
            .addHeader("X-Custom-PSK", EVENTS_API_KEY)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("MeshkraftAR :: Couldn't send event: ${e.localizedMessage}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        println("MeshkraftAR :: Couldn't send event: ${response.message}")
                    }
                }
            }
        })
    }
}