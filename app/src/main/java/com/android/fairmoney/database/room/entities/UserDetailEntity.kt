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
        fun from(user: User): UserEntity {
            return UserEntity(user.id, user.title, user.firstName, user.lastName, user.email, user.picture)
        }
    }

    fun toUser(): User {
        return User(userId, title, firstName, lastName, email, picture)
    }
}