package com.suhas.todoapplication.ui.ui.checkboxItem

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.suhas.todoapplication.data.Todo
import com.suhas.todoapplication.helperFunctions.formatTimestamp
import com.suhas.todoapplication.ui.ui.theme.TodoAppTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
fun TodoEachItem(
    todo: Todo,
    onToggleCheck: (Todo) -> Unit,
    onEdit: (Todo) -> Unit,
    onDelete: (Todo) -> Unit,
    currentSwipedItem: MutableState<Int?>
) {
    val swipeOffset = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val maxOffset = with(LocalDensity.current) { 80.dp.toPx() }
    val swipeThreshold = maxOffset * 0.6f

    //swipe reset animation
    LaunchedEffect(currentSwipedItem.value) {
        if (currentSwipedItem.value == null || currentSwipedItem.value != todo.id) {
            coroutineScope.launch {
                swipeOffset.animateTo(0f)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(MaterialTheme.colors.background)
            .pointerInput(todo.id) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        currentSwipedItem.value = todo.id
                    },
                    onDragEnd = {
                        coroutineScope.launch {
                            val target = when {
                                swipeOffset.value > swipeThreshold -> { // right swipe
                                    maxOffset
                                }

                                swipeOffset.value < -swipeThreshold -> { // left swipe
                                    -maxOffset
                                }

                                else -> { // partial swipe
                                    currentSwipedItem.value = null
                                    0f
                                }
                            }
                            swipeOffset.animateTo(target)
                        }
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        if (currentSwipedItem.value != todo.id) {
                            return@detectHorizontalDragGestures
                        }
                        change.consume()
                        coroutineScope.launch {
                            val newOffset =
                                (swipeOffset.value + dragAmount).coerceIn(-maxOffset, maxOffset)
                            swipeOffset.snapTo(newOffset)
                        }
                    }
                )
            }
    ) {
        // Revealed background
        if (swipeOffset.value < 0f) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(MaterialTheme.colors.error)
                    .padding(end = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = { onDelete(todo) }) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colors.onError
                    )
                }
            }
        } else if (swipeOffset.value > 0f) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(MaterialTheme.colors.primary)
                    .padding(start = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                IconButton(onClick = { onEdit(todo) }) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }

        // Foreground card
        val dynamicElevation = if (swipeOffset.value != 0f) 6.dp else 2.dp

        Card(
            modifier = Modifier
                .offset { IntOffset(swipeOffset.value.roundToInt(), 0) }
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 12.dp),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = dynamicElevation
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = todo.isCompleted,
                        onCheckedChange = { onToggleCheck(todo.copy(isCompleted = it)) }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = todo.task,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body1.copy(
                            textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                        ),
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colors.onSurface
                    )
                    IconButton(
                        onClick = { /*TODO()*/ }
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "Options",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
                Text(
                    text = formatTimestamp(todo.createdAt),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 4.dp, end = 2.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTodoEachItem() {
    val dummyTodo = Todo(id = 1, task = "Sample Task", isCompleted = false)
    val currentSwipedItem = remember { mutableStateOf<Int?>(null) }
    val itemToDeleteAnimated: Int? = null
    TodoAppTheme {
        TodoEachItem(
            todo = dummyTodo,
            onToggleCheck = { Log.d("Preview", "Toggled: ${it.task}") },
            onEdit = { Log.d("Preview", "Edit requested for: ${it.task}") },
            onDelete = { Log.d("Preview", "Delete requested for: ${it.task}") },
            currentSwipedItem = currentSwipedItem,
        )
    }
}