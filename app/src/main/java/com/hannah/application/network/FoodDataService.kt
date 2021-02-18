package com.hannah.application.network

import com.hannah.application.model.User
import com.hannah.application.model.UserResponse
import com.hannah.application.model.UsersResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodDataService {

    @GET("users?order=desc&sort=reputation&site=stackoverflow")
    fun getUsersRx(): Single<UsersResponse>


    @GET("users/{id}?site=stackoverflow")
    fun getUserDetail(@Path("id") id: String): Single<UsersResponse>

}