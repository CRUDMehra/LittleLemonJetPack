package com.crud.littlelemon.networkKtorSer

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import java.nio.charset.Charset

class ApiRepositories {
    private val networkResponseCode = NetworkResponseCode()

    private var getMenuURL = "/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
    suspend fun getMenu(): NetworkResponse<PostResponse> {
        return try {
            val response: HttpResponse = ApiWorker()
                .getClient()
                .get(ApiWorker.BASE_URL + getMenuURL)
            Log.e("ApiRepositories", "getMenu:body " + response.body())
            Log.e("ApiRepositories", "getMenu:headers " + response.headers)
            Log.e("ApiRepositories", "getMenu: " + response.bodyAsText(Charset.defaultCharset()))
            Log.e("ApiRepositories", "getMenu: " + response.bodyAsChannel().toString())
            val gson = Gson().fromJson(
                response.bodyAsText(Charset.defaultCharset()),
                PostResponse::class.java
            )
            (NetworkResponse.Success(gson))
        } catch (e: Throwable) {
            (NetworkResponse.Error(networkResponseCode.checkError(e)))
        }
    }
}



@kotlinx.serialization.Serializable
class PostResponse(
    @PrimaryKey(autoGenerate = false)
    @SerialName("menu")
    @Expose
    val menu: ArrayList<DataMenu>? = ArrayList()
) {
    @Entity(tableName = "menu")
    @kotlinx.serialization.Serializable
    class DataMenu(
        @PrimaryKey(autoGenerate = true)
        @SerialName("id")
        @Expose
        val id: Int ,
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
