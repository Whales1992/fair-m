package com.android.fairmoney.di

import android.app.Application
import com.android.fairmoney.ApplicationClass
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
interface IApplicationComponent : AndroidInjector<ApplicationClass> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): IApplicationComponent?
    }

}