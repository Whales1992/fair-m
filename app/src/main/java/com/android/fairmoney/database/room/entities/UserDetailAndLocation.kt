/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.fairmoney.database.room.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.android.fairmoney.models.UserDetail

@Entity
data class UserDetailAndLocation(
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
        fun from(userDetail: UserDetail): UserDetailAndLocation {
            return UserDetailAndLocation(
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

    private fun toLocation(location: Location): UserDetail.Location{
        return UserDetail.Location(
                location.street,
                location.city,
                location.state,
                location.country,
                location.timezone
        )
    }

    data class UserDetailAndLocation(
            @Embedded val userDetail: UserDetailAndLocation,
            @Relation(
                    parentColumn = "userId",
                    entityColumn = "userOwnerId"
            )
            val location: Location
    ){
        fun toUserDetail(): UserDetail {
            return UserDetail(
                    userDetail.userId,
                    userDetail.title,
                    userDetail.firstName,
                    userDetail.lastName,
                    userDetail.gender,
                    userDetail.email,
                    userDetail.dateOfBirth,
                    userDetail.phone,
                    userDetail.picture,
                    userDetail.toLocation(location),
                    userDetail.registerDate,
                    userDetail.updatedAt)
        }
    }
}