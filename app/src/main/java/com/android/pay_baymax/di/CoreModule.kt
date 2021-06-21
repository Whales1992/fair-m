package com.android.pay_baymax.di

import android.app.Application
import androidx.room.Room
import com.android.pay_baymax.ApiToken
import com.android.pay_baymax.DATABASE_NAME
import com.android.pay_baymax.RequestBaseUrl
import com.android.pay_baymax.room.AppDatabase
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class CoreModule {

    @Singleton
    @Provides
    @Named("token")
    fun provideToken(): Map<String, String>{
        return mapOf("access_key" to ApiToken)
    }

    @Singleton
    @Provides
    fun provideClient() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val ongoing = chain.request().newBuilder()
                    chain.proceed(ongoing.build())
                })
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()
    }

    @Singleton
    @Provides
    fun provideNetworkClient(provideClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(RequestBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(provideClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideDataBase(application: Application) : AppDatabase {
        return Room.databaseBuilder(application.applicationContext, AppDatabase::class.java, DATABASE_NAME).build()
    }
}
