package com.artlabs.meshkraft.data.network.service


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Mert Gölcü
 * @date 10.09.2021
 */
interface Service {

    @GET("product/{psku}/")
    fun getProduct(
        @Path("psku") sku: String
    ): Call<ResponseBody>

    @GET("product/availability/{psku}")
    fun getProductAvailability(
        @Path("psku") sku: String
    ): Call<ResponseBody>
}