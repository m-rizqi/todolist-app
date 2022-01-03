package com.rizqi.todolistapp.presentation.task

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavHostController
import com.rizqi.todolistapp.data.model.Subtask
import com.rizqi.todolistapp.ui.theme.*


@ExperimentalComposeUiApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewTask(
    activity: ComponentActivity,
    navHostController: NavHostController
){
    activity.window.navigationBarColor = Grey5.hashCode()
    Box(
        modifier = Modifier
            .background(Grey5)
            .fillMaxSize()
    ) {
        val viewModel: TaskViewModel = viewModel()
        var titleText by rememberSaveable {
            mutableStateOf("")
        }
        val stringTime = viewModel.stringTime.observeAsState()
        var descriptionText by rememberSaveable {
            mutableStateOf("")
        }
        val focusRequester = remember {
            FocusRequester()
        }
        val context = LocalContext.current
        Column(
            modifier = Modifier.
                    padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ChevronLeft,
                        contentDescription = "Arrow Back",
                        tint = Color.Black
                    )
                }
                Text(
                    text = "New Task",
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.DeleteOutline,
                        contentDescription = "Delete",
                        tint = Red
                    )
                }
            }
            Spacer(modifier = Modifier.height(28.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = titleText,
                onValueChange = {
                    titleText = it
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.LineWeight,
                        contentDescription = "Title",
                        tint = Grey1
                    )
                              },
                label = {
                    Text(
                        text = "Title",
                        style = TextStyle(
                            color = Grey2,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Grey5,
                    cursorColor = Grey1,
                    textColor = Color.Black,
                    focusedIndicatorColor = Grey1,
                    unfocusedIndicatorColor = Grey3,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle.Default.copy(fontFamily = Poppins, fontWeight = FontWeight.Medium),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { if (it.isFocused) viewModel.selectDateTime(context) },
                value = stringTime.value?: "",
                onValueChange = {
                    viewModel.selectDateTime(context)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.CalendarToday,
                        contentDescription = "Calendar",
                        tint = Grey1
                    )
                },
                label = {
                    Text(
                        text = "Due Date",
                        style = TextStyle(
                            color = Grey2,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Grey5,
                    cursorColor = Grey1,
                    textColor = Color.Black,
                    focusedIndicatorColor = Grey1,
                    unfocusedIndicatorColor = Grey3,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle.Default.copy(fontFamily = Poppins, fontWeight = FontWeight.Medium),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = descriptionText,
                onValueChange = {
                    descriptionText = it
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ShortText,
                        contentDescription = "Description",
                        tint = Grey1
                    )
                },
                label = {
                    Text(
                        text = "Description",
                        style = TextStyle(
                            color = Grey2,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    )
                },
                maxLines = 3,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Grey5,
                    cursorColor = Grey1,
                    textColor = Color.Black,
                    focusedIndicatorColor = Grey1,
                    unfocusedIndicatorColor = Grey3,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle.Default.copy(fontFamily = Poppins, fontWeight = FontWeight.Medium),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Subtasks",
                style = TextStyle(
                    color = Grey2,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp
                )
            )
            val subtaskList = remember {
                listOf(
                    Subtask("",false)
                )
            }
            LazyColumn(
            ){
                items(subtaskList){ item: Subtask ->
                    SubtaskView(
                        subtask = item,
                        onCheckedChange = {
                           item.complete = it
                        },
                        onTextChange = {
                            item.name = it
                    })
                }
            }
            Spacer(modifier= Modifier.height(12.dp))
            Row(
                modifier = Modifier.clickable(){  },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Arrow Back",
                    tint = Grey1,
                )
                Text(
                    text = "Add subtask",
                    style = TextStyle(
                        color = Grey1,
                        fontFamily = Poppins,
                        fontSize = 12.sp
                    )
                )
            }

        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun SubtaskView(subtask: Subtask, onCheckedChange: (Boolean) -> Unit, onTextChange: (String)->Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var name by remember {
        mutableStateOf(subtask.name)
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = {
                    onTextChange(it)
                },
                leadingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Task,
                            contentDescription = "Subtask",
                            tint = if(subtask.complete) Grey4 else Grey1
                        )
                        Checkbox(
                            checked = subtask.complete,
                            onCheckedChange = {onCheckedChange(it)},
                            colors = CheckboxDefaults.colors(checkedColor = BlueSoft, uncheckedColor = Grey4, checkmarkColor = BlueSoft),
                        )
                    }
                },
                maxLines = 3,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Grey5,
                    cursorColor = Grey1,
                    textColor = Color.Black,
                    focusedIndicatorColor = Grey1,
                    unfocusedIndicatorColor = Grey4,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle.Default.copy(fontFamily = Poppins, fontWeight = FontWeight.Medium),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()}
                )
            )
        }
    }
}

@ExperimentalComposeUiApi
@RequiresApi(Build.VERSION_CODES.O)
@Preview(name = "New Task Preview", showBackground = true)
@Composable
fun NewTaskPreview() {
    NewTask(activity = ComponentActivity(), navHostController = NavHostController(LocalContext.current))
}