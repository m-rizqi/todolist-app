package com.rizqi.todolistapp.presentation.auth

import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rizqi.todolist.nav.Screen
import com.rizqi.todolistapp.ui.theme.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rizqi.todolistapp.utils.LoadingState

@ExperimentalComposeUiApi
@Composable
fun Register(
    activity : ComponentActivity,
    navHostController: NavHostController,
    viewModel: AuthViewModel = viewModel()
) {
    activity.window.statusBarColor = GreyGradient1.hashCode()
    activity.window.navigationBarColor = GreyGradient2.hashCode()
    ToDoListAppTheme {
        var nameText by rememberSaveable {
            mutableStateOf("")
        }
        var emailText by rememberSaveable {
            mutableStateOf("")
        }
        var passwordText by rememberSaveable {
            mutableStateOf("")
        }
        var confirmPasswordText by rememberSaveable {
            mutableStateOf("")
        }
        val focusRequester = remember {
            FocusRequester()
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        var registerButtonLoading by remember { mutableStateOf(false)}
        val state by viewModel.loadingState.collectAsState()
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            GreyGradient1,
                            GreyGradient2
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "Glad to see you",
                style = TextStyle(
                    color = Color.Black,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
            )
            Text(
                text = "Can I get to know you?",
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
                value = nameText,
                onValueChange = {nameText = it},
                singleLine = true,
                label = {
                    Text(
                        text = "Name",
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
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
                visualTransformation = PasswordVisualTransformation(),
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
                value = confirmPasswordText,
                onValueChange = {confirmPasswordText = it},
                singleLine = true,
                label = {
                    Text(
                        text = "Confirm Password",
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
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()}
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    registerButtonLoading = true
                    viewModel.createUserWithEmailAndPassword(nameText,emailText, passwordText)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = BlueSoft)
            ) {
                if(registerButtonLoading){
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(38.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                }else{
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = "Register",
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    )
                }

            }
            Spacer(modifier = Modifier.height(32.dp))
            Row() {
                Text(
                    text = "Already have an account?",
                    style = TextStyle(
                        color = Grey2,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    ),
                )
                Text(
                    modifier = Modifier.clickable {
                        navHostController.popBackStack()
                    },
                    text = " Sign in here",
                    style = TextStyle(
                        color = BlueSoft,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    ),
                )
            }
            Spacer(modifier = Modifier.height(72.dp))
        }
        when(state.status){
            LoadingState.Status.SUCCESS -> {
                Log.d("TAG", "status:succes")
                registerButtonLoading = false
                navHostController.navigate(Screen.Home.route)
            }
            LoadingState.Status.IDLE -> {
                Log.d("TAG", "status:idle")
            }
            LoadingState.Status.RUNNING -> {
                Log.d("TAG", "status:running")
            }
            LoadingState.Status.FAILED -> {
                registerButtonLoading = false
                Toast.makeText(LocalContext.current, "Register Failed : ${state.msg?:"Error"}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
@ExperimentalComposeUiApi
@Preview(
    name = "Register Preview",
    showBackground = true,
)
@Composable
fun RegisterPreview() {
    Register(ComponentActivity(), NavHostController(ComponentActivity()))
}