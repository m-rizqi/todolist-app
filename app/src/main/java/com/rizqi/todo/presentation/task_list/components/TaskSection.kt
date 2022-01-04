package com.rizqi.todo.presentation.task_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.ui.theme.BlueGradient2

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun TaskListSection(
    scrollState: LazyListState,
    pagerState: PagerState,
    navHostController: NavHostController,
    appBarExtendedHeight: Dp = 48.dp
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
                        for (i in 0..24) {
                            TaskCard(task = Task(
                                1,
                                if(page == 0) "Page 1" else "Page 2",
                                "Content",
                                System.currentTimeMillis()
                            ))
                            Spacer(modifier = Modifier.height(10.dp))
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
            NavHostController(LocalContext.current)
        )
    }
}