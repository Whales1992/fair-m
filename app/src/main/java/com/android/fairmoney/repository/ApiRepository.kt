package com.android.fairmoney.repository

import com.android.fairmoney.network.rectrofit.ApiCalls

class ApiRepository(private val apiCalls: ApiCalls) {
    fun getUsersApiRepository() = apiCalls.getUsersApiCall()
    fun getUsersDetailApiRepository(userId : String) = apiCalls.getUsersDetailApiCall(userId)
}