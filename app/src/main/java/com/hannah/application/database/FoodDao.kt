package com.hannah.application.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(foods: List<FoodEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(food: FoodEntity)

    @Query("SELECT * FROM foodsResponses")
    fun getFoodsRx(): Single<List<FoodEntity>>

    @Query("SELECT * FROM foodsResponses WHERE id = :userId")
    fun getFoodDetailRx(userId: Int): Single<FoodEntity>

    @Query("DELETE FROM foodsResponses")
    fun deleteAll()
}