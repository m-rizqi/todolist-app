package com.rizqi.todo.domain.use_case

import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.domain.repository.TaskRepository

class DeleteTask (private val repository: TaskRepository){
    suspend operator fun invoke(task: Task){
        repository.deleteTask(task)
    }
}