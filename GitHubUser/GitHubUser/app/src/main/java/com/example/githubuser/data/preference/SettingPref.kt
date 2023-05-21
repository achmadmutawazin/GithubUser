package com.example.githubuser.data.preference

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.prefDataStore by preferencesDataStore("settings")

class SettingPref constructor(context:Context) {

    private val settingDataStore = context.prefDataStore
    private val theme = booleanPreferencesKey("theme_setting")

    fun getThemeSetting():Flow<Boolean> =
        settingDataStore.data.map{preferences->
            preferences[theme]?:false
        }

    suspend fun ThemeSetting (DarkMode:Boolean){
        settingDataStore.edit { preferences->
            preferences[theme] = DarkMode
        }
    }
}