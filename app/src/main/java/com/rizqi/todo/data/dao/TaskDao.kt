package com.rizqi.todo.data.dao

import androidx.room.*
import com.rizqi.todo.domain.model.Subtask
import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.domain.model.TaskWithSubtasks
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE taskId = :id")
    suspend fun getTaskById(id: Long): Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task) : Long

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubtask(subtask: Subtask) : Long

    @Delete
    suspend fun deleteSubtask(subtask: Subtask)

    @Query("SELECT * FROM subtask WHERE subtaskId = :subtaskId")
    suspend fun getSubtaskById(subtaskId : Long) : Subtask?

    @Transaction
    @Query("SELECT * FROM task WHERE taskId = :taskId")
    suspend fun getTaskWithSubtask(taskId: Long): List<TaskWithSubtasks>
}