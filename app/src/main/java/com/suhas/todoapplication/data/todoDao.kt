package com.suhas.todoapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_list")
    fun getAllTodos(): Flow<List<Todo>>

    @Insert
    suspend fun insertTask(todo: Todo)

    @Query("DELETE FROM todo_list WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)

    @Update
    suspend fun updateTodo(todo: Todo)
}