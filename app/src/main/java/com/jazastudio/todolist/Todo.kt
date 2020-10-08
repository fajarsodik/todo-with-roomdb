package com.jazastudio.todolist

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = MyDatabase.TABLE_NAME_TODO)
class Todo : Serializable {
    @PrimaryKey(autoGenerate = true)
    var todo_id: Int = 0

    var name: String = ""

    lateinit var description: String

}