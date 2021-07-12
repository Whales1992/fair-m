package com.android.fairmoney.database.room.dao

import androidx.room.*
import com.android.fairmoney.database.room.entities.UserEntity

@Dao
interface IUserDao {
    @Query("SELECT * from userentity")
    suspend fun getAllUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAllUsers(userList: List<UserEntity>)
}