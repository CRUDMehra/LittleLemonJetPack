package com.crud.littlelemon.utils

import android.content.Context
import android.content.SharedPreferences
import com.crud.littlelemon.MyApp
import com.crud.littlelemon.models.UserDetailsModel
import com.google.gson.Gson

private const val MY_PREF_NAME = "LittleLemon"
private fun getPrefManger(): SharedPreferences? {
    return MyApp.self.applicationContext.getSharedPreferences(MY_PREF_NAME, Context.MODE_PRIVATE)
}

fun saveStringPref(key: String, value: String) {
    getPrefManger()?.edit()?.putString(key, value)?.apply()
}

fun getStringPref(key: String): String? {
    return getPrefManger()?.getString(key, "")
}

fun saveBooleanPref(key: String, value: Boolean) {
    getPrefManger()?.edit()?.putBoolean(key, value)?.apply()
}

fun getBooleanPref(key: String): Boolean? {
    return getPrefManger()?.getBoolean(key, false)
}


fun saveUserInfoPref(value: UserDetailsModel) {
    val model = Gson().toJson(value)
    getPrefManger()?.edit()?.putString(USER_INFO, model)?.apply()
}

fun getUserInfoPref(): UserDetailsModel? {
    val userDetails = getPrefManger()?.getString(USER_INFO, "")
    return Gson().fromJson(userDetails, UserDetailsModel::class.java)
}


const val IS_LOGGED_USER = "user_logged"
private const val USER_INFO = "user_info"
