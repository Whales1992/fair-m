package com.android.fairmoney.network.rectrofit

import com.android.fairmoney.models.User
import com.android.fairmoney.models.UserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IApi {

    @GET("user?limit=100")
    fun getUsers(): Call<User>

    @GET("user/{userId}")
    fun getUsersDetail(@Path("userId") userId : String ): Call<UserDetail>

}