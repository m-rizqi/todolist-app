package com.rizqi.todo.presentation.addedit_task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizqi.todo.domain.model.Subtask
import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.domain.use_case.TaskUseCases
import com.rizqi.todo.domain.util.InvalidTaskException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val _taskTitle = mutableStateOf(TaskTextFieldState())
    val taskTitle: State<TaskTextFieldState> = _taskTitle

    private val _taskDueDate = mutableStateOf(0L)
    val taskDueDate: State<Long> = _taskDueDate

    private val _subtasks = mutableStateOf(emptyList<Subtask>())
    val subtasks: State<List<Subtask>> = _subtasks

    private val _taskContent = mutableStateOf(TaskTextFieldState())
    val taskContent: State<TaskTextFieldState> = _taskContent

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTaskId: Long? = null

    init {
        savedStateHandle.get<Long>("taskId")?.let { taskId ->
            if(taskId != -1L){
                viewModelScope.launch {
                    taskUseCases.getTask(taskId)?.also { task ->
                        currentTaskId = task.taskId
                        _taskTitle.value = taskTitle.value.copy(
                            text = task.title,
                        )
                        _taskDueDate.value = task.timestamp
                        viewModelScope.launch {
                            _subtasks.value = taskUseCases.getTaskWithSubtask(taskId)[0].subtasks
                        }
                        _taskContent.value = taskContent.value.copy(
                            text = task.content,
                        )
                    }
                }
            }else{
                viewModelScope.launch {
                    currentTaskId = taskUseCases.insertTask(
                        Task(
                            title = taskTitle.value.text,
                            content = taskTitle.value.text,
                            timestamp = taskDueDate.value,
                            taskId = currentTaskId
                        )
                    )
                }
            }
        }
    }

    fun selectDateTime(context: Context){
        var time = ""
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)
        DatePickerDialog(context, { _, year, month, day ->
            TimePickerDialog(context, { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                val monthStr: String
                if ((month + 1).toString().length == 1) {
                    monthStr = "0${month + 1}"
                } else {
                    monthStr = month.toString()
                }
                time = "$day/$monthStr/${year} $hour:$minute"
                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = formatter.parse(time)
                _taskDueDate.value = date.time
            }, startHour, startMinute, true).show()
        }, startYear, startMonth, startDay).show()
    }

    fun dateFormatter(timestamp: Long): String{
        val date = Date(timestamp)
        val format = SimpleDateFormat("EEEE, d MMMM yyyy  HH:mm")
        return format.format(date)
    }

    fun onEvent(event: AddEditTaskEvent){
        when(event){
            is AddEditTaskEvent.EnteredContent -> {
                _taskContent.value = _taskContent.value.copy(
                    text = event.value
                )
            }
            is AddEditTaskEvent.EnteredTitle -> {
                _taskTitle.value = taskTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditTaskEvent.AddSubtask -> {
                val subtask = Subtask(null, "", false, currentTaskId ?: 0)
                _subtasks.value = subtasks.value.plus(subtask)
                viewModelScope.launch {
                    taskUseCases.insertSubtask(subtask)
                }
            }
            is AddEditTaskEvent.DeleteSubtask -> {
                _subtasks.value = subtasks.value.minusElement(event.subtask)
                viewModelScope.launch {
                    taskUseCases.deleteSubtask(event.subtask)
                }
            }
            is AddEditTaskEvent.SaveSubtask -> {
                viewModelScope.launch {
                    taskUseCases.insertSubtask(event.subtask.copy(name = event.name))
                }
            }
            is AddEditTaskEvent.SaveTask -> {
                viewModelScope.launch {
                    try {
                        taskUseCases.insertTask(
                            Task(
                                title = taskTitle.value.text,
                                content = taskTitle.value.text,
                                timestamp = taskDueDate.value,
                                taskId = currentTaskId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    }catch (e: InvalidTaskException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save task"
                            )
                        )
                    }
                }
            }
        }
    }


    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}