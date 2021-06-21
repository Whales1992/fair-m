package com.android.pay_baymax.rectrofit.dto

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.FlowCollector

data class CurrencyConversionRates(
    @SerializedName("success") val success: Boolean,
    @SerializedName("terms") val terms: String,
    @SerializedName("privacy") val privacy: String,
    @SerializedName("source") val source: String,
    @SerializedName("quotes") val quotes: Map<String, Double>)