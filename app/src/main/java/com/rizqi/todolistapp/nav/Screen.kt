package com.rizqi.todolist.nav

sealed class Screen(val route: String){
    object Login: Screen(route = "login")
}
