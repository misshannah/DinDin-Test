package com.hannah.application.di

import com.hannah.application.ui.MainActivity
import com.hannah.application.ui.food.FoodsFragment
import com.hannah.application.ui.fooditem.FoodItemFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityFragmentBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindFoodsFragment(): FoodsFragment

    @ContributesAndroidInjector
    abstract fun bindFoodItemFragment(): FoodItemFragment
}
