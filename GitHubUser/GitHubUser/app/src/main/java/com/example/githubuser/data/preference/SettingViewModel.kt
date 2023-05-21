package com.example.githubuser.data.preference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingViewModel (private val pref: SettingPref):ViewModel(){

    fun getTheme() = pref.getThemeSetting().asLiveData()

    fun saveTheme(isDark:Boolean){
        viewModelScope.launch {
        pref.ThemeSetting(isDark)
    }}

    class Factory(private val pref: SettingPref) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = SettingViewModel(pref) as T
    }
}