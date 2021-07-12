package com.android.fairmoney.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.fairmoney.room.dao.RateDao
import com.android.fairmoney.room.entities.RateEntity

@Database(entities = [RateEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rateDao(): RateDao
}