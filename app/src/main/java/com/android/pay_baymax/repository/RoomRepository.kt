package com.android.pay_baymax.repository

import android.app.Application
import com.android.pay_baymax.room.AppDatabase
import com.android.pay_baymax.room.entities.RateEntity

class RoomRepository(private val application: Application){
    fun loadRateList(): List<RateEntity> = AppDatabase.getInstance(application).rateDao().getAllRates()
    fun updateRate(saveRate: RateEntity) = AppDatabase.getInstance(application).rateDao().updateCurrencyRate(saveRate)
    fun findRate(currencyCode: String) = AppDatabase.getInstance(application).rateDao().findRateByTypeCurrencyCode(currencyCode)
    fun updateAllRate(saveList: List<RateEntity>) = AppDatabase.getInstance(application).rateDao().updateAllRates(saveList)
}