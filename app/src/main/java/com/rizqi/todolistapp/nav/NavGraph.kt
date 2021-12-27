package com.rizqi.todolist.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rizqi.todolist.ui.view.Login

@Composable
fun SetUpNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login.route,
    ){
        composable(
            route = Screen.Login.route
        ){
            Login()
        }
    }
}