package com.suhas.todoapplication.data

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
        /*val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE todo_list ADD COLUMN createdAt INTEGER NOT NULL DEFAULT 0")

                val currentTime = System.currentTimeMillis()
                db.execSQL("UPDATE todo_list SET createdAt = $currentTime")
            }
        }
        return Room.databaseBuilder(app, TodoAppDatabase::class.java, "todo-database")
            .addMigrations(MIGRATION_1_2).build()*/

        return Room.databaseBuilder(app, TodoAppDatabase::class.java, "todo-database")
            .fallbackToDestructiveMigration(false).build()
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