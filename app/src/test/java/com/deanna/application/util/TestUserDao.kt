package com.hannah.mvrx.util

import androidx.room.Room
import com.hannah.application.database.AppDatabase
import com.hannah.application.database.UserDao
import com.hannah.application.util.RobolectricTest
import org.junit.After
import java.io.IOException

class TestUserDao : RobolectricTest() {
    private lateinit var database: AppDatabase

    fun create(): UserDao {
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        return DelegatingUserDao(database.userDao())
    }

    @After
    @Throws(IOException::class)
    fun shutdown() {
        database.close()
    }

}

/* Delegates methods in this interface to the instance
* passed in so that we can spy the interface for tests */
open class DelegatingUserDao(dao: UserDao) : UserDao by dao