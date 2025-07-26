package com.suhas.todoapplication.ui.ui.alertDialogs

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
fun AddTaskDialog(
    inputText: String,
    showError: Boolean,
    updateInputTask: (String) -> Unit,
    onAdd: () -> Unit,
    onDismiss: () -> Unit

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
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier.padding(8.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                //title
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.add_new),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onSurface,
                    )
                }
                //inputTextFiled
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { onInputTaskChange(it.filter { char -> char != '\n' }) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { onAdd() }
                    ),
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    maxLines = 2,
                    modifier = Modifier.padding(8.dp),
                    label = {
                        Text(stringResource(id = R.string.enter_new_task))
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colors.onBackground,
                        unfocusedBorderColor = MaterialTheme.colors.onBackground,
                        focusedLabelColor = MaterialTheme.colors.onBackground,
                        unfocusedLabelColor = MaterialTheme.colors.onBackground,
                        cursorColor = MaterialTheme.colors.onBackground
                    )
                )

                if (showError && inputText.isBlank()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp)
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
                            backgroundColor = MaterialTheme.colors.background
                        )
                    ) {
                        Text(
                            stringResource(id = R.string.cancel_task),
                            color = MaterialTheme.colors.primary
                        )
                    }
                    //submit
                    Button(
                        enabled = inputText.isNotBlank(),
                        onClick = { onAdd() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1f),
                    ) {
                        Text(
                            stringResource(id = R.string.add_task),
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
        AddTaskDialog(
            inputText = "title",
            showError = false,
            onInputTaskChange = {},
            onAdd = {},
            onDismiss = {},
        )
    }
}