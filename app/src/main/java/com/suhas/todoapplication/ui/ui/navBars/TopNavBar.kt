package com.suhas.todoapplication.ui.ui.navBars

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.suhas.todoapplication.R
import com.suhas.todoapplication.ui.ui.theme.TodoAppTheme

@Composable
fun TopNavBar(navController: NavController) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val showBackIcon = currentBackStackEntry?.destination?.route != "home"
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
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
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menu",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTopNavBar() {
    TodoAppTheme {
        TopNavBar(
            navController = TODO()
        )
    }
}