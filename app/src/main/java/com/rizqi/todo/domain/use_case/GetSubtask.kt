package com.rizqi.todo.domain.use_case

import com.rizqi.todo.domain.model.Subtask
import com.rizqi.todo.domain.repository.TaskRepository

class GetSubtask (
    private val repository: TaskRepository
){
    suspend operator fun invoke(taskId: Long): Subtask? {
        return repository.getSubtaskById(taskId)
    }
}