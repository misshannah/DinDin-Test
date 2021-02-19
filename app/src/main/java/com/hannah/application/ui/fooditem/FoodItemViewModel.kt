package com.hannah.application.ui.fooditem

import android.os.Parcelable
import com.airbnb.mvrx.*
import com.hannah.application.model.Food
import com.hannah.application.mvibase.MviViewModel
import com.hannah.application.repository.FoodRepository
import com.hannah.application.ui.food.FoodListIntent
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Observable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FoodItemArgs(val id: Int) : Parcelable

data class FoodItemState(
    val id: Int,
    val food: Async<Food> = Uninitialized
) : MvRxState {
    constructor(args: FoodItemArgs) : this(id = args.id)
}

class FoodItemViewModel @AssistedInject constructor(
    @Assisted state: FoodItemState,
    private val foodRepository: FoodRepository
) : MviViewModel<FoodListIntent, FoodItemState>(state) {

    init {
        fetchUser()

    }

    override fun processIntents(intents: Observable<FoodListIntent>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun states(): Observable<FoodItemState> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun fetchUser() = withState { state ->
        if (state.food is Loading) return@withState
        foodRepository.getFoodRx(state.id).execute { copy(food = it) }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(state: FoodItemState): FoodItemViewModel
    }

    companion object : MvRxViewModelFactory<FoodItemViewModel, FoodItemState> {
        override fun create(viewModelContext: ViewModelContext, state: FoodItemState): FoodItemViewModel? {
            val fragment = (viewModelContext as FragmentViewModelContext).fragment<FoodItemFragment>()
            return fragment.viewModelFactory.create(state)
        }
    }
}
