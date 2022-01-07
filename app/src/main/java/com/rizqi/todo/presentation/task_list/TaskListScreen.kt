package com.rizqi.todo.presentation.task_list

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.rizqi.todo.data.database.TaskDatabase
import com.rizqi.todo.domain.model.Subtask
import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.presentation.navigation.Screen
import com.rizqi.todo.presentation.task_list.components.TaskAppBar
import com.rizqi.todo.presentation.task_list.components.TaskListSection
import com.rizqi.todo.ui.theme.BlueGradient2
import com.rizqi.todo.ui.theme.BlueSoft
import com.rizqi.todo.ui.theme.GreyC4
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun TaskListScreen(
    activity: Activity,
    navController: NavController,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val scrollState = rememberLazyListState()
    val pagerState = rememberPagerState(initialPage = 0)
    val state = viewModel.listState.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    activity.window.statusBarColor = GreyC4.hashCode()
    activity.window.navigationBarColor = BlueGradient2.hashCode()
    val dao = TaskDatabase.getInstance(LocalContext.current).taskDao
    val tasks = listOf<Task>(
        Task(1, "Task 1", "Content 1", 1L),
        Task(2, "Task 2", "Content 2", 2L),
        Task(3, "Task 3", "Content 3", 3L)
    )
    val subtasks = listOf<Subtask>(
        Subtask(1,"Subtask 1", true, 1),
        Subtask(2,"Subtask 2", false, 1),
        Subtask(3,"Subtask 3", true, 2),
        Subtask(4,"Subtask 4", true, 2),
        Subtask(5,"Subtask 5", false, 1),
    )
    LaunchedEffect(key1 = true){
        scope.launch {
            tasks.forEach { dao.insertTask(it) }
            subtasks.forEach { dao.insertSubtask(it) }
            val taskWithSubtasks = dao.getTaskWithSubtask(3)
            println(taskWithSubtasks)
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                          navController.navigate(route = Screen.AddEditTaskScreen.route)
                          },
                backgroundColor = BlueSoft
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task", tint = Color.White)
            }
        },
        scaffoldState = scaffoldState
    ) {
        Box() {
            TaskListSection(
                scrollState,
                pagerState,
                listState = state,
                viewModel = viewModel,
                scaffoldState = scaffoldState,
                scope = scope,
                navController = navController
            )
            TaskAppBar(
                scrollState = scrollState,
                pagerState = pagerState,
                listState = state,
                viewModel = viewModel
            )
        }
    }

}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Preview()
@Composable
fun TaskListScreenPreview() {
    TaskListScreen(activity = Activity(), NavController(LocalContext.current),)
}