package com.example.githubuser.data.local.logindata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao{
    @Query("SELECT * FROM users WHERE username = :username")
    fun getUser(username:String):Userdata?

    @Insert
    fun insertUser(user:Userdata)

}