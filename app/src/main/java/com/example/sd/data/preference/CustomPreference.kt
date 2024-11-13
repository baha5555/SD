package com.example.sd.data.preference

import android.content.Context
import android.content.SharedPreferences
import com.example.sd.utils.Constants

import dagger.hilt.android.qualifiers.ApplicationContext

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomPreference @Inject constructor(@ApplicationContext context : Context){

    private val prefs: SharedPreferences = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE)


    fun getAccessToken(): String {
        return prefs.getString(Constants.ACCESS_TOKEN, "")!!
    }
    fun setAccessToken(query: String) {
        prefs.edit().putString(Constants.ACCESS_TOKEN, query).apply()
    }
}
