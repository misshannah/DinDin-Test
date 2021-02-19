package com.hannah.application.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hannah.application.model.Food

@Entity(tableName = "foodsResponses")
data class FoodEntity(
    @ColumnInfo(name = "id") @PrimaryKey @field:NonNull val userId: Int,
    @ColumnInfo(name = "display_name") val userName: String,
    @ColumnInfo(name = "reputation") val reputation: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "website_url") val websiteUrl: String?
) {
    fun toFoodModel() : Food {
        return Food(
            this.userId,
            this.userName,
            this.reputation,
            this.imageUrl,
            this.websiteUrl
        )
    }
}