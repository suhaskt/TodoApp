package com.suhas.todoapplication.ui.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.suhas.todoapplication.ui.ui.checkboxItem.TodoEachItem
import com.suhas.todoapplication.ui.ui.alertDialogs.AddTaskDialog
import com.suhas.todoapplication.ui.ui.theme.TodoAppTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.suhas.todoapplication.viewModel.TodoViewModel
import com.suhas.todoapplication.ui.ui.navBars.TopNavBar
import kotlinx.coroutines.launch

@Composable
fun TodoApp(todoViewModel: TodoViewModel = hiltViewModel()) {
    val todoList by todoViewModel.todoList.collectAsState()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val currentDismissedItem = remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopNavBar(navController) },
        backgroundColor = MaterialTheme.colors.background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                backgroundColor = MaterialTheme.colors.onBackground,
                contentColor = MaterialTheme.colors.surface,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState
                                        .showSnackbar(
                                            message = "Add new task",
                                            duration = SnackbarDuration.Short
                                        )

                                }
                            }
                        )
                    }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add task")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colors.background)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = 8.dp,
                backgroundColor = MaterialTheme.colors.surface
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(id = R.string.thought_of_the_day),
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "\"Success is not final, failure is not fatal: it is the courage to continue that counts.\"",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(todoList) { todoItem ->
                    TodoEachItem(
                        todo = todoItem,
                        onToggleCheck = TODO(),
                        onEdit = todoViewModel::updateTodo,
                        onDelete = todoViewModel::removeTodo,
                        currentDismissedItem = currentDismissedItem,
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }

        if (showDialog) {
            AddTaskDialog(
                inputText = todoViewModel.inputTaskText,
                showError = todoViewModel.showError,
                updateInputTask = { todoViewModel.updateInputTask(it) },
                onAdd = {
                    todoViewModel.validateAndAdd {
                        showDialog = false
                    }
                },
                onDismiss = {
                    todoViewModel.clearTaskInput()
                    showDialog = false
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTodoApp() {
    TodoAppTheme {
        TodoApp()
    }

}