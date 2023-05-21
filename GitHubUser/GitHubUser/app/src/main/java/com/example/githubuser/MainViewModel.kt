package com.example.githubuser

import androidx.lifecycle.*
import com.example.githubuser.data.model.Result
import com.example.githubuser.data.preference.SettingPref
import com.example.githubuser.data.remote.ApiConfig
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val pref: SettingPref):ViewModel() {

    val resultUser = MutableLiveData<Result>()
    val token = "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"
    private val _event = MutableLiveData<MainActivityEvent>()
    val event: LiveData<MainActivityEvent>
        get() = _event
    fun getTheme() = pref.getThemeSetting().asLiveData()
    private fun handleNoUserFound() {
        _event.value = MainActivityEvent.ShowMessage("No user found")
    }
    fun getUser() {
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .getApiService(token)
                    .getUsers()

                emit(response)
            }.onStart {
                resultUser.value = Result.Loading(true)
            }.onCompletion {
                resultUser.value = Result.Loading(false)
            }.catch { exception ->
                resultUser.value = Result.Error(exception)
                handleNoUserFound()
            }.collect { userList ->
                if (userList.isNotEmpty()) {
                    resultUser.value = Result.Success(userList)
                } else {
                    handleNoUserFound()
                }
            }
        }
    }

    fun Search(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .getApiService(token)
                    .getSearch(
                        mapOf(
                            "q" to username
                        )
                    )

                emit(response)
            }.onStart {
                resultUser.value = Result.Loading(true)
            }.onCompletion {
                resultUser.value = Result.Loading(false)
            }.catch { exception ->
                resultUser.value = Result.Error(exception)
                handleNoUserFound()
            }.collect { searchResponse ->
                if (searchResponse.items.isNotEmpty()) {
                    resultUser.value = Result.Success(searchResponse.items)
                } else {
                    handleNoUserFound()
                }
            }
        }
    }

    class Factory(private val pref: SettingPref) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(pref) as T
    }
}