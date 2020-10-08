package com.jazastudio.todolist

import androidx.room.*

@Dao
interface DaoAccess {
    @Insert
    suspend fun insertTodo(todo: Todo): Long

    @Query("SELECT * FROM ${MyDatabase.TABLE_NAME_TODO} WHERE name = :name")
    suspend fun fetchTodoListByName(name: String): MutableList<Todo>

    @Query("SELECT * FROM ${MyDatabase.TABLE_NAME_TODO} WHERE todo_id = :todoId")
    suspend fun fetchTodoListById(todoId: Int?): Todo?

    @Query("SELECT * FROM ${MyDatabase.TABLE_NAME_TODO}")
    suspend fun fetchAllTodos(): MutableList<Todo?>?

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

}