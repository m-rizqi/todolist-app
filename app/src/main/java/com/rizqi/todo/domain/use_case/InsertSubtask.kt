package com.rizqi.todo.domain.use_case

import com.rizqi.todo.domain.model.Subtask
import com.rizqi.todo.domain.repository.TaskRepository

class InsertSubtask(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(subtask: Subtask){
        repository.insertSubtask(subtask)
    }
}