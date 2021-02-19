package com.hannah.application.network

import com.hannah.application.database.FoodDao
import com.hannah.application.database.FoodEntity
import com.hannah.application.model.FoodsResponse
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject



class FoodDataSyncer @Inject constructor(
    private val foodDataService: FoodDataService,
    private val foodDao: FoodDao
) {
    /* ensures we delete before making the call, and
    *  that all work is done on the io thread */
    fun refreshUsers(): Single<List<FoodEntity>> {
        return Completable.fromRunnable { foodDao.deleteAll() }.andThen(
            foodDataService.getFoodsRx()
                .map(FoodsResponse::toFoodEntities)
                .doOnSuccess {
                    foodDao.insert(it)
                }
                .doOnError { TODO() }

        ).subscribeOn(Schedulers.io())
    }



}