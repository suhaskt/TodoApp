package com.suhas.todoapplication.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepository @Inject constructor(private val todoDao: TodoDao) {

    fun getAllTodos(): Flow<List<Todo>> {
        return todoDao.getAllTodos()
    }

    suspend fun addNewTask(task: Todo) {
        todoDao.insertTask(task)
    }

    suspend fun deleteTask(taskId: Int) {
        todoDao.deleteTask(taskId)
    }

    suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo)
    }
}