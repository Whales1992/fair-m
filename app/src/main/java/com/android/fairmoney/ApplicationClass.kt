package com.android.pay_baymax

import android.app.Application
import com.android.pay_baymax.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class ApplicationClass: DaggerApplication() {
    private val application: Application

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? {
        return DaggerApplicationComponent.builder().application(this).build()
    }

    fun getApplication(): Application {
        return application
    }

    init {
        super.onCreate()
        application = this
    }


}