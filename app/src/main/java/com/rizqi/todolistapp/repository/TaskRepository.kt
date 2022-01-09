package com.rizqi.todolistapp.repository

import androidx.lifecycle.LiveData
import com.rizqi.todolistapp.dao.TaskDao
import com.rizqi.todolistapp.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    val getAllTasks: Flow<List<TaskEntity>> = taskDao.getAllTasks()

    suspend fun getTaskById(id: Long):TaskEntity? {
        return taskDao.getTaskById(id)
    }

    suspend fun insertTask(item: List<TaskEntity>){
        taskDao.insertTask(item)
    }

    suspend fun updateTask(item: TaskEntity){
        taskDao.updateTask(item)
    }

    suspend fun deleteTask(id: Long){
        taskDao.deleteTask(id)
    }

    suspend fun deleteAllTask(){
        taskDao.deleteAllTasks()
    }
}