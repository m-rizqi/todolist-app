package com.rizqi.todo.domain.use_case

import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.domain.repository.TaskRepository

class GetTask(
    private val repository: TaskRepository
){
    suspend operator fun invoke(id: Long): Task? {
        return repository.getTaskById(id)
    }
}