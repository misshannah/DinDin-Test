package com.hannah.application.database

import com.hannah.application.util.RobolectricTest
import com.hannah.mvrx.util.TestFoodDao
import org.junit.Before
import org.junit.Test

class FoodDaoTests : RobolectricTest() {

    private lateinit var foodDao: FoodDao
    private val imgUrl: String = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"
    private val webUrl: String = "https://www.google.com"

    @Before
    fun setup() {
        foodDao = TestFoodDao().create()
    }

    @Test
    fun inserts_user_entity() {

        val user = FoodEntity(0, "userName", 100, imgUrl, webUrl)
        foodDao.insert(user)

        val res = foodDao.getFoodsRx().blockingGet()

        assert(res.size == 1)
        val userRes = res[0]
        assert(userRes.userId == 0)
        assert(userRes.userName == "userName")
        assert(userRes.reputation == 100)
        assert(userRes.imageUrl == imgUrl)
        assert(userRes.websiteUrl == webUrl)

    }

    @Test
    fun inserts_user_entities() {

        val users = listOf(
            FoodEntity(0, "userName", 100, imgUrl, webUrl),
            FoodEntity(11, "userName1", 101, imgUrl + "1", webUrl + "1"),
            FoodEntity(222, "userName2", 102, imgUrl + "1", webUrl + "2")
        )
        foodDao.insert(users)

        val res = foodDao.getFoodsRx().blockingGet()

        assert(res.size == 3)
        assert(res[0].userId == 0)
        assert(res[1].userId == 11)
        assert(res[2].userId == 222)

    }

    @Test
    fun gets_matching_entries() {

        val users = listOf(
            FoodEntity(0, "userName", 100, imgUrl, webUrl),
            FoodEntity(11, "jon", 101, imgUrl + "1", webUrl + "1"),
            FoodEntity(222, "jonSkeet", 102, imgUrl + "2", webUrl + "2")
        )
        foodDao.insert(users)


    }

    @Test
    fun gets_user_by_id() {

        val users = listOf(
            FoodEntity(0, "userName", 100, imgUrl, webUrl),
            FoodEntity(11, "jon", 101, imgUrl + "1", webUrl + "1"),
            FoodEntity(222, "jonSkeet", 102, imgUrl + "2", webUrl + "2")
        )
        foodDao.insert(users)

        val res = foodDao.getFoodDetailRx(11).blockingGet()

        assert(res.userId == 11)
        assert(res.userName == "jon")
        assert(res.reputation == 101)
        assert(res.imageUrl == imgUrl + "1")
        assert(res.websiteUrl == webUrl + "1")

    }

    @Test
    fun deletes_all_entries() {

        val users = listOf(
            FoodEntity(0, "userName", 100, imgUrl, webUrl),
            FoodEntity(11, "userName1", 101, imgUrl + "1", webUrl + "1"),
            FoodEntity(222, "userName2", 102, imgUrl + "2", webUrl + "2")
        )
        foodDao.insert(users)

        foodDao.deleteAll()
        val res = foodDao.getFoodsRx().blockingGet()

        assert(res.isEmpty())

    }

}