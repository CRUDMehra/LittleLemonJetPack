package com.crud.littlelemon.roomData

import androidx.lifecycle.LiveData
import androidx.room.*
import com.crud.littlelemon.networkKtorSer.PostResponse

@Dao
interface MenuDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMenu(user: PostResponse.DataMenu)

    @Update
    suspend fun updateMenu(user: PostResponse.DataMenu)

    @Delete
    suspend fun deleteMenu(user: PostResponse.DataMenu)

    @Query("DELETE FROM menu")
    suspend fun deleteMenu()

    @Query("SELECT * from menu ORDER BY id ASC")
    fun readAllData(): LiveData<List<PostResponse.DataMenu>>
}