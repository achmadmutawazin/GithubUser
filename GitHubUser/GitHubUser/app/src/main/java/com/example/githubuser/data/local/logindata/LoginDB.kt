package com.example.githubuser.data.local.logindata

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Userdata::class],version = 1, exportSchema = false)
abstract class LoginDB: RoomDatabase(){
    abstract fun userDao():UserDao
}