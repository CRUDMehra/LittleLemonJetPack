package com.crud.littlelemon.roomData

import androidx.lifecycle.LiveData
import com.crud.littlelemon.networkKtorSer.PostResponse


class MenuRepository(private val menuDao: MenuDao) {
    val readAllData: LiveData<List <PostResponse.DataMenu>> = menuDao.readAllData()

    suspend fun addMenu(user: PostResponse.DataMenu) {
        menuDao.addMenu(user)
    }

    suspend fun updateMenu(user: PostResponse.DataMenu) {
        menuDao.updateMenu(user)
    }

    suspend fun deleteMenu(user: PostResponse.DataMenu) {
        menuDao.deleteMenu(user)
    }

    suspend fun deleteMenu() {
        menuDao.deleteMenu()
    }
}