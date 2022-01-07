package com.rizqi.todo.presentation.addedit_task

import androidx.compose.ui.focus.FocusState

sealed class AddEditTaskEvent{
    data class EnteredTitle(val value: String): AddEditTaskEvent()
    data class EnteredContent(val value: String): AddEditTaskEvent()
    data class EnteredSubtaskName(val value: String) : AddEditTaskEvent()
    object SaveTask: AddEditTaskEvent()
    object AddSubtask: AddEditTaskEvent()
    object DeleteSubtask: AddEditTaskEvent()
}
