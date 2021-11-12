package com.artlabs.meshkraft.data.network.utlis

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author Mert Gölcü
 * @date 10.09.2021
 */
fun <T> callRequest(call: Call<T>, listener: (result: Result<T?>) -> Unit) {

    call.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            listener.invoke(
                if (response.isSuccessful) {
                    Result.Success(response.body())
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