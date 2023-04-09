package com.crud.littlelemon.networkKtorSer

import android.util.Log
import io.ktor.client.plugins.*
import io.ktor.util.network.*

class NetworkResponseCode {

    companion object{
        private const val TAG = "NetworkResponseCode"
    }
    /**
     * responses status code.
     */
     fun checkError(e: Throwable): Int {
        Log.e(TAG, "checkError: ${e.message.toString()}" )
        // Handle Error
        return when (e) {

            //For 3xx responses
            is RedirectResponseException -> {
                (e.response.status.value)
            }
            //For 4xx responses
            is ClientRequestException -> {
                (e.response.status.value)
            }

            //For 5xx responses
            is ServerResponseException -> {
                (e.response.status.value)
            }

            // Network Error
            is UnresolvedAddressException -> {
                // Internet Error
                -1
            }
            else -> {
                // Unhandled error
                -2
            }
        }
    }
}