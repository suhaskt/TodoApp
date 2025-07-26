package com.suhas.todoapplication.data

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoDatabaseModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoAppDatabase {
        return Room.databaseBuilder(app, TodoAppDatabase::class.java, "todo-database")
            .build()
    }

    @Provides
    fun provideTodoDao(database: TodoAppDatabase): TodoDao {
        return database.todoDao()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(todoDao: TodoDao): TodoRepository {
        return TodoRepository(todoDao)
    }
}