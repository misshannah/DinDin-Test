package com.hannah.application.ui.userprofile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.hannah.application.mvibase.BaseFragment
import com.hannah.application.mvibase.simpleController
import com.hannah.application.ui.views.foodItem
import javax.inject.Inject

class UserProfileFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: UserProfileViewModel.Factory
    private val viewModel: UserProfileViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.asyncSubscribe(UserProfileState::user)
    }

    override fun invalidate() = withState(viewModel) { state ->
        super.invalidate()
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        val u = state.user.invoke()

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
