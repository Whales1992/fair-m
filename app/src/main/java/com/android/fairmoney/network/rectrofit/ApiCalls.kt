package com.android.fairmoney.network.rectrofit

import com.android.fairmoney.models.User
import com.android.fairmoney.models.UserDetail
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit

class ApiCalls(retrofit: Retrofit) {

    private var iApi: IApi = retrofit.create(IApi::class.java)

    fun getUsersApiCall(): Call<User> {
        return iApi.getUsers()
    }

    fun getUsersDetailApiCall(userId : String): Call<UserDetail> {
        return iApi.getUsersDetail(userId)
    }
}