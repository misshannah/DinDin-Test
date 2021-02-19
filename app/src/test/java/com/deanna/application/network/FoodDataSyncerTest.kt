package com.hannah.application.network

import com.hannah.application.database.FoodDao
import com.hannah.application.database.FoodEntity
import com.hannah.application.model.UserResponse
import com.hannah.application.model.UsersResponse
import com.hannah.application.util.RobolectricTest
import com.hannah.mvrx.util.TestFoodDao
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.mockito.Answers
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FoodDataSyncerTest : RobolectricTest() {
    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private lateinit var foodDataService: FoodDataService
    private lateinit var foodDao: FoodDao
    private lateinit var foodDataSyncer: FoodDataSyncer


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        foodDao = spy(TestFoodDao().create())
        foodDataSyncer = FoodDataSyncer(foodDataService, foodDao)

        whenever(foodDataService.getUsersRx()).thenReturn(Single.just(usersResponse))
        whenever(foodDataService.getUserDetail("0")).thenReturn(Single.just(usersResponse))
    }

    @Test
    fun refreshUsers() {
        foodDataSyncer.refreshUsers().ignoreElement().blockingAwait()

        verify(foodDao, times(1)).deleteAll()
        verify(foodDao, times(1)).insert(any<List<FoodEntity>>())
        verify(foodDataService, times(1)).getUsersRx()
    }

    @Test
    fun refreshUsers_with_data() {
        foodDao.insert(userEntity)

        val syncerRes = foodDataSyncer.refreshUsers().blockingGet()
        val daoRes = foodDao.getUsersRx().blockingGet()
        Assertions.assertThat(syncerRes.size).isEqualTo(1)
        Assertions.assertThat(daoRes.size).isEqualTo(1)
        Assertions.assertThat(syncerRes[0]).isEqualTo(daoRes[0])
    }


    private val userResponse = UserResponse(
        1,
        2,
        3,
        "userName",
        true,
        4,
        5,
        "link",
        "location",
        "profileImage",
        6,
        7,
        8,
        9,
        10,
        11,
        12,
        "userType",
        "websiteUrl"
    )

    private val usersResponse = UsersResponse(
        false,
        listOf(userResponse),
        1,
        2
    )

    private val userEntity = FoodEntity(
        55, "userName", 1, "imageUrl", "websiteUrl"
    )

}