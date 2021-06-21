package com.android.pay_baymax.business

class BusinessLogic{
    suspend fun convert(unit:Double, rate:Double, rateAgainst:Double): Double = UnitConvert(unit, rate, rateAgainst).convert()
    suspend fun convertLocal(unit:Double, rate:Double, rateAgainst:Double): Double = UnitConvert(unit, rate, rateAgainst).convertLocal()
}