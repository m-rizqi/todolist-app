package com.rizqi.todo.presentation.addedit_task.addedit_subtask

import com.rizqi.todo.domain.model.Subtask

sealed class AddEditSubtaskEvent {
    data class EnteredName(val value: String) : AddEditSubtaskEvent()
    data class ToggleCheckbox(val value: Boolean) : AddEditSubtaskEvent()
    data class DeleteSubtask(val value: Subtask) : AddEditSubtaskEvent()
}