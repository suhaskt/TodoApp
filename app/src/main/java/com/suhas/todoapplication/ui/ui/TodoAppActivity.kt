package com.suhas.todoapplication.ui.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.suhas.todoapplication.data.Todo
import com.suhas.todoapplication.ui.ui.Dialogs.SubmitTaskDialog
import com.suhas.todoapplication.ui.ui.Dialogs.ViewTaskDialog
import com.suhas.todoapplication.ui.ui.Dialogs.createTaskDialogConfig
import com.suhas.todoapplication.ui.ui.cards.TopCard
import com.suhas.todoapplication.ui.ui.checkboxItem.TodoEachItem
import com.suhas.todoapplication.ui.ui.navBars.BottomNavBar
import com.suhas.todoapplication.ui.ui.navBars.TopNavBar
import com.suhas.todoapplication.ui.ui.placeHolders.EmptyTaskPlaceHolder
import com.suhas.todoapplication.ui.ui.theme.TodoAppTheme
import com.suhas.todoapplication.viewModel.TodoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TodoApp(todoViewModel: TodoViewModel = hiltViewModel()) {
    val todoList by todoViewModel.todoList.collectAsState()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()
    val listScrollState = rememberLazyListState()
    val currentSwipedItem = todoViewModel.currentSwipedItem
    val itemToDeleteAnimated by todoViewModel.itemToDeleteAnimated
    val newItemAddCounter by todoViewModel.newItemAddCounter
    var showDialog by remember { mutableStateOf(false) }
    var todoItemToView by remember { mutableStateOf<Todo?>(null) }

    //scroll-to-top animation
    LaunchedEffect(newItemAddCounter) {
        if (newItemAddCounter > 0) {
            delay(50)
            listScrollState.animateScrollToItem(0)
        }
    }
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
                    .size(60.dp)
                    .offset(y = 8.dp)
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
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = { BottomNavBar(navController) }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colors.background)
        ) {
            TopCard()
            if (todoList.isEmpty()) {
                EmptyTaskPlaceHolder()
            } else {
//                val decay = rememberSplineBasedDecay<Float>()
                val flingBehavior = ScrollableDefaults.flingBehavior()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .animateContentSize(animationSpec = tween(300)),
                    userScrollEnabled = true,
                    flingBehavior = flingBehavior,
                    state = listScrollState,
                    contentPadding = PaddingValues(bottom = 30.dp)
                ) {
                    items(items = todoList, key = { it.id }) { todoItem ->
                        val isBeingDeleted = itemToDeleteAnimated == todoItem.id
                        val itemVisible = remember { mutableStateOf(true) }

                        LaunchedEffect(isBeingDeleted) {
                            if (isBeingDeleted) {
                                itemVisible.value = false
                            }
                        }
                        LaunchedEffect(itemVisible.value) {
                            if (!itemVisible.value) {
                                delay(300)
                                todoViewModel.onAnimationEnd(todoItem.id)
                            }
                        }
                        AnimatedVisibility(
                            visible = !isBeingDeleted,
                            enter = fadeIn(),
                            exit = shrinkHorizontally(animationSpec = tween(durationMillis = 300)) + fadeOut()
                        ) {
                            TodoEachItem(
                                todo = todoItem,
                                onToggleCheck = todoViewModel::updateTodo,
                                onView = { viewedItem ->
                                    todoItemToView = viewedItem
                                },
                                onEdit = {
                                    showDialog = true
                                    todoViewModel.editTask(todoItem)
                                },
                                onDelete = todoViewModel::removeTodo,
                                currentSwipedItem = currentSwipedItem
                            )
                        }

                    }
                }
            }
        }
    }
    if (showDialog) {
        val dialogConfig = createTaskDialogConfig(todoViewModel.currentEditingTask)

        SubmitTaskDialog(
            title = dialogConfig.title,
            inputText = todoViewModel.inputTaskText,
            showError = todoViewModel.showError,
            onInputTaskChange = { todoViewModel.updateInputTask(it) },
            onSubmit = {
                todoViewModel.validateAndAdd {
                    showDialog = false
                    currentSwipedItem.value = null
                }
            },
            onDismiss = {
                todoViewModel.clearTaskInput()
                showDialog = false
                currentSwipedItem.value = null
            },
            submitButtonLabel = dialogConfig.submitButtonLabel
        )
    }
    if (todoItemToView != null) {
        ViewTaskDialog(
            todo = todoItemToView!!,
            onDismiss = {
                todoItemToView = null
            }
        )

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTodoApp() {
    TodoAppTheme {
        TodoApp()
    }

}