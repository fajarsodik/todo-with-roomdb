package com.jazastudio.todolist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_add_new_todo.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import splitties.activities.start
import splitties.bundle.BundleSpec
import splitties.bundle.bundle
import splitties.bundle.bundleOrNull
import splitties.bundle.withExtras
import splitties.intents.ActivityIntentSpec
import splitties.intents.activitySpec
import timber.log.Timber


class AddNewTodo : AppCompatActivity() {

    private lateinit var myDatabase: MyDatabase
    private var todoId: Int = 0

    companion object : ActivityIntentSpec<AddNewTodo, ExtrasSpec> by activitySpec(ExtrasSpec) {

    }

    object ExtrasSpec : BundleSpec() {
        var isNew: Boolean by bundle()
        var todo_id: Int? by bundleOrNull()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setContentView(R.layout.activity_add_new_todo)

        myDatabase =
            Room.databaseBuilder(this, MyDatabase::class.java, MyDatabase.DB_NAME)
                .build()

        withExtras(ExtrasSpec) {
            Timber.d("todoid: $todo_id")
            if (!isNew) {
                btnDelete.visibility = View.VISIBLE
                todoId = todo_id!!
                btnDelete.setOnClickListener {
                    val updateTodo = Todo()
                    updateTodo.todo_id = todoId
                    updateTodo.name = inTitle.text.toString()
                    updateTodo.description = inDescription.text.toString()
                    Timber.d("delete check : ${updateTodo.todo_id}")
                    delete(updateTodo)
                }
                btnDone.text = "Update"
                btnDone.setOnClickListener {
                    val updateTodo = Todo()
                    updateTodo.todo_id = todoId
                    updateTodo.name = inTitle.text.toString()
                    updateTodo.description = inDescription.text.toString()
                    Timber.d("${updateTodo.todo_id}")
                    update(updateTodo)
                }
            } else {
                btnDone.setOnClickListener {
                    val todo = Todo()
                    todo.name = inTitle.text.toString()
                    todo.description = inDescription.text.toString()
                    insert(todo)
                }
            }
            loadTodosById(todo_id)
            Timber.d("$todo_id")
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Back"

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    fun loadTodosById(todoId: Int?) = runBlocking {
        var data = myDatabase.daoAccess()?.fetchTodoListById(todoId)
        inTitle.setText(data?.name)
        inDescription.setText(data?.description)
    }

    fun insert(todo: Todo) = runBlocking { // this: CoroutineScope
        launch {
            myDatabase.daoAccess()?.insertTodo(todo)
            Timber.d("insert async")
            delay(100L)
        }
        start<MainActivity>()
    }

    fun update(todo: Todo) = runBlocking {
        launch {
            myDatabase.daoAccess()?.updateTodo(todo)
            Timber.d("update async")
            delay(100L)
        }
        start<MainActivity>()
    }

    fun delete(todo: Todo) = runBlocking {
        coroutineScope {
            myDatabase.daoAccess()?.deleteTodo(todo)
            Timber.d("delete async")
            delay(100L)
        }
        start<MainActivity>()
    }

}