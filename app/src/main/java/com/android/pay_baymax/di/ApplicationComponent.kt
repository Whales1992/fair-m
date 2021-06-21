package com.android.pay_baymax.di

import android.app.Application
import com.android.pay_baymax.ApplicationClass
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (CoreModule::class),
    (AndroidSupportInjectionModule::class),
    (ActivityBuildersModule::class)])
interface ApplicationComponent : AndroidInjector<ApplicationClass> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent?
    }

}