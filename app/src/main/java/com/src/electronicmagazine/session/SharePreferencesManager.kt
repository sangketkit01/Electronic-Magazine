package com.src.electronicmagazine.session

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.src.electronicmagazine.data.User

class SharePreferencesManager(context : Context) {
    private val preferences : SharedPreferences =
        context.getSharedPreferences("preferences_data",Context.MODE_PRIVATE)
    private val gson = Gson()

    var loggedIn : Boolean
        get() = preferences.getBoolean(KEY_IS_LOGGED_IN,false)
        set(value) = preferences.edit().putBoolean(KEY_IS_LOGGED_IN,value).apply()

    var user : User?
        get(){
            val json = preferences.getString(KEY_USER,null)
            return json?.let { gson.fromJson(it,User::class.java) }
        }
        set(value){
            val json = gson.toJson(value)
            preferences.edit().putString(KEY_USER,json).apply()
        }

    fun clear(){
        preferences.edit().clear().apply()
    }

    companion object{
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER = "user_date"
    }
}