package com.rizqi.todo.domain.use_case

import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTasks(
    private val repository: TaskRepository
) {
    operator fun invoke(
    ) : Flow<List<Task>>{
        return repository.getAllTasks().map { task ->
            task.sortedBy { it.timestamp}
        }
    }
}