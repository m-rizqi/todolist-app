package com.rizqi.todolist.nav

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rizqi.todolistapp.presentation.auth.Login
import com.rizqi.todolistapp.presentation.auth.Register
import com.rizqi.todolistapp.presentation.home.Home

@ExperimentalComposeUiApi
@Composable
fun SetUpNavGraph(
    navHostController: NavHostController,
    activity : ComponentActivity,
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
    ){
        composable(
            route = Screen.Login.route
        ){
            Login(activity, navHostController)
        }
        composable(
            route = Screen.Register.route
        ){
            Register(activity, navHostController)
        }
        composable(
            route = Screen.Home.route
        ){
            Home(activity, navHostController)
        }
    }
}