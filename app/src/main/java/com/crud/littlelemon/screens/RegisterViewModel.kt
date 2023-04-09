package com.crud.littlelemon.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crud.littlelemon.MyApp
import com.crud.littlelemon.networkKtorSer.PostResponse
import com.crud.littlelemon.roomData.MenuDatabase
import com.crud.littlelemon.roomData.MenuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    val readAllData: LiveData<List<PostResponse.DataMenu>>
    private val repository: MenuRepository

    init {
        val userDao = MenuDatabase.getDatabase(MyApp.self).userDao()
        repository = MenuRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addMenu(user: PostResponse.DataMenu) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMenu(user)
        }
    }

    fun updateMenu(user: PostResponse.DataMenu) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMenu(user)
        }
    }

    fun deleteMenu(user: PostResponse.DataMenu) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMenu(user)
        }
    }

    fun deleteMenu() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMenu()
        }
    }
}