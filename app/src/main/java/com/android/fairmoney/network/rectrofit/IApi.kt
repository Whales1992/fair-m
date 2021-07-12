package com.android.fairmoney.rectrofit

import com.android.fairmoney.rectrofit.dto.CurrencyConversionRates
import com.android.fairmoney.rectrofit.dto.CurrencyConversionTypes
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface IApi {

    @GET("list")
    fun getCurrencyTypes(@QueryMap token : Map<String, String>): Observable<CurrencyConversionTypes>

    @GET("live")
    fun getCurrencyConversionRates(@QueryMap token : Map<String, String>): Observable<CurrencyConversionRates>

}