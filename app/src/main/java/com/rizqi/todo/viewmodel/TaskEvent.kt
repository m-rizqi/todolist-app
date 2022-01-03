package com.rizqi.todo.viewmodel

import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.domain.util.TaskOrder

sealed class TaskEvent {
    data class Order(val taskOrder: TaskOrder) : TaskEvent()
    data class DeleteTask(val task: Task) : TaskEvent()
    object RestoreTask: TaskEvent()
    object ToggleOrderSection: TaskEvent()
}