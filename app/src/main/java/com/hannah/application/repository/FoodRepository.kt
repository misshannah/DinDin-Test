package com.hannah.application.repository

import com.hannah.application.database.FoodDao
import com.hannah.application.database.FoodEntity
import com.hannah.application.model.Food
import com.hannah.application.network.FoodDataSyncer
import io.reactivex.Single
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val foodDataSyncer: FoodDataSyncer,
    private val foodDao: FoodDao
) {

    fun getFoodRx(): Single<List<Food>> {
        return foodDataSyncer.refreshUsers().flatMap {
            foodDao.getFoodsRx().map {
                it.map(FoodEntity::toFoodModel)
            }
        }
    }

    fun getFoodRx(id: Int): Single<Food> {
        return foodDao.getFoodDetailRx(id).map(FoodEntity::toFoodModel)
    }



}