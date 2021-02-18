package com.hannah.application.repository

import com.hannah.application.database.UserDao
import com.hannah.application.database.UserEntity
import com.hannah.application.model.User
import com.hannah.application.network.FoodDataService
import com.hannah.application.network.FoodDataSyncer
import com.hannah.application.util.RobolectricTest
import com.hannah.mvrx.util.TestUserDao
import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

class UserRepositoryTest : RobolectricTest() {
    private lateinit var foodDataSyncer: FoodDataSyncer
    private lateinit var foodDataService: FoodDataService
    private lateinit var userDao: UserDao
    private lateinit var repository: UserRepository

    @Before
    fun setup() {
        userDao = spy(TestUserDao().create())
        foodDataService = mock()
        foodDataSyncer = FoodDataSyncer(foodDataService,userDao)
        repository = UserRepository(foodDataSyncer, userDao)
        
    }

    @Test
    fun getUsersRx() {
        userDao.insert(userEntity)

        val user = repository.getUsersRx().blockingGet()

        verify(foodDataSyncer, times(1)).refreshUsers()
        verify(userDao, times(1)).getUsersRx()
        Assertions.assertThat(user.size).isEqualTo(1)
        Assertions.assertThat(user[0].userId == 0)
    }

    private val userEntity = UserEntity(
        0, "userName", 1, "imageUrl", "websiteUrl"
    )

    private val userModel = User(
        0, "userName", 1, "imageUrl", "websiteUrl"
    )
}