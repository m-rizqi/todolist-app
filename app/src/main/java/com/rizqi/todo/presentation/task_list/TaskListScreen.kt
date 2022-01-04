package com.rizqi.todo.presentation.task_list

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.rizqi.todo.presentation.task_list.components.TaskAppBar
import com.rizqi.todo.presentation.task_list.components.TaskListSection
import com.rizqi.todo.ui.theme.BlueGradient2
import com.rizqi.todo.ui.theme.GreyC4

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun TaskListScreen(activity: Activity) {
    val scrollState = rememberLazyListState()
    val pagerState = rememberPagerState(initialPage = 0)
    activity.window.statusBarColor = GreyC4.hashCode()
    activity.window.navigationBarColor = BlueGradient2.hashCode()
    Box() {
        TaskListSection(scrollState, pagerState)
        TaskAppBar(scrollState = scrollState, pagerState = pagerState)
    }
}