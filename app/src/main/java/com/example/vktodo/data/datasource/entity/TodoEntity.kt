package com.example.vktodo.data.datasource.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vktodo.domain.model.TodoItem

@Entity(tableName = "todo")
data class TodoEntity(
    val title: String,
    val timestamp: Long,
    @PrimaryKey val id: Int? = null,
)

fun TodoEntity.asTodoItem() = TodoItem(
    title = title,
    timestamp = timestamp,
    id = id
)

fun TodoItem.asTodoEntity() = TodoEntity(
    title = title,
    timestamp = timestamp,
    id = id
)