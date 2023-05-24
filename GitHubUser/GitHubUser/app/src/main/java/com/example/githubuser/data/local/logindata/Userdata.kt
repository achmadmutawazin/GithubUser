package com.example.githubuser.data.local.logindata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class Userdata(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val username:String,
    val password:String
)
