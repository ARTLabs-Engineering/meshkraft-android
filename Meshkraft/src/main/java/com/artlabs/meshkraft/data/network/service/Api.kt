package com.artlabs.meshkraft.data.network.service


import com.artlabs.meshkraft.BASE_URL
import com.artlabs.meshkraft.TOKEN
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Mert Gölcü
 * @date 10.09.2021
 */
object Api {

    private var token = ""
    fun setToken(token: String) {
        Api.token = token
    }

    private fun interceptor(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter(TOKEN, token)
                .build()

            // Request customization: add request headers
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
        return httpClient.build()
    }

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(interceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service: Service by lazy {
        retrofit().create(Service::class.java)
    }

}