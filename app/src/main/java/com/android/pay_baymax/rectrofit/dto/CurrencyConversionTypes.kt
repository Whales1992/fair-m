package com.android.pay_baymax.rectrofit.dto

import com.google.gson.annotations.SerializedName

data class CurrencyConversionTypes(
    @SerializedName("success") val success: Boolean,
    @SerializedName("terms") val terms: String,
    @SerializedName("privacy") val privacy: String,
    @SerializedName("currencies") val currencies: Map<String, String>)