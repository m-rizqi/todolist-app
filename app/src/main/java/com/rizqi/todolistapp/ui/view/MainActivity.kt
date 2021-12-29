package com.rizqi.todolist.ui.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rizqi.todolist.nav.Screen
import com.rizqi.todolist.nav.SetUpNavGraph
import com.rizqi.todolist.repository.DataStoreRepository
import com.rizqi.todolist.viewmodel.DataStoreViewModel
import com.rizqi.todolistapp.ui.theme.ToDoListAppTheme
import com.rizqi.todolistapp.ui.view.AuthActivity
import com.rizqi.todolistapp.ui.view.HomeActivity

class MainActivity : ComponentActivity() {

    private lateinit var dataStoreViewModel : DataStoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.White.hashCode()
        window.navigationBarColor = Color.White.hashCode()
        dataStoreViewModel = ViewModelProvider(this).get(DataStoreViewModel::class.java)
    }

    @ExperimentalComposeUiApi
    override fun onStart() {
        super.onStart()
        setContent {
            ToDoListAppTheme {
                Scaffold(backgroundColor = Color.White) {}
                dataStoreViewModel.isLogin.observe(this){
                    login -> if (login){
                        startActivity(Intent(this, HomeActivity::class.java))
                    }else{
                        startActivity(Intent(this, AuthActivity::class.java))
                }
                }
            }
        }

    }
}