package com.rizqi.todo.presentation.task_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizqi.todo.domain.model.Subtask
import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.domain.use_case.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
) : ViewModel(){

    private val _state = mutableStateOf(TaskListState())
    val listState : State<TaskListState> = _state

    private val _todoTasks = mutableStateOf(emptyList<Task>())
    val todoTasks : State<List<Task>> = _todoTasks

    private val _completeTasks = mutableStateOf(emptyList<Task>())
    val completeTasks : State<List<Task>> = _completeTasks

    private var recentlyDeleteNote: Task? = null

    private var getTaskJob: Job? = null

    init {
        getAlltask()
    }

    fun dateFormatter(timestamp: Long): String{
        val date = Date(timestamp)
        val format = SimpleDateFormat("EEEE, d MMMM yyyy  HH:mm")
        return format.format(date)
    }

    fun onEvent(event: TaskEvent){
        when(event){
            is TaskEvent.DeleteTask -> {
                viewModelScope.launch {
                    taskUseCases.deleteTask(event.task)
                    recentlyDeleteNote = event.task
                }
            }
            is TaskEvent.RestoreTask -> {
                viewModelScope.launch {
                    taskUseCases.insertTask(recentlyDeleteNote ?: return@launch)
                    recentlyDeleteNote = null
                }
            }
        }
    }

    suspend fun getSubtask(taskId: Long): List<Subtask> {
        val subtasks = mutableStateOf(emptyList<Subtask>())
        subtasks.value = taskUseCases.getTaskWithSubtask(taskId)[0].subtasks
        return subtasks.value
    }

    suspend fun getPrecentageCompleted(taskId: Long): Float{
        val subtask = getSubtask(taskId)
        var completed = 0
        subtask.forEach{
            if (it.isComplete){
                completed++
            }
        }
        val percent = if(subtask.isEmpty()) 0F else (completed.toFloat() / subtask.size.toFloat())
        return percent
    }

    private fun getAlltask(){
        getTaskJob?.cancel()
        getTaskJob = taskUseCases.getAllTasks()
            .onEach { tasks ->
                tasks.onEach { task ->
                    viewModelScope.launch {
                        val subtasks = taskUseCases.getTaskWithSubtask(task.taskId!!)[0].subtasks
                        var completed = 0
                        subtasks.forEach{
                            if (it.isComplete){
                                completed++
                            }
                        }
                        if (completed == subtasks.size && subtasks.size != 0){
                            _state.value = listState.value.copy(
                                completeTasks = listState.value.completeTasks.plus(task)
                            )
                        }else{
                            _state.value = listState.value.copy(
                                todoTasks = listState.value.todoTasks.plus(task)
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

}