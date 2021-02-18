package com.hannah.application.network

import com.hannah.application.database.UserDao
import com.hannah.application.database.UserEntity
import com.hannah.application.model.UsersResponse
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject



class FoodDataSyncer @Inject constructor(
    private val foodDataService: FoodDataService,
    private val userDao: UserDao
) {
    /* ensures we delete before making the call, and
    *  that all work is done on the io thread */
    fun refreshUsers(): Single<List<UserEntity>> {
        return Completable.fromRunnable { userDao.deleteAll() }.andThen(
            foodDataService.getUsersRx()
                .map(UsersResponse::toUserEntities)
                .doOnSuccess {
                    userDao.insert(it)
                }
                .doOnError { TODO() }

        ).subscribeOn(Schedulers.io())
    }



}