package com.rizqi.todolist.ui.view

import android.content.Context
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rizqi.todolist.nav.SetUpNavGraph
import com.rizqi.todolist.repository.DataStoreRepository
import com.rizqi.todolist.viewmodel.DataStoreViewModel
import com.rizqi.todolistapp.ui.theme.ToDoListAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var navHostController : NavHostController
    private lateinit var auth: FirebaseAuth
    private lateinit var dataStoreViewModel : DataStoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        dataStoreViewModel = ViewModelProvider(this).get(DataStoreViewModel::class.java)
    }

    @ExperimentalComposeUiApi
    override fun onStart() {
        super.onStart()
        var isLogin = false
        setContent {
            window.statusBarColor = Color.White.hashCode()
            window.navigationBarColor = Color.White.hashCode()
            ToDoListAppTheme {
                Scaffold(backgroundColor = Color.White) {}
                navHostController = rememberNavController()
                dataStoreViewModel.isLogin.observe(this, {
                        login -> isLogin = login
                })
                if(isLogin){

                }else{
                    SetUpNavGraph(navHostController = navHostController, this) // To Login Screen
                }
            }
        }

    }
}