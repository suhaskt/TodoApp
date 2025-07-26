package com.suhas.todoapplication.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suhas.todoapplication.data.Todo
import com.suhas.todoapplication.data.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    private val _todoList = MutableStateFlow<List<Todo>>(emptyList())
    val todoList: StateFlow<List<Todo>> = _todoList

    var inputTaskText by mutableStateOf("")
        private set

    var showError by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            _todoList.value = todoRepository.getAllTodos()
        }
    }

    fun validateAndAdd(onSuccess: () -> Unit) {
        if (inputTaskText.isNotBlank()) {
            val newTodo = Todo(task = inputTaskText)
            viewModelScope.launch {
                todoRepository.addNewTask(newTodo)
                _todoList.value = todoRepository.getAllTodos()
            }
            clearTaskInput()
            onSuccess()
        } else {
            showError = true
        }
    }

    fun updateInputTask(newValue: String) {
        inputTaskText = newValue
    }

    fun clearTaskInput() {
        inputTaskText = ""
        showError = false
    }


    fun removeTodo(todo: Todo?) {
        viewModelScope.launch {
            todo?.let {
                todoRepository.deleteTask(it.id)
                _todoList.value = todoRepository.getAllTodos()
            }

        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.updateTodo(todo)
            _todoList.value = todoRepository.getAllTodos()
        }

    }
}