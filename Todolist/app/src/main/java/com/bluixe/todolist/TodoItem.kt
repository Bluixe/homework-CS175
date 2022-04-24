package com.bluixe.todolist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class TodoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "date")
    val date: String?,
    @ColumnInfo(name = "content")
    val content: String?,
    @ColumnInfo(name = "status")
    val status: Boolean?
)
