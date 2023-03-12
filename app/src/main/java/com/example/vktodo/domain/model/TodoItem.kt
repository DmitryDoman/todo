package com.example.vktodo.domain.model

data class TodoItem(
    val title: String,
    val timestamp: Long,
    val id: Int?
)