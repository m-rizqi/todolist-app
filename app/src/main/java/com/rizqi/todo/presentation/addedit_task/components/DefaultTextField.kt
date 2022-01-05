package com.rizqi.todo.presentation.addedit_task.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LineWeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.rizqi.todo.ui.theme.Grey60
import com.rizqi.todo.ui.theme.Grey7
import com.rizqi.todo.ui.theme.GreyC4
import com.rizqi.todo.ui.theme.Poppins

@ExperimentalComposeUiApi
@Composable
fun DefaultTextField(
    text: String,
    hint: String,
    singleLine: Boolean = false,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Box(
        modifier = modifier,
    ){
        TextField(
            value = text,
            readOnly = readOnly,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = "Title",
                    tint = Grey7
                )
            },
            label = {
                Text(
                    text = hint,
                    style = TextStyle(
                        color = Grey60,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                )
            },
            singleLine = singleLine,
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
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
    }
}

@ExperimentalComposeUiApi
@Preview()
@Composable
fun DefaultTextFieldScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultTextField(text = "", hint = "", leadingIcon = Icons.Default.Home, onValueChange = {})
    }
}