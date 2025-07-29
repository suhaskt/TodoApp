package com.suhas.todoapplication.ui.ui.Dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.suhas.todoapplication.R
import com.suhas.todoapplication.data.Todo

data class TaskDialogConfig(
    val title: String,
    val submitButtonLabel: String,
    val isEditMode: Boolean
)

@Composable
fun createTaskDialogConfig(currentEditingTask: Todo?): TaskDialogConfig {
    val isEditMode = currentEditingTask != null
    return TaskDialogConfig(
        title = if (isEditMode) stringResource(R.string.edit_task)
        else stringResource(R.string.add_new_task),
        submitButtonLabel = if (isEditMode) stringResource(R.string.save)
        else stringResource(R.string.add),
        isEditMode = isEditMode
    )
}

