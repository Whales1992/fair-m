package com.android.fairmoney.database.room.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.android.fairmoney.models.UserDetail

@Entity
data class UserDetailAndLocation(
    @Embedded val userDetailEntity : UserDetailEntity,
    @Relation(
            parentColumn = "userId",
            entityColumn = "userOwnerId"
    )
    val location: UserDetailEntity.Location
    )