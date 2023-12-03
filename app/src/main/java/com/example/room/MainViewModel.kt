package com.example.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.room.data.db.ToDoEntity
import com.example.room.data.repositories.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: ToDoRepository
): BaseViewModel() {

    val todoListLiveData: LiveData<List<ToDoEntity>> = repo.todoFlow.asLiveData()

    private val _getAllLiveData = MutableLiveData<List<ToDoEntity>>()
    val getAllLieData: LiveData<List<ToDoEntity>> = _getAllLiveData

    init {
        getAll()
    }

    fun saveTodo(todo: String, done: Boolean = false) {
        launch(
            request = {
                repo.saveTodo(todo, done)
            }
        )
    }

    fun deleteAll() {
        launch(
            request = {
                repo.deleteAll()
            }
        )
    }

    fun updateToDo(toDo: ToDoEntity) {
        launch(
            request = {
                repo.update(toDo)
            }
        )
    }
    fun getAll() {
        launch(
            request = {
                repo.getAll()
            },
            onSuccess = {
                _getAllLiveData.postValue(it)
            }
        )
    }
}