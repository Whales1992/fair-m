package com.android.pay_baymax.business

interface IUnitConverter {
    suspend fun convert() : Double
    suspend fun convertLocal() :Double
}