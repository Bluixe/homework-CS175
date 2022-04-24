package com.bluixe.todolist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.Thread.sleep
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    private lateinit var db: TodoItemDatabase
    private lateinit var dao: TodoItemDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = Room.databaseBuilder(
            applicationContext,
            TodoItemDatabase::class.java, "todoitem"
        ).build()
        dao = db.todoItemDao()
        val rv = findViewById<RecyclerView>(R.id.recycler_view)
        rv.layoutManager = LinearLayoutManager(this)
        val handler = Handler(Looper.getMainLooper())
        val adapter = TodoItemAdapter()
//        var data: List<TodoItem>
        Thread {
//            dao.deleteAll()
            val data = dao.getAll()
            adapter.setContentList(data)
            runOnUiThread {
                rv.adapter = adapter
                rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
            }

        }.start()



        val addItem = findViewById<TextView>(R.id.add_item)
        addItem.setOnClickListener {
            val content = "this"
//            val date = SimpleDateFormat("yyyy-MM-dd").parse("2022-04-23")
            val date = "2022-04-23"
            Thread {
                dao.insetAll(TodoItem(0, date, content, true))
//                val data = dao.getAll()
//                adapter.setContentList(data)
                val data = dao.getAll()

                runOnUiThread {
                    adapter.setContentList(data)
                    rv.adapter = adapter
                }
            }.start()
        }

        val addButton = findViewById<FloatingActionButton>(R.id.add_todo)
        addButton.setOnClickListener {
            var ddl_dialog = layoutInflater.inflate(R.layout.ddl_dialog, findViewById(R.id.ddl_dialog))
            var dialog = MaterialAlertDialogBuilder(this)
            dialog.setTitle("新建DDL")
//            .setMessage("Congratulations!")
            .setView(ddl_dialog)
            .setNeutralButton("暂存",null)
            .setNegativeButton("取消", null)
            .setPositiveButton("确定", DialogInterface.OnClickListener {
                    dialogInterface, i -> run {
                        val date = ddl_dialog.findViewById<EditText>(R.id.et).text.toString()
                        val content = ddl_dialog.findViewById<EditText>(R.id.ct).text.toString()
                        Thread {
                            dao.insetAll(TodoItem(0, date, content, true))
                            val data = dao.getAll()

                            runOnUiThread {
                                adapter.setContentList(data)
                                rv.adapter = adapter
                            }
                        }.start()

            } })
            .create()
            dialog.show()
//                .show()
        }

    }

}