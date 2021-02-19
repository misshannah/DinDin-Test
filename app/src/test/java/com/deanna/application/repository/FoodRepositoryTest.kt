package com.hannah.application.repository

import com.hannah.application.database.FoodDao
import com.hannah.application.database.FoodEntity
import com.hannah.application.model.Food
import com.hannah.application.network.FoodDataService
import com.hannah.application.network.FoodDataSyncer
import com.hannah.application.util.RobolectricTest
import com.hannah.mvrx.util.TestFoodDao
import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

class FoodRepositoryTest : RobolectricTest() {
    private lateinit var foodDataSyncer: FoodDataSyncer
    private lateinit var foodDataService: FoodDataService
    private lateinit var foodDao: FoodDao
    private lateinit var repository: FoodRepository

    @Before
    fun setup() {
        foodDao = spy(TestFoodDao().create())
        foodDataService = mock()
        foodDataSyncer = FoodDataSyncer(foodDataService,foodDao)
        repository = FoodRepository(foodDataSyncer, foodDao)
        
    }

    @Test
    fun getUsersRx() {
        foodDao.insert(userEntity)

        val user = repository.getFoodRx().blockingGet()

        verify(foodDataSyncer, times(1)).refreshUsers()
        verify(foodDao, times(1)).getFoodsRx()
        Assertions.assertThat(user.size).isEqualTo(1)
        Assertions.assertThat(user[0].userId == 0)
    }

    private val userEntity = FoodEntity(
        0, "userName", 1, "imageUrl", "websiteUrl"
    )

    private val userModel = Food(
        0, "userName", 1, "imageUrl", "websiteUrl"
    )
}