package com.rizqi.todo.presentation.navigation

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rizqi.todo.presentation.addedit_task.AddEditTaskScreen
import com.rizqi.todo.presentation.task_list.TaskListScreen

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    activity: Activity,
    startDestination: String = Screen.TasksScreen.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
        ){
        composable(
            route = Screen.TasksScreen.route
        ){
            TaskListScreen(
                activity = activity,
                navController = navController,
            )
        }
        composable(
            route = Screen.AddEditTaskScreen.route +
                    "?taskId={taskId}&isNewTask={isNewTask}",
            arguments = listOf(
                navArgument(name = "taskId"){
                    type = NavType.LongType
                    defaultValue = -1
                },
                navArgument(name = "isNewTask"){
                    type = NavType.BoolType
                    defaultValue = true
                }
            )
        ){
            val isNewTask = it.arguments?.getBoolean("isNewTask") ?: true
            AddEditTaskScreen(
                activity = activity,
                navController = navController,
                isNewTask = isNewTask
            )
        }
    }
}