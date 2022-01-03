package com.rizqi.todo.domain.use_case

data class TaskUseCases(
    val getAllTask: GetTask,
    val deleteTask: DeleteTask,
    val insertTask: InsertTask
)
