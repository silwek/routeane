package com.silwek.routeane.ui.routinedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silwek.routeane.data.entities.RoutineDayAssignment
import com.silwek.routeane.data.entities.RoutineItem
import com.silwek.routeane.data.repositories.PlannerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoutineDetailViewModel(
    private val repository: PlannerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RoutineDetailUiState())
    val uiState: StateFlow<RoutineDetailUiState> = _uiState

    fun loadRoutineDetail(itemId: Int) {
        viewModelScope.launch {
            refreshRoutineItem(itemId)
            repository.getAssignmentsForItem(itemId)
                .collect { assignments ->
                    val assignmentsPerDay = (1..7).associateWith { day ->
                        assignments.filter {
                            it.dayOfWeek == day || it.dayOfWeek == RoutineDayAssignment.EVERYDAY
                        }
                    }
                    _uiState.update { it.copy(assignmentsPerDay = assignmentsPerDay) }
                }
        }
    }

    private suspend fun refreshRoutineItem(itemId: Int) {
        val routineItem = repository.getRoutineItem(itemId)
        _uiState.update { it.copy(routineItem = routineItem ?: RoutineItem(itemId, "", 0)) }
    }

    fun editRoutine(itemId: Int, name: String, duration: Int) {
        viewModelScope.launch {
            repository.updateRoutineItem(itemId, name, duration)
            refreshRoutineItem(itemId)
        }
    }

    fun editRoutineIcon(itemId: Int, iconName: String) {
        viewModelScope.launch {
            repository.updateRoutineItemIcon(itemId, iconName)
            refreshRoutineItem(itemId)
        }
    }

    fun addAssignment(itemId: Int, dayOfWeek: Int, modeId: Int) {
        //TODO remove all previous assignments for the mode if dayOfWeek is EVERYDAY
        viewModelScope.launch {
            repository.insertAssignment(
                RoutineDayAssignment(
                    routineItemId = itemId,
                    dayOfWeek = dayOfWeek,
                    modeId = modeId
                )
            )
        }
    }

    fun removeAssignment(assignmentId: Int, dayOfWeek: Int) {
        viewModelScope.launch {
            val assignment = repository.getAssignment(assignmentId)
            if (assignment != null && assignment.dayOfWeek == RoutineDayAssignment.EVERYDAY) {
                (1..7).forEach {
                    if (it != dayOfWeek) {
                        repository.insertAssignment(
                            RoutineDayAssignment(
                                routineItemId = assignment.routineItemId,
                                dayOfWeek = it,
                                modeId = assignment.modeId
                            )
                        )
                    }
                }
            }
            repository.removeAssignment(assignmentId)
        }
    }
}