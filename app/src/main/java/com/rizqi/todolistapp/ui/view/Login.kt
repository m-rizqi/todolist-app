package com.rizqi.todolist.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rizqi.todolistapp.ui.theme.*

@Composable
fun Login() {
    ToDoListAppTheme() {
        var emailText by remember {
            mutableStateOf("")
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
                shape = RoundedCornerShape(10.dp)
            )
        }
    }
}

@Preview(
    name = "Login Preview",
    showBackground = true,
)
@Composable
fun LoginPreview() {
    Login()
}