package com.rizqi.todo.domain.use_case

import com.rizqi.todo.domain.model.Subtask
import com.rizqi.todo.domain.model.TaskWithSubtasks
import com.rizqi.todo.domain.repository.TaskRepository

class GetTaskWithSubtask(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(
        taskId: Long
    ) : List<TaskWithSubtasks>{
        return repository.getTaskWithSubtasks(taskId)
    }
}