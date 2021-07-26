package com.james.delivery.util

import android.annotation.SuppressLint
import android.content.Context
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPreference @Inject constructor(@ApplicationContext context: Context){
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun getBoolean(query: String): Boolean {
        return prefs.getBoolean(query, false)
    }

    @SuppressLint("CommitPrefEdits")
    fun setBoolean(query: String, value: Boolean) {
        prefs.edit().putBoolean(query, value).apply()
    }
}