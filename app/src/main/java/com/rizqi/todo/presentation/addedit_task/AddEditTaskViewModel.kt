package com.rizqi.todo.presentation.addedit_task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.domain.use_case.TaskUseCases
import com.rizqi.todo.domain.util.InvalidTaskException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    private val _taskTitle = mutableStateOf(TaskTextFieldState(
        hint = "Enter title"
    ))
    val taskTitle: State<TaskTextFieldState> = _taskTitle

    private val _taskContent = mutableStateOf(TaskTextFieldState(
        hint = "Enter some content"
    ))
    val taskContent: State<TaskTextFieldState> = _taskContent

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTaskId: Long? = null

    init {
        savedStateHandle.get<Long>("taskId")?.let { taskId ->
            if(taskId != -1L){
                viewModelScope.launch {
                    taskUseCases.getTask(taskId)?.also { task ->
                        currentTaskId = task.id
                        _taskTitle.value = taskTitle.value.copy(
                            text = task.title,
                            isHintVisible = false
                        )
                        _taskContent.value = taskContent.value.copy(
                            text = task.content,
                            isHintVisible = false
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditTaskEvent){
        when(event){
            is AddEditTaskEvent.ChangeContentFocus -> {
                _taskContent.value = _taskContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && _taskContent.value.text.isBlank()
                )
            }
            is AddEditTaskEvent.ChangeTitleFocus -> {
                _taskTitle.value = taskTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && taskTitle.value.text.isBlank()
                )
            }
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
            is AddEditTaskEvent.SaveTask -> {
                viewModelScope.launch {
                    try {
                        taskUseCases.insertTask(
                            Task(
                                title = taskTitle.value.text,
                                content = taskTitle.value.text,
                                timestamp = System.currentTimeMillis(),
                                id = currentTaskId
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