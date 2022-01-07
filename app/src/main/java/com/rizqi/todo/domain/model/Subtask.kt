package com.rizqi.todo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Subtask(
    @PrimaryKey(autoGenerate = true)
    val subtaskId: Long? = null,
    val name: String,
    val isComplete: Boolean = false,
    val taskId: Long
)
