package com.rizqi.todo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rizqi.todo.data.dao.TaskDao
import com.rizqi.todo.domain.model.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase(){
    abstract val taskDao: TaskDao

    companion object{
        const val DATABASE_NAME = "tasks"
    }
}