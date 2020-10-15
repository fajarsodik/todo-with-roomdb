package com.jazastudio.todolist

import android.app.Application
import androidx.multidex.MultiDex

class TodoApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Prefs.init(applicationContext)
        MultiDex.install(this)
    }
}