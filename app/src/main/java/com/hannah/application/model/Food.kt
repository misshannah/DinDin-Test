package com.hannah.application.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Food(
    val userId: Int,
    val userName: String,
    val reputation: Int,
    val imageUrl: String,
    val websiteUrl: String?
)
