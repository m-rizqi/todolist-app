package com.rizqi.todo.presentation.addedit_task

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.LineWeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rizqi.todo.presentation.addedit_task.components.DefaultTextField
import kotlinx.coroutines.flow.collectLatest
import com.rizqi.todo.ui.theme.Poppins

@ExperimentalComposeUiApi
@Composable
fun AddEditTaskScreen(
    navController: NavController,
    viewModel: AddEditTaskViewModel = hiltViewModel(),
    isNewTask: Boolean
) {
    val titleState = viewModel.taskTitle.value
    val contentState = viewModel.taskContent.value
    val scaffoldState = rememberScaffoldState()
    val scope =  rememberCoroutineScope()
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event -> 
            when(event){
                is AddEditTaskViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
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
            Spacer(modifier = Modifier.height(16.dp))
            DefaultTextField(
                text = titleState.text,
                hint = "Title",
                leadingIcon = Icons.Filled.LineWeight,
                onValueChange = {
                    viewModel.onEvent(AddEditTaskEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditTaskEvent.ChangeTitleFocus(it))
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(28.dp))
            DefaultTextField(
                text = contentState.text,
                hint = "Content",
                leadingIcon = Icons.Filled.LineWeight,
                onValueChange = {
                    viewModel.onEvent(AddEditTaskEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditTaskEvent.ChangeContentFocus(it))
                },
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
            )
        }
    }
}