package com.rizqi.todo.presentation.task_list

import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.domain.util.OrderType
import com.rizqi.todo.domain.util.TaskOrder

data class TaskListState(
    val todoTasks : List<Task> = emptyList(),
    val completeTasks : List<Task> = emptyList(),
    val taskOrder: TaskOrder = TaskOrder.Date(OrderType.Ascending),
    val isOrderSectionVisible: Boolean = false
)
