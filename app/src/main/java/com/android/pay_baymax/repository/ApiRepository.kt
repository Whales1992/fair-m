package com.android.pay_baymax.repository

import com.android.pay_baymax.rectrofit.ApiCalls
import com.android.pay_baymax.rectrofit.IResponse
import com.android.pay_baymax.rectrofit.dto.CurrencyConversionRates
import com.android.pay_baymax.rectrofit.dto.CurrencyConversionTypes

class ApiRepository(private val apiCalls: ApiCalls) {
    fun getCurrencyTypeClient() = apiCalls.getCurrencyTypesClient()
    fun getCurrencyRatesClient() = apiCalls.getCurrencyRatesClient()
}