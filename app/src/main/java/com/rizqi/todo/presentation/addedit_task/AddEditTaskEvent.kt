package com.rizqi.todo.presentation.addedit_task

import com.rizqi.todo.domain.model.Subtask

sealed class AddEditTaskEvent{
    data class EnteredTitle(val value: String): AddEditTaskEvent()
    data class EnteredContent(val value: String): AddEditTaskEvent()
    object SaveTask: AddEditTaskEvent()
    object AddSubtask: AddEditTaskEvent()
    data class SaveSubtask(val name: String, val subtask: Subtask): AddEditTaskEvent()
    data  class DeleteSubtask(val subtask: Subtask) : AddEditTaskEvent()
}
