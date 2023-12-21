package com.example.room.data.repositories

import com.example.room.data.db.ToDoEntity
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    suspend fun saveTodo(todo: String, done: Boolean = false)
    suspend fun deleteAll()
    suspend fun deleteById(id: Int)

    suspend fun deleteAllCompletely()

    suspend fun update(toDo: ToDoEntity)
    suspend fun getAll(): List<ToDoEntity>
    var todoFlow: Flow<List<ToDoEntity>>
    var deletedFlow: Flow<List<ToDoEntity>>
}