package com.example.vktodo.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vktodo.data.datasource.entity.TodoEntity

@Database(
    entities = [TodoEntity::class],
    version = 1
)
abstract class TodoDatabase : RoomDatabase() {

    abstract val todoDao: TodoDao

    companion object {
        const val DATABASE_NAME = "todo_db"
    }
}