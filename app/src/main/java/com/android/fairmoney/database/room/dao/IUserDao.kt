package com.android.fairmoney.database.room.dao

import androidx.room.*
import com.android.fairmoney.database.room.entities.UserEntity

@Dao
interface IUserDao {
    @Query("SELECT * from userentity")
    fun getAllUsers(): List<UserEntity>

    @Query("SELECT * from userentity WHERE userId = :userId")
    fun findUserByTypeUserId(userId: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateAllUsers(userList: List<UserEntity>)
}