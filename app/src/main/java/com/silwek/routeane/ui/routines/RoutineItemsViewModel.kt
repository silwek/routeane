package com.silwek.routeane.ui.routines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silwek.routeane.data.AppPreferences
import com.silwek.routeane.data.repositories.PlannerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoutineItemsViewModel(
    private val repository: PlannerRepository,
    private val preferences: AppPreferences
) : ViewModel() {
    private val _uiState = MutableStateFlow(RoutinesConfigUiState())
    val uiState: StateFlow<RoutinesConfigUiState> = _uiState.asStateFlow()


    init {
        observeItems()
    }

    private fun observeItems() {
        viewModelScope.launch {
            repository.getAllItemsFlow().collect { items ->
                _uiState.update { it.copy(items = items) }
            }
        }
    }

    fun addRoutine(name: String, duration: Int) {
        viewModelScope.launch {
            repository.insertRoutineItem(name, duration)
        }
    }

    fun deleteRoutine(id: Int) {
        viewModelScope.launch {
            repository.deleteRoutineItem(id)
        }
    }
}