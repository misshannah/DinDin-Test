package com.hannah.application.ui.users

sealed class UserListViewEvent {
    data class UserClick(val userId: Int) : UserListViewEvent()
}