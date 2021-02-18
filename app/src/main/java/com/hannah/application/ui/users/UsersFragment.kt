package com.hannah.application.ui.users

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.hannah.application.R
import com.hannah.application.mvibase.BaseFragment
import com.hannah.application.mvibase.simpleController
import com.hannah.application.ui.userprofile.UserProfileArgs
import com.hannah.application.ui.views.basicRow

import io.reactivex.Observable
import javax.inject.Inject

class UsersFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: UsersViewModel.Factory
    private val viewModel: UsersViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /* send intents directly to the view model
        *  keep the fragment dumb */
        viewModel.processIntents(intents())
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        val list = state.users.invoke()

        list?.forEach { user ->

            basicRow {
                id("ID: " + user.userId.toString())
                title(user.userName)
                subtitle("Rep: " + user.reputation.toString())
                image(user.imageUrl)
                clickListener { _ ->
                    navigateTo(
                        R.id.action_usersListIndex_to_usersProfileFragment,
                        UserProfileArgs(user.userId)
                    )
                }
            }
        }
    }

    /* this gets called anytime the viewModel's state changes
    *  epoxy by design will do a diff check on data
    *  you can access the state of multiple viewModels as well */
    override fun invalidate() = withState(viewModel) { state ->
        super.invalidate()
    }

    /* send all intents to the viewModel via this observable */
    private fun intents(): Observable<UserListIntent> {
        return Observable.merge(
            listOf(
                initialIntent()
            )
        )
    }

    private fun initialIntent(): Observable<UserListIntent.InitialIntent> {
        return Observable.just(UserListIntent.InitialIntent)
    }


}

fun Any.logError(throwable: Throwable, message: String = "") = Log.e(javaClass.simpleName, message, throwable)