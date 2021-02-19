package com.hannah.application.ui.food

import com.airbnb.mvrx.*
import com.hannah.application.model.Food
import com.hannah.application.mvibase.MviViewModel
import com.hannah.application.repository.FoodRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

/* MvRx has a class, Async. Under the hood it's an observable
 * It's a sealed class with Uninitialized, Loading, Success, and Fail types
 * we can check the type and render accordingly */
data class FoodsState (
    val users: Async<List<Food>> = Uninitialized,
    val query: String = ""
) : MvRxState

class FoodsViewModel @AssistedInject constructor(
    @Assisted state: FoodsState,
    private val foodRepository: FoodRepository
) : MviViewModel<FoodListIntent, FoodsState>(state) {

    private val disposables = CompositeDisposable()

    override fun processIntents(intents: Observable<FoodListIntent>) {
        /* subscribe to our intents with a BehaviorSubject
         * locally subscribe to to the BehaviorSubject */
        disposables.add(intents.subscribe{ actionFromIntent(it) })
    }

    /* we can do actions directly from the intent since the
     * ViewModel directly accesses, modifies, and renders state */
    private fun actionFromIntent(intent: FoodListIntent) {
        return when (intent) {
            is FoodListIntent.InitialIntent -> fetchInitialFoods()
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    /*  With these functions, we're returning an observable, then using
     *  execute to subscribe to it and emit state (Loading, Success, etc).
     *  Loading will actually have a "null" value, whereas Success will
     *  include the result.
     *  the subscription disposal is taken care of by MvRx */
    private fun fetchInitialFoods() = withState { state ->
        if (state.users is Uninitialized) fetchFoods()
        else return@withState
    }

    private fun fetchFoods() = withState { state ->
        if (state.users is Loading) return@withState
        foodRepository.getFoodRx().execute { copy(users = it) }
    }


    @AssistedInject.Factory
    interface Factory {
        fun create(state: FoodsState): FoodsViewModel
    }

    companion object : MvRxViewModelFactory<FoodsViewModel, FoodsState> {
        override fun create(viewModelContext: ViewModelContext, state: FoodsState): FoodsViewModel? {
            val fragment = (viewModelContext as FragmentViewModelContext).fragment<FoodsFragment>()
            return fragment.viewModelFactory.create(state)
        }
    }
}
