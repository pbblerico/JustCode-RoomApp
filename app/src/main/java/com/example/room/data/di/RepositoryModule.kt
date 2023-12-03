package com.example.room.data.di

import com.example.room.data.db.ToDoDao
import com.example.room.data.repositories.ToDoRepository
import com.example.room.data.repositories.ToDoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideToDoRepository(toDoDao: ToDoDao): ToDoRepository = ToDoRepositoryImpl(toDoDao)
}