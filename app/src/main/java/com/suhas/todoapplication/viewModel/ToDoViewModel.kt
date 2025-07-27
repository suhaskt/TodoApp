package com.suhas.todoapplication.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suhas.todoapplication.data.Todo
import com.suhas.todoapplication.data.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    val todoList: StateFlow<List<Todo>> = todoRepository.getAllTodos()
        .map { todos ->
            todos.sortedByDescending { it.createdAt }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    var inputTaskText by mutableStateOf("")
        private set

    var showError by mutableStateOf(false)
        private set

    var currentEditingTask by mutableStateOf<Todo?>(null)
        private set

    val newItemAddCounter: MutableState<Int> = mutableStateOf(0)


    val currentSwipedItem: MutableState<Int?> = mutableStateOf(null)
    val itemToDeleteAnimated: MutableState<Int?> = mutableStateOf(null)


    fun validateAndAdd(onSuccess: () -> Unit) {
        if (inputTaskText.isBlank()) {
            showError = true
            return
        }
        if (currentEditingTask != null) { // edit task
            val editedTask = currentEditingTask!!.copy(task = inputTaskText)
            viewModelScope.launch {
                todoRepository.updateTodo(editedTask) // // existing task update
            }
        } else {
            // new task
            val newTodo = Todo(task = inputTaskText)
            viewModelScope.launch {
                todoRepository.addNewTask(newTodo) //  adds a new task
            }
        }
        if (currentEditingTask == null) {
            newItemAddCounter.value++
        }
        clearTaskInput()
        onSuccess()
    }

    fun updateInputTask(newValue: String) {
        inputTaskText = newValue
    }

    fun clearTaskInput() {
        inputTaskText = ""
        currentEditingTask = null
        showError = false
    }

    fun removeTodo(todo: Todo) {
        itemToDeleteAnimated.value = todo.id
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch {
            todoRepository.updateTodo(todo)
        }
    }

    fun editTask(todo: Todo) {
        currentEditingTask = todo
        updateInputTask(todo.task)
    }

    fun onAnimationEnd(todoId: Int) {
        viewModelScope.launch {
            todoRepository.deleteTask(todoId)
            itemToDeleteAnimated.value = null
            currentSwipedItem.value = null
        }
    }

}