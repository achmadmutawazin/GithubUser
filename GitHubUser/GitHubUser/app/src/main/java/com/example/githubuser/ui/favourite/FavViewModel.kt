package com.example.githubuser.ui.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.data.local.DbConfig

class FavViewModel (private val dbModule: DbConfig) : ViewModel(){

    fun getUserFav() = dbModule.userDao.loadAll()

    class Factory(private val db: DbConfig): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavViewModel(db) as T
    }
}