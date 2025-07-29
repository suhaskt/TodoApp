package com.suhas.todoapplication.ui.ui.Dialogs

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.suhas.todoapplication.R
import com.suhas.todoapplication.ui.ui.theme.TodoAppTheme

@Composable
fun SubmitTaskDialog(
    title: String,
    inputText: String,
    showError: Boolean,
    onInputTaskChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onDismiss: () -> Unit,
    submitButtonLabel: String
) {

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier.padding(8.dp),
            elevation = 8.dp
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                //title
                Text(
                    text = title,
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                )
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.onSurface,
                )
                //inputTextFiled
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { onInputTaskChange(it.filter { char -> char != '\n' }) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { onSubmit() }
                    ),
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp),
                    label = {
                        Text(stringResource(id = R.string.enter_new_task))
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.primary,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground,
                        focusedLabelColor = MaterialTheme.colors.primary,
                        unfocusedLabelColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.onBackground,
                        errorBorderColor = MaterialTheme.colors.error,
                        errorLabelColor = MaterialTheme.colors.error,
                        errorCursorColor = MaterialTheme.colors.error
                    )
                )

                if (showError && inputText.isBlank()) {
                    Log.d("TodoApp", "show error = $showError")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Icon(
                            imageVector = Icons.Filled.Error,
                            contentDescription = "Error",
                            tint = MaterialTheme.colors.error,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.empty_task_error),
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
                Row {
                    //dismiss
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = MaterialTheme.colors.surface,
                        )

                    ) {
                        Text(
                            stringResource(id = R.string.cancel),
                            color = MaterialTheme.colors.primary
                        )
                    }
                    //submit
                    Button(
                        onClick = { onSubmit() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary,
                            contentColor = MaterialTheme.colors.onPrimary
                        )

                    ) {
                        Text(
                            submitButtonLabel,
                            color = MaterialTheme.colors.onBackground
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAddTaskDialog() {
    TodoAppTheme {
        SubmitTaskDialog(
            title = "title",
            inputText = "new task",
            showError = true,
            onInputTaskChange = {},
            onSubmit = {},
            onDismiss = {},
            submitButtonLabel = "submit",
        )
    }
}