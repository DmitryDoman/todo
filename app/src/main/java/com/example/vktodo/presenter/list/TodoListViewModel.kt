package com.example.vktodo.presenter.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vktodo.domain.model.TodoItem
import com.example.vktodo.domain.repository.TodoRepository
import com.example.vktodo.presenter.utils.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private var fetchJob: Job? = null

    private val _todoList = MutableStateFlow(listOf<TodoItem>())
    val todoList = _todoList.asStateFlow()

    private val _navigator = Channel<String>(Channel.UNLIMITED)
    val navigator = _navigator.receiveAsFlow()

    init {
        fetchList()
    }

    fun onEvent(event: TodoListEvent) {
        when (event) {
            TodoListEvent.AddNewItem -> {
                viewModelScope.launch {
                    _navigator.send(Screen.Add.destination)
                }
            }

            is TodoListEvent.EditTodoItem -> {
                viewModelScope.launch {
                    _navigator.send("${Screen.Add.destination}/${event.id}")
                }
            }

            is TodoListEvent.RemoveTodoItem -> {
                viewModelScope.launch {
                    repository.remove(event.item)
                }
            }
        }
    }

    private fun fetchList() {
        fetchJob?.cancel()
        fetchJob = repository.fetchTodoList().onEach { list ->
            _todoList.update { list }
        }.launchIn(viewModelScope)
    }
}