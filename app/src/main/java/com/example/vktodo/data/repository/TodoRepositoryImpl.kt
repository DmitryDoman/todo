package com.example.vktodo.data.repository

import com.example.vktodo.data.datasource.TodoDao
import com.example.vktodo.data.datasource.entity.TodoEntity
import com.example.vktodo.data.datasource.entity.asTodoEntity
import com.example.vktodo.data.datasource.entity.asTodoItem
import com.example.vktodo.domain.model.TodoItem
import com.example.vktodo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(private val dao: TodoDao) : TodoRepository {

    override fun fetchTodoList(): Flow<List<TodoItem>> =
        dao.fetchTodoList().map { it.map(TodoEntity::asTodoItem) }


    override suspend fun create(todoItem: TodoItem) {
        dao.insert(todoItem.asTodoEntity())
    }

    override suspend fun remove(todoItem: TodoItem) {
        dao.remove(todoItem.asTodoEntity())
    }

    override suspend fun fetchById(id: Int): TodoItem? =
        dao.fetchById(id)?.asTodoItem()
}