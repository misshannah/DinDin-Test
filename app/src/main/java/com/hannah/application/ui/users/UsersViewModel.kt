package com.hannah.application.ui.users

import com.airbnb.mvrx.*
import com.hannah.application.model.User
import com.hannah.application.mvibase.MviViewModel
import com.hannah.application.repository.UserRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

/* MvRx has a class, Async. Under the hood it's an observable
 * It's a sealed class with Uninitialized, Loading, Success, and Fail types
 * we can check the type and render accordingly */
data class UsersState (
    val users: Async<List<User>> = Uninitialized,
    val query: String = ""
) : MvRxState

class UsersViewModel @AssistedInject constructor(
    @Assisted state: UsersState,
    private val userRepository: UserRepository
) : MviViewModel<UserListIntent, UsersState>(state) {

    private val disposables = CompositeDisposable()

    override fun processIntents(intents: Observable<UserListIntent>) {
        /* subscribe to our intents with a BehaviorSubject
         * locally subscribe to to the BehaviorSubject */
        disposables.add(intents.subscribe{ actionFromIntent(it) })
    }

    /* we can do actions directly from the intent since the
     * ViewModel directly accesses, modifies, and renders state */
    private fun actionFromIntent(intent: UserListIntent) {
        return when (intent) {
            is UserListIntent.InitialIntent -> fetchInitialUsers()
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
    private fun fetchInitialUsers() = withState { state ->
        if (state.users is Uninitialized) fetchUsers()
        else return@withState
    }

    private fun fetchUsers() = withState { state ->
        if (state.users is Loading) return@withState
        userRepository.getUsersRx().execute { copy(users = it) }
    }


    @AssistedInject.Factory
    interface Factory {
        fun create(state: UsersState): UsersViewModel
    }

    companion object : MvRxViewModelFactory<UsersViewModel, UsersState> {
        override fun create(viewModelContext: ViewModelContext, state: UsersState): UsersViewModel? {
            val fragment = (viewModelContext as FragmentViewModelContext).fragment<UsersFragment>()
            return fragment.viewModelFactory.create(state)
        }
    }
}
