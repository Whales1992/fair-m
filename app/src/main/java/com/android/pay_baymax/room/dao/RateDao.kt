package com.android.pay_baymax.room.dao

import androidx.room.*
import com.android.pay_baymax.room.entities.RateEntity

@Dao
interface RateDao {
    @Query("SELECT * from rateentity")
    fun getAllRates(): List<RateEntity>

    @Query("SELECT * from rateentity WHERE currency_code = :currencyCode")
    fun findRateByTypeCurrencyCode(currencyCode: String): RateEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateCurrencyRate(rateData: RateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAllRates(rateList: List<RateEntity>)
}