package com.rizqi.todolist.ui.view

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rizqi.todolist.nav.SetUpNavGraph
import com.rizqi.todolistapp.ui.theme.ToDoListAppTheme

class MainActivity : ComponentActivity() {

    lateinit var navHostController : NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor = Color.White.hashCode()
            window.navigationBarColor = Color.White.hashCode()
            ToDoListAppTheme {
                Scaffold(backgroundColor = Color.White) {}
                navHostController = rememberNavController()
                SetUpNavGraph(navHostController = navHostController, this)
            }
        }
    }
}