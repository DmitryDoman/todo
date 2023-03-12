package com.example.vktodo.di

import android.app.Application
import androidx.room.Room
import com.example.vktodo.data.datasource.TodoDatabase
import com.example.vktodo.data.repository.TodoRepositoryImpl
import com.example.vktodo.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository = TodoRepositoryImpl(db.todoDao)
}