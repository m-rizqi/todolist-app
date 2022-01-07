package com.rizqi.todo.domain.repository

import com.rizqi.todo.domain.model.Subtask
import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.domain.model.TaskWithSubtasks
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks() : Flow<List<Task>>

    suspend fun getTaskWithSubtasks(taskId: Long) : List<TaskWithSubtasks>

    suspend fun getTaskById(id: Long): Task?

    suspend fun insertTask(task: Task) : Long

    suspend fun deleteTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun insertSubtask(subtask: Subtask) : Long

    suspend fun deleteSubtask(subtask: Subtask)

    suspend fun getSubtaskById(subtaskId: Long): Subtask?
}