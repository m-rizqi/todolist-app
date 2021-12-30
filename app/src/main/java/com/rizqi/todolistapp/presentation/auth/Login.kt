package com.rizqi.todolistapp.presentation.auth

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import com.google.firebase.ktx.Firebase
import com.rizqi.todolist.nav.Screen
import com.rizqi.todolistapp.AppDataStoreViewModel
import com.rizqi.todolistapp.R
import com.rizqi.todolistapp.domain.model.User
import com.rizqi.todolistapp.ui.theme.*
import java.lang.Exception

@ExperimentalComposeUiApi
@Composable
fun Login(activity : ComponentActivity, navHostController: NavHostController) {
    val firebase = Firebase
    ToDoListAppTheme() {
        var emailText by rememberSaveable {
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
        var signinButtonLoading by remember { mutableStateOf(false)}
        BackHandler() {

        }
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
                text = "Let's do our task again",
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
                onClick = {
                    navHostController.navigate(Screen.Home.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = BlueSoft)
            ) {
                if(signinButtonLoading){
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(38.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                }else{
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
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(1.dp)
                        .width(100.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Grey2
                                )
                            )
                        ),
                ){}
                Text(
                    text = "Or continue with",
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    style = TextStyle(
                        color = Grey2,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp
                    ),
                )
                Box(
                    modifier = Modifier
                        .height(1.dp)
                        .width(100.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Grey2,
                                    Color.Transparent,
                                )
                            )
                        ),
                ){}
            }
            Spacer(modifier = Modifier.height(32.dp))
            IconButton(
                onClick = {}
            ) {
                Icon(
                    modifier = Modifier.size(55.dp),
                    painter = painterResource(id = R.drawable.ic_google_logo),
                    contentDescription = "Google Button",
                    tint = Color.Unspecified,
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row() {
                Text(
                    text = "Does'nt have an account yet?",
                    style = TextStyle(
                        color = Grey2,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    ),
                )
                Text(
                    modifier = Modifier.clickable {
                        navHostController.navigate(Screen.Register.route)
                    },
                    text = " Register now",
                    style = TextStyle(
                        color = BlueSoft,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    ),
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
    Login(ComponentActivity(), NavHostController(ComponentActivity()))
}