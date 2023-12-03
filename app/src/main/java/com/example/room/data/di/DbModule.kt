package com.example.room.data.di

import android.content.Context
import androidx.room.Room
import com.example.room.data.db.ToDoDao
import com.example.room.data.db.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {

    @Singleton
    @Provides
    fun getDatabase(@ApplicationContext context: Context): ToDoDatabase {
        return Room
            .databaseBuilder(context, ToDoDatabase::class.java, "To do database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun getTodoDao(db: ToDoDatabase): ToDoDao = db.toDoDao()
}