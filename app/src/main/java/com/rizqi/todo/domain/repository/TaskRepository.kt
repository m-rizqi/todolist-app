package com.rizqi.todo.domain.repository

import com.rizqi.todo.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks() : Flow<List<Task>>

    suspend fun getTaskById(id: Long): Task?

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun updateTask(task: Task)
}