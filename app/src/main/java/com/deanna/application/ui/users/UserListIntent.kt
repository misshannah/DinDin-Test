package com.hannah.application.ui.users

import com.hannah.application.mvibase.MviIntent

sealed class UserListIntent : MviIntent {
    object InitialIntent : UserListIntent()
    object RefreshIntent : UserListIntent()
    object ClearSearchIntent : UserListIntent()
    data class SearchIntent(val query: String, val network: Boolean = false) : UserListIntent()
}