package com.example.githubuser.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubuser.data.remote.GithubResponse

@Database(entities = [GithubResponse.ItemsItem::class], version = 1, exportSchema = false)
abstract class AppDb:RoomDatabase() {
    abstract  fun userDao(): Dao
}