package com.crud.littlelemon.networkKtorSer

import com.google.gson.annotations.Expose
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.SerialName

class ApiRepositories {

    private val apiWorker: ApiWorker = ApiWorker()
    private val networkResponseCode = NetworkResponseCode()

    private var getMenuURL ="/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
    suspend fun getMenu(): NetworkResponse<MutableList<PostResponse>> {
        return try {
            val response: HttpResponse =
                apiWorker.getClient().get(apiWorker.BASE_URL + getMenuURL)
            // Return response
            (NetworkResponse.Success(response.body()))
        } catch (e: Throwable) {
            (NetworkResponse.Error(networkResponseCode.checkError(e)))
        }
    }
}

@kotlinx.serialization.Serializable
class PostResponse(
    @SerialName("menu")
    @Expose
    val menu: ArrayList<DataMenu>? = ArrayList()
) {
    @kotlinx.serialization.Serializable
    class DataMenu(
        @SerialName("id")
        @Expose
        val id: String? = null,
        @SerialName("title")
        @Expose
        val title: String? = null,
        @SerialName("description")
        @Expose
        val description: String? = null,
        @SerialName("price")
        @Expose
        val price: String? = null,
        @SerialName("image")
        @Expose
        val image: String? = null,
        @SerialName("category")
        @Expose
        val category: String? = null,
    )
}
