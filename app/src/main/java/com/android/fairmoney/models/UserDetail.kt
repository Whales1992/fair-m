package com.android.fairmoney.models

data class UserDetail(
                val id: String,
                val title: String,
                val firstName: String,
                val lastName: String,
                val gender: String,
                val email: String,
                val dateOfBirth: String,
                val phone: String,
                val picture:String,
                val location:Location,
                val registerDate: String,
                val updatedAt: String){

        class Location(
                val street: String,
                val city: String,
                val state: String,
                val country: String,
                val timezone: String)
}