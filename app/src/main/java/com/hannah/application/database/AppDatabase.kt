package com.hannah.application.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Database(entities = [FoodEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
}

@Singleton
class DatabaseController @Inject constructor(
    private val context: Context
) {
    val database by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "mvrx.db")
            .build()
    }

}