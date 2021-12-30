package com.rizqi.todolist.repository.model

data class Task(val id : String,
                val title : String,
                val description : String,
                val subtask : List<String>,
                val status : Status
)
