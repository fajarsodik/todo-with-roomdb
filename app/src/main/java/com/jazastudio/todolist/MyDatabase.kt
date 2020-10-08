package com.jazastudio.todolist

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun daoAccess(): DaoAccess?

    companion object {
        const val DB_NAME = "app_db"
        const val TABLE_NAME_TODO = "todo"
    }
}
