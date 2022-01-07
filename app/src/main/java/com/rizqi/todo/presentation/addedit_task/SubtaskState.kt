package com.rizqi.todo.presentation.addedit_task

import com.rizqi.todo.domain.model.Subtask

data class SubtaskState(
    val subtask : List<Subtask> = emptyList()
)