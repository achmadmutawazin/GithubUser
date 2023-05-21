package com.example.githubuser.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.remote.GithubResponse
import com.example.githubuser.data.model.Result
import com.example.githubuser.data.local.DbConfig
import com.example.githubuser.data.remote.ApiConfig
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel (private val db: DbConfig) :ViewModel(){
    val token = "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"
    val resultDetailUser = MutableLiveData<Result>()
    val resultFollower = MutableLiveData<Result>()
    val resultFollowing = MutableLiveData<Result>()
    val resultisfav = MutableLiveData<Boolean>()
    val resultdelfav = MutableLiveData<Boolean>()

    private var isFavourite = false

    fun setFav(item: GithubResponse.ItemsItem?){
        viewModelScope.launch {
        item?.let {
            if (isFavourite){
                db.userDao.delete(item)
                resultdelfav.value = true
            }else{
                db.userDao.insert(item)
                resultisfav.value = true
            }
        }
            isFavourite = !isFavourite
    }
    }
    fun findFav(id:Int, listenFav: ()-> Unit){
        viewModelScope.launch {
            val user = db.userDao.findById(id)
            if (user != null){
                listenFav()
                isFavourite = true
            }
        }
    }

    fun getDetailUser(username:String){
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .getApiService(token)
                    .getDetailUsers(username)

                emit(response)
            }.onStart {
                resultDetailUser.value = Result.Loading(true)
            }.onCompletion {
                resultDetailUser.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultDetailUser.value = Result.Error(it)
            }.collect{
                resultDetailUser.value = Result.Success(it)
            }
        }
    }

    fun getFollowers(username:String){
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .getApiService(token)
                    .getFollowers(username)

                emit(response)
            }.onStart {
                resultFollower.value = Result.Loading(true)
            }.onCompletion {
                resultFollower.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultFollower.value = Result.Error(it)
            }.collect{
                resultFollower.value = Result.Success(it)
            }
        }
    }

    fun getFollowing(username:String){
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .getApiService(token)
                    .getFollowing(username)

                emit(response)
            }.onStart {
                resultFollowing.value = Result.Loading(true)
            }.onCompletion {
                resultFollowing.value = Result.Loading(false)
            }.catch {
                it.printStackTrace()
                resultFollowing.value = Result.Error(it)
            }.collect{
                resultFollowing.value = Result.Success(it)
            }
        }
    }

    class Factory(private val db: DbConfig):ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailViewModel(db) as T
    }
}