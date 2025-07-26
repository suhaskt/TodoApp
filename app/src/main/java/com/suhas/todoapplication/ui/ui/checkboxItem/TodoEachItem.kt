package com.suhas.todoapplication.ui.ui.checkboxItem

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.suhas.todoapplication.data.Todo
import com.suhas.todoapplication.helperFunctions.formatTimestamp
import com.suhas.todoapplication.ui.ui.theme.TodoAppTheme
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt


@Composable
fun TodoEachItem(
    todo: Todo,
    onToggleCheck: (Todo) -> Unit,
    onEdit: (Todo) -> Unit,
    onDelete: (Todo) -> Unit,
    currentDismissedItem: MutableState<String?>
) {
    val swipeOffset = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val maxOffset = with(LocalDensity.current) { 80.dp.toPx() }
    val swipeThreshold = maxOffset * 0.6f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(Color.Transparent)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        coroutineScope.launch {
                            val target = when {
                                swipeOffset.value > swipeThreshold -> maxOffset
                                swipeOffset.value < -swipeThreshold -> -maxOffset
                                else -> 0f
                            }
                            swipeOffset.animateTo(target)
                        }
                    },
                    onDrag = { change, dragAmount ->
                        if (abs(dragAmount.x) > abs(dragAmount.y)) {
                            change.consume()
                            coroutineScope.launch {
                                val newOffset = (swipeOffset.value + dragAmount.x)
                                    .coerceIn(-maxOffset, maxOffset)
                                swipeOffset.snapTo(newOffset)
                            }
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
                    .background(Color.Red)
                    .padding(end = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = { onDelete(todo) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.White)
                }
            }
        } else if (swipeOffset.value > 0f) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color(0xFF3F51B5)),
                contentAlignment = Alignment.CenterStart
            ) {
                IconButton(onClick = { onEdit(todo) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.White)
                }
            }
        }

        // Foreground card
        Card(
            modifier = Modifier
                .offset { IntOffset(swipeOffset.value.roundToInt(), 0) }
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 12.dp),
            backgroundColor = Color(0xFFF5F5F5),
            elevation = 2.dp
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
                        style = MaterialTheme.typography.bodyLarge.copy(
                            textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
                Text(
                    text = formatTimestamp(todo.createdAt),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 4.dp, end = 2.dp)
                )
            }
        }
    }
}


/*@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun TodoEachItem(todo: Todo, onRemoveTodo: (Todo) -> Unit, onUpdateTodo: (Todo) -> Unit, currentDismissedItem: MutableState<String?>) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            it == DismissValue.DismissedToStart
        }
    )

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val anchors = mapOf(
        0f to AnchoredDraggableValue.Default,
        -0.75f to AnchoredDraggableValue.Dismissed
    )
    val state = rememberAnchoredDraggableState(AnchoredDraggableValue.Default)

    val offset by animateFloatAsState(
        targetValue =
        if (state.targetValue == AnchoredDraggableValue.Default) 0f
        else -state.size.toFloat() * 0.75f,
        animationSpec = tween(500)
    )
    val scale by animateFloatAsState(
        targetValue =
        if (state.targetValue == AnchoredDraggableValue.Default) 0.5f
        else 1f,
        animationSpec = tween(500)
    )
    val backgroundColor by animateColorAsState(
        targetValue = if (state.targetValue == AnchoredDraggableValue.Default) MaterialTheme.colors.onBackground else MaterialTheme.colors.error,
        animationSpec = tween(500)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colors.surface)
            .anchoredDraggable(
                state = state,
                anchors = anchors,
                orientation = Orientation.Horizontal,
                ),
    )

}

/*@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoEachItem(todo: Todo, onRemoveTodo: (Todo) -> Unit, onUpdateTodo: (Todo) -> Unit) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            it != DismissValue.DismissedToEnd
        }
    )

    SwipeToDismiss(
        state = dismissState,
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
            val color by animateColorAsState(
                if (dismissState.targetValue == DismissValue.Default) {
                    MaterialTheme.colors.onBackground
                } else {
                    MaterialTheme.colors.error
                }, label = "SwipeAnimationColor"
            )

            val scale by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default)
                    0.75f
                else
                    1f, label = "SwipeAnimationScale"
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {

                IconButton(
                    onClick = { onRemoveTodo(todo) },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "default error icon",
                        tint = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.scale(scale)
                    )
                }
            }
        },
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = { FractionalThreshold(0.75f) },
        dismissContent = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = todo.isCompleted,
                    onCheckedChange = { isChecked ->
                        onUpdateTodo(todo.copy(isCompleted = isChecked))
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = todo.task,
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.body1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f)
                )
            }
            Divider(thickness = 5.dp, modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.onBackground)
        }
    )
    if (dismissState.currentValue == DismissValue.DismissedToStart) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.error)
                .padding(horizontal = 20.dp)
        ) {
            IconButton(
                onClick = { todoViewModel.removeTodo(todo) },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colors.onError
                )
            }
        }
    }

}*/

*/
@Preview(showBackground = true)
@Composable
fun PreviewTodoEachItem() {
    TodoAppTheme {
        TodoEachItem(
            todo = Todo(id = 1, task = "Sample Task", isCompleted = false),
            onRemoveTodo = {},
            onUpdateTodo = {},

            )
    }
}