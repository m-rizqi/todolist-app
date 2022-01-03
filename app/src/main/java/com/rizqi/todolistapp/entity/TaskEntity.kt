package com.rizqi.todolistapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rizqi.todolist.repository.model.Status

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "date")
    var date: String,

//    @ColumnInfo(name = "subtask")
//    var subtask: List<SubTask>,

    @ColumnInfo(name = "complete")
    var complete: Boolean,
)
