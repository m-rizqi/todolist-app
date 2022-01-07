package com.rizqi.todo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rizqi.todo.data.dao.TaskDao
import com.rizqi.todo.domain.model.Subtask
import com.rizqi.todo.domain.model.Task

@Database(entities = [Task::class, Subtask::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase(){

    abstract val taskDao: TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null
        fun getInstance(context: Context): TaskDatabase{
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "tasks"
                ).build().also {
                    INSTANCE = it
                }
            }
        }

        const val DATABASE_NAME = "tasks"
    }
}