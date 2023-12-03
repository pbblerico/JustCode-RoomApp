package com.example.room.data.repositories

import com.example.room.data.db.ToDoEntity
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    suspend fun saveTodo(todo: String)
    suspend fun deleteAll()
    suspend fun deleteById(id: Int)
    suspend fun getAll(): List<ToDoEntity>
    suspend fun getById(id: Int): ToDoEntity
    var todoFlow: Flow<List<ToDoEntity>>
}