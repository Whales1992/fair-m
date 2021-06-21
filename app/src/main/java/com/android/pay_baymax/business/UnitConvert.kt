package com.android.pay_baymax.business

class UnitConvert(private val unit:Double, private val rate:Double, private val rateAgainst:Double):IUnitConverter{
    override suspend fun convert() : Double{
        return ((rate*unit)/rateAgainst)
    }

    override suspend fun convertLocal() : Double{
//        TODO("Not yet implemented")
        return 0.0
    }
}