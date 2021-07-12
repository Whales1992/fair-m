package com.android.fairmoney.di

import com.android.fairmoney.activities.MainActivity
import com.android.fairmoney.activities.UserDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity?

    @ContributesAndroidInjector
    abstract fun contributeUserDetailActivity(): UserDetailActivity?
}