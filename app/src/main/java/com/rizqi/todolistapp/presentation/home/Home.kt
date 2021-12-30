package com.rizqi.todolistapp.presentation.home

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.pager.*
import com.rizqi.todolistapp.R
import com.rizqi.todolistapp.ui.theme.*
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

val AppBarCollapsedHeight = 48.dp
val AppBarExpendedHeight = 185.dp

@ExperimentalPagerApi
@Composable
fun Home(activity: ComponentActivity, navHostController: NavHostController) {
    val scrollState = rememberLazyListState()
    val pagerState = rememberPagerState(initialPage = 0)
    activity.window.statusBarColor = Grey3.hashCode()
    activity.window.navigationBarColor = GreyGradient2.hashCode()
    BackHandler() {
        
    }
    Box() {
        TaskSection(scrollState, pagerState)
        AppBar(scrollState, pagerState)
    }
}

@ExperimentalPagerApi
@Composable
fun AppBar(scrollState: LazyListState, pagerState: PagerState) {
    val contentHeight = AppBarExpendedHeight - AppBarCollapsedHeight
    val maxOffset = with(LocalDensity.current){
        contentHeight.roundToPx()
    } - LocalWindowInsets.current.systemBars.layoutInsets.top
    val offset = min(scrollState.firstVisibleItemScrollOffset, maxOffset)
    val offsetProgress = max(0f, offset * 3f - 2f * maxOffset) / maxOffset

    val userPhoto = rememberImagePainter(
        data = "https://firebasestorage.googleapis.com/v0/b/todolistapp-44316.appspot.com/o/default_user.png?alt=media&token=e98ea01f-c86e-402c-a3cb-09a07c3f304e",
        builder = {
            transformations(
                CircleCropTransformation()
            )
        }
    )

    TopAppBar(
        contentPadding = PaddingValues(),
        backgroundColor = Color.White,
        modifier = Modifier
            .height(
                AppBarExpendedHeight
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
                        Image(
                            painter = userPhoto,
                            contentDescription = "User Photo Profile",
                            modifier = Modifier
                                .size(45.dp)
                                .align(Alignment.TopStart),
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
                                onClick = { /*TODO*/ },
                                modifier = Modifier.size(28.dp),
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ellipsis_solid),
                                    contentDescription = "More",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "Tasks",
                        style = TextStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 32.sp,
                            color = Color.Black
                        )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppBarCollapsedHeight),
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
                            color = Grey3
                        )
                    },
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(pagerState,tabPositions),
                            height = 2.dp,
                            color = when(pagerState.currentPage){
                                0 -> OrangeToDo
                                1 -> BlueSoft
                                else -> {Grey3}
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
                                    color = if (index == pagerState.currentPage) Color.Black else Grey2
                                )
                            )
                        }
                        }
                    }
                }
            }
        }
    }

@ExperimentalPagerApi
@Composable
fun TaskSection(scrollState: LazyListState, pagerState: PagerState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GreyGradient1,
                        GreyGradient2
                    )
                )
            )
    ){
        LazyColumn(
            modifier = Modifier.align(Alignment.TopCenter),
            contentPadding = PaddingValues(top = AppBarExpendedHeight),
            state = scrollState
        ) {
            item {
                HorizontalPager(state = pagerState, count = 2) {page->
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        for (i in 0..24) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 5.dp)
                                    .height(75.dp),
                                elevation = 5.dp,
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_google_logo),
                                        contentDescription = "Profile Image",
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(CircleShape)
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Column {
                                        Text(
                                            text = "tabname",
                                            color = Color.Black,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "+91-1234567890",
                                            color = Color.Black,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(20.dp)
        ){
            Button(
                onClick = {},
                modifier = Modifier
                    .size(56.dp)
                    .align(Alignment.BottomEnd),
                shape = CircleShape,
                elevation = ButtonDefaults.elevation(6.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = BlueSoft),
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "Add Button",
                    tint = Color.White,
                )
            }
        }
    }
}

@ExperimentalPagerApi
@Preview(
    name = "Home Preview",
    showBackground = true
)
@Composable
fun HomePreview() {
    Home(ComponentActivity(), NavHostController(ComponentActivity()))
}