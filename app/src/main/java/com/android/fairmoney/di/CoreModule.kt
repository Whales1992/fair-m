package com.android.fairmoney.di

import android.app.Application
import androidx.room.Room
import com.android.fairmoney.database.room.AppDatabase
import com.android.fairmoney.utils.*
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
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
    fun provideClient() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val ongoing = chain.request().newBuilder()
                    ongoing.addHeader("app-id", API_ID)
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
            .baseUrl(REQUEST_BASE_URL)
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

    @Singleton
    @Provides
    fun okHttp3Downloader(provideClient : OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(provideClient)
    }

    @Singleton
    @Provides
    fun picasso(application: Application, okHttp3Downloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(application)
                .downloader(okHttp3Downloader)
                .loggingEnabled(false)
                .build()
    }
}