package com.jazastudio.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item_layout.view.*
import splitties.views.inflate

class TodoListAdapter(
    private val listAllTodos: MutableList<Todo>,
    private val listenerAdapter: (Todo) -> Unit
) : RecyclerView.Adapter<TodoListAdapter.TodoListAdapterViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_layout, parent, false)
        return TodoListAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListAdapterViewHolder, position: Int) {
        holder.bindItem(listAllTodos!![position]!!, listenerAdapter)
    }

    override fun getItemCount(): Int = listAllTodos.size

    class TodoListAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(listAllTodos: Todo, listenerAdapter: (Todo) -> Unit) {
            val id_todo = listAllTodos.todo_id
            val name_todo = listAllTodos.name
            val description_todo = listAllTodos.description
            itemView.txtName.setText(name_todo)
            itemView.txtDesc.setText(description_todo)
            itemView.setOnClickListener {
                listenerAdapter(listAllTodos)
            }
        }
    }
}