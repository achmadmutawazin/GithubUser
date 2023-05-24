package com.example.githubuser.data.local.logindata

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DatabaseHelper {
    private var db:LoginDB?=null
    fun init(context: Context){
        if (db == null){
            db = Room.databaseBuilder(
                context,
                LoginDB::class.java,
                "logindb"
            ).build()
        }
    }

    suspend fun getUser(username: String): Userdata? {
        return withContext(Dispatchers.IO) {
            db?.userDao()?.getUser(username)
        }
    }

    suspend fun insertUser(user: Userdata) {
        withContext(Dispatchers.IO) {
            db?.userDao()?.insertUser(user)
        }
    }
}