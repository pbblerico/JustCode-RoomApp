package com.example.room.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface ToDoDao {

    @Query("Select * from to_do")
    fun getAll(): List<ToDoEntity> ////

    @Query("Select * from to_do")
    fun getAllFlow(): Flow<List<ToDoEntity>>  ////

    @Update
    fun update(toDo: ToDoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(todoEntity: ToDoEntity) ////

    @Query("UPDATE to_do SET deleted = 1 WHERE deleted = 0") ////
    fun deleteAll()

    @Query("Delete from to_do WHERE deleted = 1")
    fun deleteAllCompletely()

    @Query("Delete from to_do where id = :id")
    fun deleteById(id: Int) ////
}