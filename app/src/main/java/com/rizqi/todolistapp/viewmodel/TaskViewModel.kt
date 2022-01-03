package com.rizqi.todolistapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.rizqi.todolistapp.dao.TaskDao
import com.rizqi.todolistapp.database.TaskDatabase
import com.rizqi.todolistapp.entity.TaskEntity
import com.rizqi.todolistapp.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: TaskDao){
    val getAllData = taskDao.getAllTasks()

    init {
        val taskDao = TaskDatabase.getInstance(application).taskDao()
        repository = TaskRepository(taskDao)
        getAllData = repository.getAllTasks
    }

    fun insertTask(item: List<TaskEntity>){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(item)
        }
    }

    fun updateTask(item: TaskEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(item)
        }
    }

    fun deleteTask(item: TaskEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(item)
        }
    }

    fun deleteAllTask(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTask()
        }
    }
}

class TaskViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if(modelClass.isAssignableFrom(TaskViewModel::class.java)){
            return TaskViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}