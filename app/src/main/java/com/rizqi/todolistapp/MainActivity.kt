package com.rizqi.todolistapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rizqi.todolistapp.presentation.auth.AuthActivity
import com.rizqi.todolistapp.presentation.home.HomeActivity
import com.rizqi.todolistapp.ui.theme.ToDoListAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.White.hashCode()
        window.navigationBarColor = Color.White.hashCode()
    }
    @ExperimentalComposeUiApi
    override fun onStart() {
        super.onStart()
        setContent {
            ToDoListAppTheme {
                Scaffold(backgroundColor = Color.White) {}
                val user = Firebase.auth.currentUser
                if (user != null){
                    startActivity(Intent(this, HomeActivity::class.java))
                }else{
                    startActivity(Intent(this, AuthActivity::class.java))
                }
            }
        }

    }
}