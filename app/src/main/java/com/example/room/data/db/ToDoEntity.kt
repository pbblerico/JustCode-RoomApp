package com.example.room.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="to_do")
data class ToDoEntity(
    @PrimaryKey(autoGenerate = true)val id: Int,
    @ColumnInfo(name= "to_do") val toDo: String,
    val done: Boolean = false
)