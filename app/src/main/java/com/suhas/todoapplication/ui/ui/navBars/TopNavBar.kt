package com.suhas.todoapplication.ui.ui.navBars

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.suhas.todoapplication.R
import com.suhas.todoapplication.ui.ui.theme.TodoAppTheme

@Composable
fun TopNavBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val showBackIcon = currentBackStackEntry?.destination?.route != "home"
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name),
                maxLines = 1
            )
        },
        navigationIcon = {
            if (showBackIcon) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
            } else null
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
//            IconButton(onClick = { showDialog = true }) {
//                Icon(
//                    Icons.Default.Add,
//                    tint = Color.White,
//                    modifier = Modifier.size(ButtonDefaults.IconSize),
//                    contentDescription = "Add Task"
//                )
//            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTopNavBar() {
    TodoAppTheme {
        TopNavBar()
    }
}