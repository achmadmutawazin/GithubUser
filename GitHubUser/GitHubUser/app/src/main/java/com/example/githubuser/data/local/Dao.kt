package com.example.githubuser.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.example.githubuser.data.remote.GithubResponse

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: GithubResponse.ItemsItem)

    @Query("SELECT * FROM user")
    fun loadAll():LiveData<MutableList<GithubResponse.ItemsItem>>

    @Query("SELECT * FROM user WHERE id LIKE :id LIMIT 1")
    fun findById(id:Int): GithubResponse.ItemsItem

    @Delete
    fun delete(user: GithubResponse.ItemsItem)
}