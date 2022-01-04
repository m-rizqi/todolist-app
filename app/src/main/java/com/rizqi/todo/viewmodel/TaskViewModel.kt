package com.rizqi.todo.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _state = mutableStateOf(TaskState())
    val state : State<TaskState> = _state

    private var recentlyDeleteNote: Task? = null

    private var getTaskJob: Job? = null

    init {
        getAlltask(TaskOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: TaskEvent){
        when(event){
            is TaskEvent.Order ->  {
                if(state.value.taskOrder::class == event.taskOrder::class &&
                        state.value.taskOrder.orderType == event.taskOrder.orderType){
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
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getAlltask(taskOrder: TaskOrder){
        getTaskJob?.cancel()
        getTaskJob = taskUseCases.getAllTask(taskOrder)
            .onEach { task ->
                _state.value = state.value.copy(
                    tasks = task,
                    taskOrder = taskOrder
                )
            }
            .launchIn(viewModelScope)
    }

}

fun formatTimeStamp(timestamp: Long) : String{
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("EEEE, d MMM yyyy HH:mm")
    return formatter.format(date)
}