package com.rizqi.todo.data.repository

import com.rizqi.todo.data.dao.TaskDao
import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl (private val taskDao: TaskDao) : TaskRepository {
    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    override suspend fun getTaskById(id: Long): Task? {
        return taskDao.getTaskById(id)
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }
}