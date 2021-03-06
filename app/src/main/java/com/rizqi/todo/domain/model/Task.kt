package com.rizqi.todo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskId: Long? = null,
    val title: String,
    val content: String,
    val timestamp: Long
){

}
