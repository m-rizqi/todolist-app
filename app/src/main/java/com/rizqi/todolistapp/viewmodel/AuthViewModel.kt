package com.rizqi.todolistapp.viewmodel

import androidx.lifecycle.ViewModel
import com.rizqi.todolist.repository.model.Task
import com.rizqi.todolistapp.repository.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
    val user: StateFlow<User?> = _user
    suspend fun signIn(id: String, name:String, email: String, photo: String, tasks: List<Task>? = null){
        delay(1000)
        _user.value = User(id, name, email, photo, tasks)
    }
}