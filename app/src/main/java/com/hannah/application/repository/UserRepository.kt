package com.hannah.application.repository

import com.hannah.application.database.UserDao
import com.hannah.application.database.UserEntity
import com.hannah.application.model.User
import com.hannah.application.network.FoodDataSyncer
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val foodDataSyncer: FoodDataSyncer,
    private val userDao: UserDao
) {

    fun getUsersRx(): Single<List<User>> {
        return foodDataSyncer.refreshUsers().flatMap {
            userDao.getUsersRx().map {
                it.map(UserEntity::toUserModel)
            }
        }
    }

    fun getUserRx(id: Int): Single<User> {
        return userDao.getUserDetailRx(id).map(UserEntity::toUserModel)
    }



}