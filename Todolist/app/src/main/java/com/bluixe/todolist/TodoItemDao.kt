package com.bluixe.todolist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoItemDao {
    @Insert
    fun insetAll(vararg items: TodoItem)

    @Delete
    fun delete(item: TodoItem)

    @Query("SELECT * FROM todoitem")
    fun getAll(): List<TodoItem>

    @Query("DELETE FROM todoitem")
    fun deleteAll()
}