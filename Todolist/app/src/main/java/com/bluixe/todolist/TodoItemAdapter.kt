package com.bluixe.todolist

import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoItemAdapter : RecyclerView.Adapter<TodoItemAdapter.TodoIemViewHolder>() {

    private val contentList = mutableListOf<TodoItem>()

    class TodoIemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val date = view.findViewById<TextView>(R.id.date)
        private val itemContent = view.findViewById<TextView>(R.id.content)
        private val status = view.findViewById<TextView>(R.id.status)
        fun bind(position: Int, content: TodoItem ){
            date.text = content.date
            itemContent.text = content.content
            status.text = content.status.toString()
        }
    }

    fun setContentList(list: List<TodoItem>){
        contentList.clear()
        contentList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoIemViewHolder {
        val v = View.inflate(parent.context, R.layout.todo_item, null)
        return TodoIemViewHolder(v)

    }

    override fun onBindViewHolder(holder: TodoIemViewHolder, position: Int) {
        holder.bind(position, contentList[position])
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

}