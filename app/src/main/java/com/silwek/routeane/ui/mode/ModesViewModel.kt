package com.silwek.routeane.ui.mode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silwek.routeane.data.entities.RoutineMode
import com.silwek.routeane.data.repositories.PlannerRepository
import com.silwek.routeane.ui.components.RouteaneIcons
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ModesViewModel(
    private val repository: PlannerRepository
) : ViewModel() {

    val modes = repository.getAllModesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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