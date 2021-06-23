package com.android.pay_baymax.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RateEntity(
    @PrimaryKey
    @ColumnInfo(name = "currency_code") val currencyCode: String,
    @ColumnInfo(name = "currency_rate") val currencyRate: Double?,
    @ColumnInfo(name = "currency_name") val currencyName: String?,
)