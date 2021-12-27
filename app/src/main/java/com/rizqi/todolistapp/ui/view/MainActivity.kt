package com.rizqi.todolist.ui.view

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rizqi.todolist.nav.SetUpNavGraph
import com.rizqi.todolistapp.ui.theme.Grey1
import com.rizqi.todolistapp.ui.theme.Grey3
import com.rizqi.todolistapp.ui.theme.ToDoListAppTheme

class MainActivity : ComponentActivity() {

    lateinit var navHostController : NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor = Grey3.hashCode()
            window.navigationBarColor = Color.White.hashCode()
            ToDoListAppTheme {
                // A surface container using the 'background' color from the theme
                navHostController = rememberNavController()
                SetUpNavGraph(navHostController = navHostController)
            }
        }
    }
}