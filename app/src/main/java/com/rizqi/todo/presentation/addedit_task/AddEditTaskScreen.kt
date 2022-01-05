package com.rizqi.todo.presentation.addedit_task

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.LineWeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rizqi.todo.presentation.addedit_task.components.DefaultTextField
import kotlinx.coroutines.flow.collectLatest
import com.rizqi.todo.ui.theme.Poppins
import com.rizqi.todo.presentation.task_list.formatTimeStamp

@ExperimentalComposeUiApi
@Composable
fun AddEditTaskScreen(
    activity: Activity,
    navController: NavController,
    viewModel: AddEditTaskViewModel = hiltViewModel(),
    isNewTask: Boolean
) {
    activity.window.navigationBarColor = Color.White.hashCode()
    val titleState = viewModel.taskTitle.value
    val dueDateState = viewModel.taskDueDate.value
    val contentState = viewModel.taskContent.value
    val scaffoldState = rememberScaffoldState()
    val scope =  rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event -> 
            when(event){
                is AddEditTaskViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = if(event.message.isBlank()) "Title is required" else event.message
                    )
                }
                is AddEditTaskViewModel.UiEvent.SaveNote ->{
                    navController.navigateUp()
                }
            }
        }
    }
    BackHandler() {
       viewModel.onEvent(AddEditTaskEvent.SaveTask)
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = {
                    viewModel.onEvent(AddEditTaskEvent.SaveTask)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ChevronLeft,
                        contentDescription = "Arrow Back",
                        tint = Color.Black
                    )
                }
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "${if (isNewTask) "New" else "Detail"} Task",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
            DefaultTextField(
                text = titleState.text,
                hint = "Title",
                leadingIcon = Icons.Filled.LineWeight,
                onValueChange = {
                    viewModel.onEvent(AddEditTaskEvent.EnteredTitle(it))
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth()){
                DefaultTextField(
                    text = if (dueDateState == 0L) "" else viewModel.dateFormatter(dueDateState),
                    hint = "Date",
                    readOnly = true,
                    leadingIcon = Icons.Filled.CalendarToday,
                    onValueChange = {},
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart)
                )
                Box(modifier = Modifier
                    .matchParentSize()
                    .background(Color.Transparent)
                    .align(Alignment.TopStart)
                    .clickable {
                        viewModel.selectDateTime(context)
                    }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            DefaultTextField(
                text = contentState.text,
                hint = "Content",
                leadingIcon = Icons.Filled.LineWeight,
                onValueChange = {
                    viewModel.onEvent(AddEditTaskEvent.EnteredContent(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
    }
}