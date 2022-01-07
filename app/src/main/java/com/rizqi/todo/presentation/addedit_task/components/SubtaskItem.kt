package com.rizqi.todo.presentation.addedit_task.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Task
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.rizqi.todo.domain.model.Subtask
import com.rizqi.todo.ui.theme.*

@ExperimentalComposeUiApi
@Composable
fun SubtaskItem(
    subtask: Subtask,
    onValueChange: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
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
                value = subtask.name,
                onValueChange = onValueChange,
                leadingIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "Subtask",
                            tint = if(subtask.isComplete) GreyC4 else Grey7
                        )
                        Checkbox(
                            checked = subtask.isComplete,
                            onCheckedChange = onCheckedChange,
                            colors = CheckboxDefaults.colors(checkedColor = BlueSoft, uncheckedColor = GreyC4, checkmarkColor = Color.White),
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
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
            onValueChange = {},
            onCheckedChange = {},
            onDelete = {}
        )
    }
}