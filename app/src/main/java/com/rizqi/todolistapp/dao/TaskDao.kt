package com.rizqi.todolistapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rizqi.todolist.repository.model.Task
import com.rizqi.todolistapp.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: Long): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(item: List<TaskEntity>)

    @Update
    suspend fun updateTask(item: TaskEntity)

    @Query("Delete From tasks WHERE id = :id")
    suspend fun deleteTask(id: Long)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()
}