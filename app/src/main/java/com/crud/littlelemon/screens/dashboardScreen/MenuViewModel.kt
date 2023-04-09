package com.crud.littlelemon.screens.dashboardScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crud.littlelemon.networkKtorSer.ApiRepositories
import com.crud.littlelemon.networkKtorSer.Event
import com.crud.littlelemon.networkKtorSer.NetworkResponse
import com.crud.littlelemon.networkKtorSer.PostResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuViewModel : ViewModel() {
    //region UseCase
    sealed class MenuLiveData {
        data class ShowMenuItems(val items: PostResponse) : MenuLiveData()
        data class Error(val code: Int) : MenuLiveData()
        data class Saved(val isSaved: Boolean) : MenuLiveData()
    }

    val getMenuDataResponse = MutableLiveData<Event<MenuLiveData>>()

    fun executeMenuData() {
        viewModelScope.launch {
            getMenuData()
        }
    }

    //region Private Methods
    private suspend fun getMenuData() {
        withContext(Dispatchers.IO) {
            ApiRepositories().getMenu()
        }.apply {
            when (this) {
                is NetworkResponse.Success -> getMenuDataResponse
                    .postValue(
                        Event(MenuLiveData.ShowMenuItems(this.data))
                    )
                is NetworkResponse.Error -> getMenuDataResponse
                    .postValue(
                        Event(MenuLiveData.Error(this.code))
                    )

            }
        }

    }
}