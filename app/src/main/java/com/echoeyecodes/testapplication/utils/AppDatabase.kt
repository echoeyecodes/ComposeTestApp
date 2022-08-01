package com.echoeyecodes.testapplication.utils

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.echoeyecodes.testapplication.application.TestApplication
import com.echoeyecodes.testapplication.domain.service.UserDao
import com.echoeyecodes.testapplication.data.models.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var instance: AppDatabase? = null
        fun getInstance() = instance ?: synchronized(this) {
            val newInstance =
                Room.databaseBuilder(TestApplication.context, AppDatabase::class.java, "app_db")
                    .fallbackToDestructiveMigration().build()
            instance = newInstance
            return newInstance
        }
    }
}