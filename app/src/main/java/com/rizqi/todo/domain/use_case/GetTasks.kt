package com.rizqi.todo.domain.use_case

import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.domain.repository.TaskRepository
import com.rizqi.todo.domain.util.OrderType
import com.rizqi.todo.domain.util.TaskOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTasks(
    private val repository: TaskRepository
) {
    operator fun invoke(
        taskOrder: TaskOrder = TaskOrder.Date(OrderType.Ascending)
    ) : Flow<List<Task>>{
        return repository.getAllTasks().map { task ->
            when(taskOrder.orderType){
                is OrderType.Ascending ->{
                    when(taskOrder){
                        is TaskOrder.Title -> task.sortedBy { it.title.lowercase() }
                        is TaskOrder.Date -> task.sortedBy { it.timestamp}
                    }
                }
                is OrderType.Descending ->{
                    when(taskOrder){
                        is TaskOrder.Title -> task.sortedByDescending { it.title.lowercase() }
                        is TaskOrder.Date -> task.sortedByDescending { it.timestamp}
                    }
                }
            }
        }
    }
}