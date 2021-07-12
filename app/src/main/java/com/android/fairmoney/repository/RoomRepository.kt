package com.android.fairmoney.repository

import com.android.fairmoney.database.room.AppDatabase
import com.android.fairmoney.database.room.entities.UserDetailEntity
import com.android.fairmoney.database.room.entities.UserEntity

class RoomRepository(private val appDatabase: AppDatabase){
    suspend fun loadUsersList(): List<UserEntity> = appDatabase.userDao().getAllUsers()
    suspend fun updateAllUsers(saveList: List<UserEntity>) = appDatabase.userDao().updateAllUsers(saveList)

    suspend fun loadUserDetailByUserId(userId: String) = appDatabase.userDetailDao().findUserDetailByUserId(userId)
    suspend fun updateUserDetail(userDetailEntity: UserDetailEntity) = appDatabase.userDetailDao().updateUserDetail(userDetailEntity)
    suspend fun updateUserLocation(userLocation: UserDetailEntity.Location) = appDatabase.userDetailDao().updateUserLocation(userLocation)
}