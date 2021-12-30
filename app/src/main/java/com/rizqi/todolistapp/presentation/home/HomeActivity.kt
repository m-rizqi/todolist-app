package com.rizqi.todolistapp.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rizqi.todolist.nav.Screen
import com.rizqi.todolist.nav.SetUpNavGraph
import com.rizqi.todolistapp.ui.theme.Grey3

class HomeActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Grey3.hashCode()
        window.navigationBarColor = Color.White.hashCode()
        setContent {
            Scaffold(backgroundColor = Color.White) {}
            navHostController = rememberNavController()
            SetUpNavGraph(navHostController = navHostController, activity = this, startDestination = Screen.Home.route)
        }
    }
}