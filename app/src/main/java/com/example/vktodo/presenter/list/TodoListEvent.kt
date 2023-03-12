package com.example.vktodo.presenter.list

import com.example.vktodo.domain.model.TodoItem

sealed interface TodoListEvent {
    object AddNewItem : TodoListEvent
    data class EditTodoItem(val id: Int) : TodoListEvent
    data class RemoveTodoItem(val item: TodoItem) : TodoListEvent
}