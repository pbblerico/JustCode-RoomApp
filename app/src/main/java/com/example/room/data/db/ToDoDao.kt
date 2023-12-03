package com.example.room.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ToDoDao {

    @Query("Select * from to_do where id = :id LIMIT 1")
    fun getById(id: Int): ToDoEntity

    @Query("Select * from to_do")
    fun getAll(): List<ToDoEntity> ////

    @Query("Select * from to_do")
    fun getAllFlow(): Flow<List<ToDoEntity>>  ////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(todoEntity: ToDoEntity) ////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(todoEntity: List<ToDoEntity>) ////

    @Query("Delete from to_do") ////
    fun deleteAll()

    @Query("Delete from to_do where id = :id")
    fun deleteById(id: Int) ////
}