package com.example.vktodo.presenter.add

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vktodo.domain.model.TodoItem
import com.example.vktodo.domain.repository.TodoRepository
import com.example.vktodo.domain.usecase.ValidateTitleInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(AddItemState())
    val state: StateFlow<AddItemState> = _state.asStateFlow()

    private val _uiActions = Channel<AddItemActions>(Channel.UNLIMITED)
    val uiActions: Flow<AddItemActions> = _uiActions.receiveAsFlow()

    private var currentId: Int? = null

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            viewModelScope.launch {
                repository.fetchById(id)?.let { item ->
                    currentId = item.id
                    _state.update {
                        it.copy(
                            title = item.title,
                            date = Calendar.getInstance().apply { timeInMillis = item.timestamp },
                            isSaveEnabled = true
                        )
                    }
                }
            }
        }
    }
    fun onEvent(event: AddTotoItemEvent) {
        when (event) {
            AddTotoItemEvent.Save -> {
                viewModelScope.launch {
                    repository.create(
                        TodoItem(
                            title = _state.value.title,
                            timestamp = _state.value.date.timeInMillis,
                            id = currentId
                        )
                    )
                    _uiActions.send(AddItemActions.NavigateUp)
                }
            }

            is AddTotoItemEvent.TitleChanged -> {
                _state.update {
                    it.copy(
                        title = event.title,
                        isSaveEnabled = ValidateTitleInput().invoke(event.title)
                    )
                }
            }

            is AddTotoItemEvent.DataChanged -> {
                _state.update { it.copy(date = event.date) }
            }

            AddTotoItemEvent.ShowDateTimePicker -> {
                viewModelScope.launch {
                    _uiActions.send(AddItemActions.OpenDateTimePicker)
                }
            }
        }
    }
}