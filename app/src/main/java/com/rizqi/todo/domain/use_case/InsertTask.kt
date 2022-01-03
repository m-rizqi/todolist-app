package com.rizqi.todo.domain.use_case

import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.domain.repository.TaskRepository
import com.rizqi.todo.domain.util.InvalidTaskException

class InsertTask(
    private val repository: TaskRepository
) {
    @Throws(InvalidTaskException::class)
    suspend operator fun invoke(task: Task){
        if(task.title.isBlank()){
            throw InvalidTaskException.TitleInvalidTaskException
        }
        if(task.timestamp == 0L){
            throw InvalidTaskException.DateInvalidTaskException
        }
        repository.insertTask(task)
    }
}