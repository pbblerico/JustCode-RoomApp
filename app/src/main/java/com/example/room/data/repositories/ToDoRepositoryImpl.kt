package com.example.room.data.repositories

import com.example.room.data.db.ToDoDao
import com.example.room.data.db.ToDoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class ToDoRepositoryImpl @Inject constructor(
    private val dao: ToDoDao
): ToDoRepository {
    override suspend fun saveTodo(todo: String, done: Boolean) {
        dao.save(
            ToDoEntity(
                0,
                todo,
                done
            )
        )
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun deleteById(id: Int) {
        dao.deleteById(id)
    }

    override suspend fun deleteAllCompletely() {
        dao.deleteAllCompletely()
    }

    override suspend fun update(toDo: ToDoEntity) {
        dao.update(toDo)
    }

    override suspend fun getAll(): List<ToDoEntity> {
        return dao.getAll()
    }

    override var todoFlow: Flow<List<ToDoEntity>> = dao.getAllFlow()
    override var deletedFlow: Flow<List<ToDoEntity>> = todoFlow.map {list ->
        list.filter { toDo -> toDo.deleted }
    }

}