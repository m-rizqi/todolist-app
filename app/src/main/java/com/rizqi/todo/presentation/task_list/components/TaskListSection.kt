package com.rizqi.todo.presentation.task_list.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.rizqi.todo.presentation.navigation.Screen
import com.rizqi.todo.ui.theme.BlueGradient2
import com.rizqi.todo.presentation.task_list.TaskEvent
import com.rizqi.todo.presentation.task_list.TaskListState
import com.rizqi.todo.presentation.task_list.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun TaskListSection(
    scrollState: LazyListState,
    pagerState: PagerState,
    appBarExtendedHeight: Dp = 125.dp,
    listState: TaskListState,
    viewModel: TaskViewModel,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        BlueGradient2
                    )
                )
            )
    ){
        LazyColumn(
            modifier = Modifier.align(Alignment.TopCenter),
            contentPadding = PaddingValues(top = appBarExtendedHeight),
            state = scrollState
        ) {
            item {
                HorizontalPager(state = pagerState, count = 2) {page->
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        when(page){
                            0 -> {
                                listState.todoTasks.onEach { task ->
                                    TaskCard(
                                        task = task,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                navController.navigate(
                                                    route = Screen.AddEditTaskScreen.route + "?taskId=${task.taskId}&isNewTask=${false}"
                                                )
                                            },
                                        viewModel = viewModel,
                                        onDeleteClick = {
                                            viewModel.onEvent(TaskEvent.DeleteTask(task))
                                            listState.todoTasks.minus(task)
                                            scope.launch {
                                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                                    message = "Task deleted",
                                                    actionLabel = "undo"
                                                )
                                                if(result == SnackbarResult.ActionPerformed){
                                                    viewModel.onEvent(TaskEvent.RestoreTask)
                                                }
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }
                            1 -> {
                                listState.completeTasks.onEach{task ->
                                    Log.d(TAG,"size:${listState.completeTasks.size}")
                                    TaskCard(
                                        task = task,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                navController.navigate(
                                                    route = Screen.AddEditTaskScreen.route + "?taskId=${task.taskId}&isNewTask=${false}"
                                                )
                                            },
                                        viewModel = viewModel,
                                        onDeleteClick = {
                                            viewModel.onEvent(TaskEvent.DeleteTask(task))
                                            listState.completeTasks.minus(task)
                                            scope.launch {
                                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                                    message = "Task deleted",
                                                    actionLabel = "undo"
                                                )
                                                if(result == SnackbarResult.ActionPerformed){
                                                    viewModel.onEvent(TaskEvent.RestoreTask)
                                                }
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Preview
@Composable
fun TaskListSectionPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TaskListSection(
            rememberLazyListState(),
            rememberPagerState(),
            listState = TaskListState(),
            viewModel = hiltViewModel(),
            scaffoldState = rememberScaffoldState(),
            scope = rememberCoroutineScope(),
            navController = rememberNavController()
        )
    }
}