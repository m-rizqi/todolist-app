package com.rizqi.todolistapp.ui.view

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.rizqi.todolistapp.ui.theme.ToDoListAppTheme

@Composable
fun Home(activity: ComponentActivity) {
    ToDoListAppTheme() {
        BackHandler() {

        }
    }
}