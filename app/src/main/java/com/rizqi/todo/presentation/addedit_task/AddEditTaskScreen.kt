package com.rizqi.todo.presentation.addedit_task

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.rizqi.todo.presentation.addedit_task.components.SubtaskItem
import com.rizqi.todo.presentation.navigation.Screen
import com.rizqi.todo.ui.theme.Grey60
import com.rizqi.todo.ui.theme.Grey7
import com.rizqi.todo.ui.theme.GreyC4
import kotlinx.coroutines.flow.collectLatest
import com.rizqi.todo.ui.theme.Poppins

@ExperimentalComposeUiApi
@Composable
fun AddEditTaskScreen(
    activity: Activity,
    navController: NavController,
    viewModel: AddEditTaskViewModel = hiltViewModel(),
    isNewTask: Boolean
) {
    activity.window.navigationBarColor = Color.White.hashCode()
    activity.window.statusBarColor = GreyC4.hashCode()
    val titleState = viewModel.taskTitle.value
    val dueDateState = viewModel.taskDueDate.value
    val subtaskState = viewModel.subtasks
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val scrollState = rememberLazyListState()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditTaskViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = if (event.message.isBlank()) "Title is required" else event.message
                    )
                }
                is AddEditTaskViewModel.UiEvent.SaveNote -> {
                    navController.popBackStack()
                    navController.navigate(Screen.TasksScreen.route)
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
        LazyColumn(
            state = scrollState
        ){
         item {
             Column (modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),){
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
                 Box(modifier = Modifier.fillMaxWidth()) {
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
                 Spacer(modifier = Modifier.height(16.dp))
                 Text(
                     text = "Subtasks",
                     style = TextStyle(
                         color = Grey60,
                         fontFamily = Poppins,
                         fontWeight = FontWeight.Medium,
                         fontSize = 14.sp
                     )
                 )
                 Column(
                     modifier = Modifier.fillMaxWidth(),
                 ) {
                     subtaskState.value.forEachIndexed() { index, subtask ->
                         SubtaskItem(
                             subtask = subtask,
                             onDelete = {
                                 viewModel.onEvent(AddEditTaskEvent.DeleteSubtask(subtask))
                             },
                         )
                     }
                     Spacer(modifier = Modifier.height(5.dp))
                     OutlinedButton(
                         shape = RoundedCornerShape(8.dp),
                         onClick = {
                             viewModel.onEvent(AddEditTaskEvent.AddSubtask)
                         },
                         border = BorderStroke(width = 1.dp, color = Grey7)
                     ) {
                         Row(
                             verticalAlignment = Alignment.CenterVertically
                         ) {
                             Icon(
                                 imageVector = Icons.Default.Add,
                                 contentDescription = "Add Subtask",
                                 tint = Grey7
                             )
                             Text(
                                 text = "Add Subtask",
                                 style = TextStyle(
                                     color = Grey7,
                                     fontFamily = Poppins,
                                     fontSize = 12.sp,
                                     fontWeight = FontWeight.Normal
                                 )
                             )
                         }
                     }
                     Spacer(modifier = Modifier.height(50.dp))
                 }
             }
         }
        }
    }
}