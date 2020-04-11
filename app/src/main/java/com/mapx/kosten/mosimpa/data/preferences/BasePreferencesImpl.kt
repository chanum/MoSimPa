package com.mapx.kosten.mosimpa.data.preferences

import android.content.Context
import android.content.SharedPreferences

open class BasePreferencesImpl constructor(
    context: Context,
    name: String
) {
    private lateinit var preferences: SharedPreferences

    init {
        if (context.applicationContext != null) {
            this.preferences = context.applicationContext.getSharedPreferences(name,
                Context.MODE_PRIVATE
            )
        }
    }

    protected fun putString(key: String, value: String?) {
        preferences.edit().putString(key, value).apply()
    }

    protected fun getString(key: String): String? {
        return preferences.getString(key, null)
    }

    protected fun putBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    protected fun getBoolean(key: String): Boolean? {
        return preferences.getBoolean(key, false)
    }

    protected fun putInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    protected fun getInt(key: String): Int? {
        return preferences.getInt(key, -1)
    }
}