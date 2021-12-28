package com.rizqi.todolist.nav

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rizqi.todolist.ui.view.Login

@ExperimentalComposeUiApi
@Composable
fun SetUpNavGraph(
    navHostController: NavHostController,
    activity : ComponentActivity
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login.route,
    ){
        composable(
            route = Screen.Login.route
        ){
            Login(activity)
        }
    }
}