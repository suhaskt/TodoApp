package com.suhas.todoapplication.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 2, exportSchema = true)
abstract class TodoAppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}