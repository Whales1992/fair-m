package com.android.fairmoney.models

data class User(val data: List<DataBeam>){

        class DataBeam(
                val id: String,
                val title: String,
                val firstName: String,
                val lastName: String,
                val email: String,
                val picture:String
                )
}