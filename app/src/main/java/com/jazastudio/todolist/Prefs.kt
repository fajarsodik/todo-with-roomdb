package com.jazastudio.todolist

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {

    private val sharedPrefs: SharedPreferences
    private val ACTIVSTATUS = "activeStatus"
    private var ISFIRST = "isFirst"

    companion object {
        private val PREF_NAME = "com.jazastudio.todolist.Prefs"

        @JvmStatic
        lateinit var instance: Prefs
            private set

        @JvmStatic
        fun init(context: Context) {
            instance = Prefs(context)
        }
    }

    init {
        sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    var activStatus: String?
        get() = sharedPrefs.getString(ACTIVSTATUS, null)
        set(value) {
            sharedPrefs.edit().putString(ACTIVSTATUS, value).apply()
        }

    var isFirst: Boolean
    get() = sharedPrefs.getBoolean(ISFIRST, true)
    set(value) {
        sharedPrefs.edit().putBoolean(ISFIRST, value).apply()
    }
}