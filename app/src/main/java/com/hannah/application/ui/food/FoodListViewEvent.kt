package com.hannah.application.ui.food

sealed class FoodListViewEvent {
    data class FoodClick(val userId: Int) : FoodListViewEvent()
}