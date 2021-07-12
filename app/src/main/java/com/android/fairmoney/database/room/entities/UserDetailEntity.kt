package com.android.fairmoney.database.room.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.android.fairmoney.models.UserDetail

@Entity
data class UserDetailEntity(
    @PrimaryKey
    val userId: String,
    val title: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val email: String,
    val dateOfBirth: String,
    val phone: String,
    val picture: String,
    @Embedded val location : Location,
    val registerDate: String,
    val updatedAt: String,
    ){

    @Entity
    data class Location(
            @PrimaryKey
            val userOwnerId: String,
            val street: String,
            val city: String,
            val state: String,
            val country: String,
            val timezone: String
            )

    companion object {
        fun from(userDetail: UserDetail): UserDetailEntity {
            return UserDetailEntity(
                    userDetail.id,
                    userDetail.title,
                    userDetail.firstName,
                    userDetail.lastName,
                    userDetail.gender,
                    userDetail.email,
                    userDetail.dateOfBirth,
                    userDetail.phone,
                    userDetail.picture,
                    fromLocation(userDetail),
                    userDetail.registerDate,
                    userDetail.updatedAt)
        }

        private fun fromLocation(userDetail: UserDetail):Location{
            return Location(
                    userDetail.id,
                    userDetail.location.street,
                    userDetail.location.city,
                    userDetail.location.state,
                    userDetail.location.country,
                    userDetail.location.timezone
            )
        }
    }

    fun toUserDetail(): UserDetail {
        return UserDetail(
                userId,
                title,
                firstName,
                lastName,
                gender,
                email,
                dateOfBirth,
                phone,
                picture,
                toLocation(location),
                registerDate,
                updatedAt)
    }

    private fun toLocation(location: Location): UserDetail.Location{
        return UserDetail.Location(
                location.street,
                location.city,
                location.state,
                location.country,
                location.timezone
        )
    }
}