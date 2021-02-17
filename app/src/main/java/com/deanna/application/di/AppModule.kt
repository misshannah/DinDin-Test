package com.hannah.application.di

import com.hannah.application.database.DatabaseController
import com.hannah.application.database.UserDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    @JvmStatic
    @Provides
    @Singleton
    fun providesUserDao(controller: DatabaseController): UserDao = controller.database.userDao()
}