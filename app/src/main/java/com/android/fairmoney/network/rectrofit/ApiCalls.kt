package com.android.fairmoney.rectrofit

import com.android.fairmoney.rectrofit.dto.CurrencyConversionRates
import com.android.fairmoney.rectrofit.dto.CurrencyConversionTypes
import io.reactivex.Observable
import retrofit2.Retrofit

class ApiCalls(retrofit: Retrofit, private val token :Map<String, String>) {

    private var iApi: IApi = retrofit.create(IApi::class.java)

    fun getCurrencyTypesClient(): Observable<CurrencyConversionTypes> {
        return iApi.getCurrencyTypes(token)
    }

    fun getCurrencyRatesClient(): Observable<CurrencyConversionRates> {
        return iApi.getCurrencyConversionRates(token)
    }
}