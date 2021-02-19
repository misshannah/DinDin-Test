package com.hannah.application.ui.fooditem

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.hannah.application.mvibase.BaseFragment
import com.hannah.application.mvibase.simpleController
import com.hannah.application.ui.views.foodItem
import javax.inject.Inject

class FoodItemFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: FoodItemViewModel.Factory
    private val viewModel: FoodItemViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.asyncSubscribe(FoodItemState::food)
    }

    override fun invalidate() = withState(viewModel) { state ->
        super.invalidate()
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        val u = state.food.invoke()

        if (u == null) {
        } else {

            foodItem {
                id(u.userId.toString())
                profileName(u.userName)
                userId(u.userId.toString())
                rep("Rep: " + u.reputation.toString())
                website(u.websiteUrl)
                image(u.imageUrl)

            }
        }
    }
}
