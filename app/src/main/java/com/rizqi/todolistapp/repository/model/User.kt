package com.rizqi.todolistapp.repository.model

import com.rizqi.todolist.repository.model.Task

data class User(
    val id: String,
    val name: String,
    val email: String,
    val photo: String,
    val tasks: List<Task>?
)
