package com.crud.littlelemon.networkKtorSer

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


class ApiWorker {

    private val client = HttpClient(CIO) {
        //Header
        install(DefaultRequest) {
            header("Accept", "application/json")
            header("Content-type", "application/json")
            contentType(ContentType.Application.Json)
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true

            })
            addDefaultResponseValidation()
        }
        // Timeout
        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }

        //Now you see response logs inside terminal
/*        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }*/

        //Print other logs
        install(ResponseObserver) {
            onResponse { response ->
                Log.d("ApiService", "HTTP status: ${response.status.value}")
            }
        }

    }


    fun getClient(): HttpClient {
        return client
    }

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com"
    }


}