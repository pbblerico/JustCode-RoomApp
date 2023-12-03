package com.example.room.data.repositories

import com.example.room.data.db.ToDoDao
import com.example.room.data.db.ToDoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ToDoRepositoryImpl @Inject constructor(
    private val dao: ToDoDao
): ToDoRepository {
    override suspend fun saveTodo(todo: String) {
        dao.save(
            ToDoEntity(
                0,
                todo
            )
        )
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun deleteById(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun getAll(): List<ToDoEntity> {
        return getAll()
    }

    override suspend fun getById(id: Int): ToDoEntity {
        return dao.getById(id)
    }

    override var todoFlow: Flow<List<ToDoEntity>> = dao.getAllFlow()

}