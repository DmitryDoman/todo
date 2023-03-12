package com.example.vktodo.data.datasource

import androidx.room.*
import com.example.vktodo.data.datasource.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo")
    fun fetchTodoList() : Flow<List<TodoEntity>>

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun fetchById(id: Int) : TodoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TodoEntity)

    @Delete
    suspend fun remove(item: TodoEntity)

}