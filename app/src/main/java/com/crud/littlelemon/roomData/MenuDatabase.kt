package com.crud.littlelemon.roomData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.crud.littlelemon.networkKtorSer.PostResponse


@Database(
    entities = [PostResponse.DataMenu::class],
    version = 1,
    exportSchema = true
)
abstract class MenuDatabase: RoomDatabase() {
    abstract fun userDao(): MenuDao

    companion object {
        @Volatile
        private var INSTANCE: MenuDatabase? = null

        fun getDatabase(context: Context): MenuDatabase{
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MenuDatabase::class.java,
                    "menu_data"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}