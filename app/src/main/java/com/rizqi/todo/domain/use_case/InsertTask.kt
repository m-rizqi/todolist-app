package com.rizqi.todo.domain.use_case

import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.domain.repository.TaskRepository

class InsertTask(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task) : Long{
        return repository.insertTask(task)
    }
}