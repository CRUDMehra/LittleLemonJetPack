package com.crud.littlelemon.networkKtorSer

sealed class NetworkResponse<out T : Any> {
    /**
     * response with a 2xx status code
     */
    data class Success<out PostResponse : Any>(val data: PostResponse) : NetworkResponse<PostResponse>()

    /**
     * response with a non-2xx status code.
     */
    data class Error(val code: Int) : NetworkResponse<Nothing>()
}