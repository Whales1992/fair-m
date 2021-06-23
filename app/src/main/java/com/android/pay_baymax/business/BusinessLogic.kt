package com.android.pay_baymax.business

class BusinessLogic{
    suspend fun convert(unit:Double, rate:Double, rateAgainst:Double): Double = UnitConvert(unit, rate, rateAgainst).convert()
    fun convertUnSecure(unit:Double, rate:Double, rateAgainst:Double): Double = UnitConvert(unit, rate, rateAgainst).convertUnSecure()
    suspend fun convertLocal(unit:Double, rate:Double, rateAgainst:Double): Double = UnitConvert(unit, rate, rateAgainst).convertLocal()
}
