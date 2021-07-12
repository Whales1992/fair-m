package com.android.fairmoney

import android.app.Application
import com.android.fairmoney.di.DaggerIApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class ApplicationClass: DaggerApplication() {
    private val application: Application

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        return DaggerIApplicationComponent.builder().application(this).build()
    }

    fun getApplication(): Application {
        return application
    }

    init {
        super.onCreate()
        application = this
    }

}