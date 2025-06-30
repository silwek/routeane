package com.silwek.routeane.ui.todayplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silwek.routeane.data.repositories.PlannerRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TodayPlanViewModel(
    private val repository: PlannerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodayUiState())
    val uiState: StateFlow<TodayUiState> = _uiState.asStateFlow()

    fun loadAssignments(dayOfWeek: Int, modeId: Int) {
        viewModelScope.launch {
            repository.getAssignmentsForDayAndModeFlow(dayOfWeek, modeId)
                .collect { assignments ->
                    _uiState.value = TodayUiState(assignments = assignments)
                }
        }
    }
}