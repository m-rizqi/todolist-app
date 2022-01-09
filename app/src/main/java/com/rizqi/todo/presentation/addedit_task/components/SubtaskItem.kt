package com.rizqi.todo.presentation.addedit_task.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.rizqi.todo.data.database.TaskDatabase
import com.rizqi.todo.domain.model.Subtask
import com.rizqi.todo.ui.theme.*
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun SubtaskItem(
    subtask: MutableState<Subtask>,
    onDelete: () -> Unit,
    delete: Boolean = false
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val dao = TaskDatabase.getInstance(LocalContext.current).taskDao
    val isDelete = remember {
        mutableStateOf(delete)
    }
    if(!isDelete.value){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            if(!isDelete.value){
                                scope.launch {
                                    dao.insertSubtask(subtask.value)
                                }
                            }
                        },
                    enabled = !subtask.value.isComplete,
                    value = subtask.value.name,
                    onValueChange = {
                        subtask.value = subtask.value.copy(name = it)
                        if(!isDelete.value){
                            scope.launch {
                                dao.insertSubtask(subtask.value)
                            }
                        }
                    },
                    leadingIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = "Subtask",
                                tint = if(subtask.value.isComplete) GreyC4 else Grey7
                            )
                            Checkbox(
                                checked = subtask.value.isComplete,
                                onCheckedChange = {
                                    subtask.value = subtask.value.copy(isComplete = it)
                                    scope.launch {
                                        dao.insertSubtask(subtask.value)
                                    }
                                },
                                colors = CheckboxDefaults.colors(checkedColor = BlueSoft, uncheckedColor = GreyC4, checkmarkColor = Color.White),
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            isDelete.value = true
                            onDelete()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Delete Subtask",
                                tint = RedToDo
                            )
                        }
                    },
                    maxLines = 3,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.Black,
                        textColor = Color.Black,
                        focusedIndicatorColor = Grey7,
                        unfocusedIndicatorColor = GreyC4,
                        disabledIndicatorColor = GreyC4,
                        disabledTextColor = GreyC4
                    ),
                    textStyle = TextStyle.Default.copy(fontFamily = Poppins, fontWeight = FontWeight.Medium),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                                scope.launch {
                                    dao.insertSubtask(subtask.value)
                            }
                        }
                    )
                )
            }
        }
    }

}