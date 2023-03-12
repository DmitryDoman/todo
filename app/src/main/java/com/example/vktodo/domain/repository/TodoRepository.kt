package com.example.vktodo.domain.repository

import com.example.vktodo.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    fun fetchTodoList(): Flow<List<TodoItem>>

    suspend fun create(todoItem: TodoItem)

    suspend fun remove(todoItem: TodoItem)

    suspend fun fetchById(id: Int): TodoItem?
}