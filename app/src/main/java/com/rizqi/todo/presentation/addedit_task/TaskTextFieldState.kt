package com.rizqi.todo.presentation.addedit_task

data class TaskTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)