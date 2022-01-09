package com.rizqi.todo.presentation.task_list

import com.rizqi.todo.domain.model.Task

sealed class TaskEvent {
    data class DeleteTask(val task: Task) : TaskEvent()
    object RestoreTask: TaskEvent()
}