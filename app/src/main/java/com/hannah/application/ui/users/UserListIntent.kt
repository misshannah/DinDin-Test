package com.hannah.application.ui.users

import com.hannah.application.mvibase.MviIntent

sealed class UserListIntent : MviIntent {
    object InitialIntent : UserListIntent()
}