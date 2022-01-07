package com.rizqi.todo.presentation.task_list

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizqi.todo.domain.model.Subtask
import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.domain.use_case.TaskUseCases
import com.rizqi.todo.domain.util.OrderType
import com.rizqi.todo.domain.util.TaskOrder
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
        getAlltask(TaskOrder.Date(OrderType.Ascending))
    }

    fun dateFormatter(timestamp: Long): String{
        val date = Date(timestamp)
        val format = SimpleDateFormat("EEEE, d MMMM yyyy  HH:mm")
        return format.format(date)
    }

    fun onEvent(event: TaskEvent){
        when(event){
            is TaskEvent.Order ->  {
                if(listState.value.taskOrder::class == event.taskOrder::class &&
                        listState.value.taskOrder.orderType == event.taskOrder.orderType){
                    return
                }
                getAlltask(event.taskOrder)
            }
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
            is TaskEvent.ToggleOrderSection -> {
                _state.value = listState.value.copy(
                    isOrderSectionVisible = !listState.value.isOrderSectionVisible
                )
            }
        }
    }

    fun getSubtask(taskId: Long): List<Subtask> {
        val subtasks = mutableStateOf(emptyList<Subtask>())
        viewModelScope.launch {
            subtasks.value = taskUseCases.getTaskWithSubtask(taskId)[0].subtasks
        }
        return subtasks.value
    }

    fun isTaskComplete(taskId: Long): Float{
        val subtask = getSubtask(taskId)
        var completed = 0
        subtask.forEach{
            if (it.isComplete){
                completed++
            }
        }
        Log.d(TAG,"subtasks:${subtask.size}")
        return if(subtask.isEmpty()) 0F else (completed / subtask.size).toFloat()
    }

    private fun getAlltask(taskOrder: TaskOrder){
        getTaskJob?.cancel()
        getTaskJob = taskUseCases.getAllTasks(taskOrder)
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
                                completeTasks = _state.value.completeTasks.plus(task)
                            )
                        }else{
                            _state.value = listState.value.copy(
                                todoTasks = _state.value.todoTasks.plus(task)
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

}