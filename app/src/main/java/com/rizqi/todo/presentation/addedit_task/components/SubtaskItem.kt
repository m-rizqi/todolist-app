package com.rizqi.todo.presentation.addedit_task.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.rizqi.todo.data.database.TaskDatabase
import com.rizqi.todo.domain.model.Subtask
import com.rizqi.todo.presentation.addedit_task.AddEditTaskViewModel
import com.rizqi.todo.presentation.navigation.Screen
import com.rizqi.todo.ui.theme.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun SubtaskItem(
    subtask: Subtask,
    onDelete: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val name = remember{
        mutableStateOf(subtask.name)
    }
    val isComplete = remember {
        mutableStateOf(subtask.isComplete)
    }
    val dao = TaskDatabase.getInstance(LocalContext.current).taskDao
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
                value = name.value,
                onValueChange = {
                    name.value = it
                    scope.launch {
                        dao.insertSubtask(subtask.copy(isComplete = isComplete.value, name = it))
                    }
                },
                leadingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Subtask",
                            tint = if(isComplete.value) GreyC4 else Grey7
                        )
                        Checkbox(
                            checked = isComplete.value,
                            onCheckedChange = {
                                isComplete.value = it
                                scope.launch {
                                    dao.insertSubtask(subtask.copy(isComplete = it, name = name.value))
                                }
                            },
                            colors = CheckboxDefaults.colors(checkedColor = BlueSoft, uncheckedColor = GreyC4, checkmarkColor = Color.White),
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = onDelete) {
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
                    textColor = if(isComplete.value) GreyC4 else Color.Black,
                    focusedIndicatorColor = Grey7,
                    unfocusedIndicatorColor = GreyC4,
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
@Preview
@Composable
fun SubtaskItemPreview() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        SubtaskItem(
            subtask = Subtask(1,"Subtask 1", false, 1),
            onDelete = {},
        )
    }
}