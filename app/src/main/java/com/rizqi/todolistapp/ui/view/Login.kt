package com.rizqi.todolist.ui.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rizqi.todolistapp.ui.theme.*

@ExperimentalComposeUiApi
@Composable
fun Login(activity : ComponentActivity) {
    activity.window.statusBarColor = GreyGradient1.hashCode()
    activity.window.navigationBarColor = GreyGradient2.hashCode()
    ToDoListAppTheme() {
        var emailText by remember {
            mutableStateOf("")
        }
        var passwordText by rememberSaveable {
            mutableStateOf("")
        }
        var passwordVisibility by remember { mutableStateOf(false) }
        val focusRequester = remember {
            FocusRequester()
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            GreyGradient1,
                            GreyGradient2
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "Welcome Back!",
                style = TextStyle(
                    color = Color.Black,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
            )
            Text(
                text = "Let's do our task",
                style = TextStyle(
                    color = Color.Black,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.height(64.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                value = emailText,
                onValueChange = {emailText = it},
                singleLine = true,
                label = {
                    Text(
                        text = "Email",
                        style = TextStyle(
                            color = Grey1,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = Grey1,
                    textColor = Grey2,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle.Default.copy(fontFamily = Poppins),
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .focusRequester(focusRequester),
                value = passwordText,
                onValueChange = {passwordText = it},
                singleLine = true,
                label = {
                    Text(
                        text = "Password",
                        style = TextStyle(
                            color = Grey1,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    cursorColor = Grey1,
                    textColor = Grey2,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle.Default.copy(fontFamily = Poppins),
                shape = RoundedCornerShape(10.dp),
                trailingIcon = {
                    val icon = if (passwordVisibility)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(imageVector  = icon, "", tint = Grey1)
                    }
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()}
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Reset Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                style = TextStyle(
                    color = Grey2,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                ),
                textAlign = TextAlign.Right
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = BlueSoft)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "Sign In",
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                )
            }

        }
    }
}

@ExperimentalComposeUiApi
@Preview(
    name = "Login Preview",
    showBackground = true,
)
@Composable
fun LoginPreview() {
    Login(ComponentActivity())
}