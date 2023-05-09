package com.artlabs.meshkraft.data.network.utlis

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




fun <T> callRequest(call: Call<T>, listener: (result: Result<T?>) -> Unit) {

    call.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val rawJson = (response.errorBody() ?: response.body() as? ResponseBody)?.string() ?: ""
            listener.invoke(
                if (response.isSuccessful) {
                    Result.Success(response.body(), rawJson)
                } else {
                    Result.Error(Exception(response.message()))
                }
            )
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            listener.invoke(Result.Error(Exception(t)))
        }
    })

}
