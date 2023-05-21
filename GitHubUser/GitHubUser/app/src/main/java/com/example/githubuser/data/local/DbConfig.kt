package com.example.githubuser.data.local

import android.content.Context
import androidx.room.Room

class DbConfig(private val context:Context) {
    private val db = Room.databaseBuilder(context, AppDb::class.java, "usergithub.db")
        .allowMainThreadQueries()
        .build()

    val userDao = db.userDao()

}