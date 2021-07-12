package com.android.fairmoney.database.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.fairmoney.models.User

@Entity
data class UserEntity(
    @PrimaryKey
    val userId: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val picture: String
){

    companion object {
        fun from(user: User.DataBeam): UserEntity {
            return UserEntity(user.id, user.title, user.firstName, user.lastName, user.email, user.picture)
        }
    }

    fun toUser(): User.DataBeam {
        return User.DataBeam(userId, title, firstName, lastName, email, picture)
    }
}