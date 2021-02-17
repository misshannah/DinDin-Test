package com.hannah.application.repository

import com.hannah.application.database.UserDao
import com.hannah.application.database.UserEntity
import com.hannah.application.model.User
import com.hannah.application.network.StackOverflowSyncer
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val stackOverflowSyncer: StackOverflowSyncer,
    private val userDao: UserDao
) {

    fun getUsersRx(): Single<List<User>> {
        return stackOverflowSyncer.refreshUsers().flatMap {
            userDao.getUsersRx().map {
                it.map(UserEntity::toUserModel)
            }
        }
    }

    fun getUserRx(id: Int): Single<User> {
        return userDao.getUserDetailRx(id).map(UserEntity::toUserModel)
    }


    fun getUsersRxLocalSearch(query: String): Single<List<User>> {
        return userDao.getUsersRxSearch(query).map {
            it.map(UserEntity::toUserModel)
        }
    }

    fun getUsersRxSearch(query: String): Single<List<User>> {
        return stackOverflowSyncer.refreshUsersSearch(query).flatMap {
            userDao.getUsersRxSearch(query).map {
                it.map(UserEntity::toUserModel)
            }
        }
    }
}