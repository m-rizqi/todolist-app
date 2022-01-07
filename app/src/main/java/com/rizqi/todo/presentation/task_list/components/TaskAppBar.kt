package com.rizqi.todo.presentation.task_list.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.rizqi.todo.ui.theme.*
import com.rizqi.todo.presentation.task_list.TaskEvent
import com.rizqi.todo.presentation.task_list.TaskListState
import com.rizqi.todo.presentation.task_list.TaskViewModel
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun TaskAppBar(
    appBarCollapsedHeight: Dp = 48.dp,
    appBarExtendedHeight: Dp = 125.dp,
    scrollState: LazyListState,
    pagerState: PagerState,
    listState: TaskListState,
    viewModel: TaskViewModel
) {
    val contentHeight = (if(listState.isOrderSectionVisible) 225.dp else appBarExtendedHeight) - appBarCollapsedHeight
    val maxOffset = with(LocalDensity.current){
        contentHeight.roundToPx()
    } - LocalWindowInsets.current.systemBars.layoutInsets.top
    val offset = min(scrollState.firstVisibleItemScrollOffset, maxOffset)
    val offsetProgress = max(0f, offset * 3f - 2f * maxOffset) / maxOffset

    TopAppBar(
        contentPadding = PaddingValues(),
        backgroundColor = Color.White,
        modifier = Modifier
            .height(
                if(listState.isOrderSectionVisible) 225.dp else appBarExtendedHeight
            )
            .offset {
                IntOffset(x = 0, y = -offset)
            },
        elevation = if (offset == maxOffset) 4.dp else 0.dp
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(contentHeight)
                    .graphicsLayer {
                        alpha = 1f - offsetProgress
                    }
                    .background(
                        Color.White
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 18.dp, top = 24.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ){
                        Text(
                            text = "Tasks",
                            style = TextStyle(
                                fontFamily = Poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp,
                                color = Color.Black
                            )
                        )
                        Row(
                            modifier = Modifier
                                .wrapContentWidth()
                                .align(Alignment.CenterEnd),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            IconButton(
                                onClick = { /*TODO*/ },
                                modifier = Modifier.size(28.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Search",
                                    tint = Color.Gray
                                )
                            }
                            IconButton(
                                onClick = {
                                          viewModel.onEvent(TaskEvent.ToggleOrderSection)
                                },
                                modifier = Modifier.size(28.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Sort,
                                    contentDescription = "Sort",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                    AnimatedVisibility(
                        visible = listState.isOrderSectionVisible,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        OrderSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            taskOrder = listState.taskOrder,
                            onOrderChange = {
                                viewModel.onEvent(TaskEvent.Order(it))
                            }
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(appBarCollapsedHeight),
                verticalArrangement = Arrangement.Center
            ) {
                val tabItem = listOf(
                    "To-do",
                    "Complete"
                )
                val scope = rememberCoroutineScope()
                TabRow(
                    modifier = Modifier.fillMaxHeight(),
                    selectedTabIndex = pagerState.currentPage,
                    backgroundColor = Color.White,
                    contentColor = Color.White,
                    divider = {
                        TabRowDefaults.Divider(
                            thickness = 1.dp,
                            color = GreyC4
                        )
                    },
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(pagerState,tabPositions),
                            height = 2.dp,
                            color = when(pagerState.currentPage){
                                0 -> OrangeInProgress
                                1 -> BlueSoft
                                else -> {
                                    GreyC4}
                            }
                        )

                    }
                ) {
                    tabItem.forEachIndexed{ index, text ->
                        Tab(
                            selected = index == pagerState.currentPage,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                        ){
                            Text(
                                text = text,
                                style = TextStyle(
                                    fontFamily = Poppins,
                                    fontWeight = if (index == pagerState.currentPage) FontWeight.SemiBold else FontWeight.Normal,
                                    color = if (index == pagerState.currentPage) Color.Black else Grey60
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Preview()
@Composable
fun TaskAppBarPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        TaskAppBar(
            scrollState = rememberLazyListState(),
            pagerState = rememberPagerState(),
            listState = TaskListState(),
            viewModel = hiltViewModel()
        )
    }
}