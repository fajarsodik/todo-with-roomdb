package com.jazastudio.todolist

import android.app.Application

class TodoApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Prefs.init(applicationContext)
    }
}