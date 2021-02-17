package com.hannah.application.di

import com.hannah.application.ui.MainActivity
import com.hannah.application.ui.userprofile.UserProfileFragment
import com.hannah.application.ui.users.UsersFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityFragmentBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindUsersFragment(): UsersFragment

    @ContributesAndroidInjector
    abstract fun bindUserProfileFragment(): UserProfileFragment
}
