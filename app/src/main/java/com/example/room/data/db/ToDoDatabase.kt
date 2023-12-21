package com.example.room.data.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ToDoEntity::class], version = 2)
abstract class ToDoDatabase: RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}