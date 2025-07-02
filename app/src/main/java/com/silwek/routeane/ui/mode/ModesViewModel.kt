package com.silwek.routeane.ui.mode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silwek.routeane.data.entities.RoutineMode
import com.silwek.routeane.data.repositories.PlannerRepository
import com.silwek.routeane.ui.components.RouteaneIcons
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ModesViewModel(
    private val repository: PlannerRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(ModesUiState())
    val uiState: StateFlow<ModesUiState> = _uiState.asStateFlow()

    init {
        observeModes()
    }

    private fun observeModes() {
        viewModelScope.launch {
            repository.getAllModesFlow().collect { modes ->
                _uiState.update { it.copy(modes = modes) }
            }
        }
    }

    fun insertMode(mode: RoutineMode) {
        viewModelScope.launch {
            repository.insertMode(
                RoutineMode(
                    name = mode.name,
                    iconName = RouteaneIcons.EMPTY_ICON
                )
            )
        }
    }

    fun onUpdatingMode(mode: RoutineMode?) {
        viewModelScope.launch {
            _uiState.update { it.copy(modeBeingEdited = mode) }
        }
    }

    fun updateModeName(modeId: Int, newName: String) {
        viewModelScope.launch {
            repository.updateModeName(modeId, newName)
        }
    }

    fun updateModeIcon(modeId: Int, iconName: String) {
        viewModelScope.launch {
            repository.updateModeIcon(modeId, iconName)
        }
    }

    fun deleteMode(mode: RoutineMode) {
        viewModelScope.launch {
            repository.deleteMode(mode)
        }
    }

}