package com.example.colorsortrobot.local_db

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object Prefs {

    private lateinit var prefs: SharedPreferences
    private const val FILE_NAME = "shared_prefs"

    fun initPrefs(activity: Activity) {
        prefs = activity.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    // Keys
    private const val KEY_LATEST_ADDRESS = "KEY_LATEST_ADDRESS"

    // Setters
    fun setLatestAddress(address: String) {
        prefs.edit().putString(KEY_LATEST_ADDRESS, address).apply()
    }

    // Getters
    fun getLatestAddress(): String {
        return prefs.getString(KEY_LATEST_ADDRESS, "#")!!
    }

}