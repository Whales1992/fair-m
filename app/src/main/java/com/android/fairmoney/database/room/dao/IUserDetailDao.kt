package com.android.fairmoney.database.room.dao

import androidx.room.*
import com.android.fairmoney.database.room.entities.UserDetailAndLocation
import com.android.fairmoney.database.room.entities.UserDetailEntity

@Dao
interface IUserDetailDao {

    @Query("SELECT * from userdetailentity WHERE userId = :userId")
    suspend fun findUserDetailByUserId(userId: String): UserDetailAndLocation

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserDetail(userDetail: UserDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserLocation(userLocation: UserDetailEntity.Location)

}