package com.rizqi.todolist.repository.model

import com.rizqi.todolistapp.data.model.SubTask
import java.util.*

data class Task(val id : String,
                val title : String,
                val description : String,
                val date: Date,
                val subtask : List<SubTask>,
                val status : Status
)
