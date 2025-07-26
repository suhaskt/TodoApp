package com.suhas.todoapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_list")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val task: String,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()

)
