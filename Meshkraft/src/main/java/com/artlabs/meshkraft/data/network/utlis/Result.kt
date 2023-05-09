package com.artlabs.meshkraft.data.network.utlis

/**
 * @author Mert Gölcü
 * @date 10.09.2021
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T?, val json: String) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}