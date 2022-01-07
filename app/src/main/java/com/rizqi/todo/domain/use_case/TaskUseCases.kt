package com.rizqi.todo.domain.use_case

data class TaskUseCases(
    val getAllTasks: GetTasks,
    val getTask: GetTask,
    val deleteTask: DeleteTask,
    val insertTask: InsertTask,
    val getTaskWithSubtask: GetTaskWithSubtask,
    val insertSubtask: InsertSubtask,
    val deleteSubtask: DeleteSubtask
)
