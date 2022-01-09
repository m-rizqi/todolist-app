package com.rizqi.todo.presentation.task_list

import com.rizqi.todo.domain.model.Task

data class TaskListState(
    val todoTasks : List<Task> = emptyList(),
    val completeTasks : List<Task> = emptyList(),
)
