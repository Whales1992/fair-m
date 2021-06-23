package com.android.pay_baymax.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.pay_baymax.room.dao.RateDao
import com.android.pay_baymax.room.entities.RateEntity

@Database(entities = [RateEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rateDao(): RateDao
}