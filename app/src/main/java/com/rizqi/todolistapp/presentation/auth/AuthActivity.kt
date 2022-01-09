package com.rizqi.todolistapp.presentation.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rizqi.todolist.nav.SetUpNavGraph
import com.rizqi.todolistapp.ui.theme.GreyGradient1
import com.rizqi.todolistapp.ui.theme.GreyGradient2

class AuthActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    @ExperimentalPagerApi
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor = GreyGradient1.hashCode()
            window.navigationBarColor = GreyGradient2.hashCode()
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
                    )
                ) {}
            navHostController = rememberNavController()
            SetUpNavGraph(navHostController = navHostController, activity = this)
        }
    }
}