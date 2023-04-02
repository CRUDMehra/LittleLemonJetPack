package com.crud.littlelemon

import android.app.Application

class MyApp : Application() {

    companion object{

        lateinit var self: MyApp
            private set
    }
    override fun onCreate() {
        super.onCreate()
        self = this
    }
}