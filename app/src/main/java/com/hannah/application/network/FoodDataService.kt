package com.hannah.application.network

import com.hannah.application.model.FoodsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodDataService {

    @GET("users?order=desc&sort=reputation&site=stackoverflow")
    fun getFoodsRx(): Single<FoodsResponse>


    @GET("users/{id}?site=stackoverflow")
    fun getFoodDetail(@Path("id") id: String): Single<FoodsResponse>

}
