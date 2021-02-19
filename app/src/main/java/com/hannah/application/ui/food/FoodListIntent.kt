package com.hannah.application.ui.food

import com.hannah.application.mvibase.MviIntent

sealed class FoodListIntent : MviIntent {
    object InitialIntent : FoodListIntent()
}