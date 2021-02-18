package com.hannah.application.di

import com.hannah.application.network.FoodDataService

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Singleton

@Module
object RetrofitModule {

    fun logging() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideStackOverflowService(): FoodDataService = Retrofit.Builder()
        .baseUrl("https://api.stackexchange.com/2.2/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .client(logging())
        .build()
        .create(FoodDataService::class.java)
}
