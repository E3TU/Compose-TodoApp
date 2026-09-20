package com.example.todoapp.ui.todo

import android.app.Application
import android.icu.text.CaseMap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TodoRepository(application)

    val todos: StateFlow<List<Todo>> = repository.todos
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addTodo(id: Long, title: String, description: String, completed: Boolean) = viewModelScope.launch {
        repository.add(id, title, description, completed)
    }

    fun toggle(todo: Todo) = viewModelScope.launch {
        repository.toggle(todo)
    }

    fun delete(todo: Todo) = viewModelScope.launch {
        repository.delete(todo)
    }
}