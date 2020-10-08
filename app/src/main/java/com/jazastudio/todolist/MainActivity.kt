package com.jazastudio.todolist

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import splitties.intents.start
import timber.log.Timber
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var myDatabase: MyDatabase
    private lateinit var todoArrayList: ArrayList<Todo>
    private lateinit var mAdapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setContentView(R.layout.activity_main)

        myDatabase = Room.databaseBuilder(
            applicationContext,
            MyDatabase::class.java,
            MyDatabase.DB_NAME
        ).fallbackToDestructiveMigration().build()
        fab_add_todo.setOnClickListener {
            start(AddNewTodo) { intentSpec, extrasSpec -> // Magic happens here!
                extrasSpec.isNew = true
            }
        }

    }

    override fun onResume() {
        super.onResume()
        loadAllTodos()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.queryHint = "Search by name..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getTodoByName(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.equals("")) {
                    loadAllTodos()
                }
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    fun loadAllTodos() = runBlocking {
        coroutineScope {
            val allTodos = myDatabase.daoAccess()?.fetchAllTodos()
            Timber.d("data : ${allTodos?.size}")
            var sizeTodoList = allTodos?.count()
            todoArrayList = ArrayList()
            if (sizeTodoList != 0) {
                for (i in 0 until sizeTodoList!!) {
                    todoArrayList.add(allTodos?.get(i)!!)
                    Timber.d("data-$i : ${allTodos.get(i)?.todo_id}")
                    Timber.d("data-$i : ${allTodos.get(i)?.name}")
                }
            }
            Timber.d("data count : ${allTodos?.count()}")
            delay(200L)
        }

        mAdapter = TodoListAdapter(todoArrayList) {
            start(AddNewTodo) { intentSpec, extrasSpec -> // Magic happens here!
                extrasSpec.isNew = false
                extrasSpec.todo_id = it.todo_id
            }
        }
        rv_list_todo.adapter = mAdapter

    }

    private fun getTodoByName(name: String?) = runBlocking {
        coroutineScope {
            val allTodos = myDatabase.daoAccess()?.fetchTodoListByName(name!!)
            Timber.d("data : ${allTodos?.size}")
            var sizeTodoList = allTodos?.count()
            todoArrayList = ArrayList()
            if (sizeTodoList != 0) {
                for (i in 0 until sizeTodoList!!) {
                    todoArrayList.add(allTodos?.get(i)!!)
                    Timber.d("data-$i : ${allTodos.get(i)?.todo_id}")
                    Timber.d("data-$i : ${allTodos.get(i)?.name}")
                }
            }
            Timber.d("data count : ${allTodos?.count()}")
            delay(200L)
        }
        mAdapter = TodoListAdapter(todoArrayList) {
            start(AddNewTodo) { intentSpec, extrasSpec -> // Magic happens here!
                extrasSpec.isNew = false
                extrasSpec.todo_id = it.todo_id
            }
        }
        rv_list_todo.adapter = mAdapter
    }
}