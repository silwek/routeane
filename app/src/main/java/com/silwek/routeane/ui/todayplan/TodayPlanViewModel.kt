package com.silwek.routeane.ui.todayplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silwek.routeane.data.AppPreferences
import com.silwek.routeane.data.entities.RoutineMode
import com.silwek.routeane.data.repositories.PlannerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodayPlanViewModel(
    private val repository: PlannerRepository,
    private val preferences: AppPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodayUiState())
    val uiState: StateFlow<TodayUiState> = _uiState.asStateFlow()

    init {
        observeModes()
    }

    private fun observeModes() {
        viewModelScope.launch {
            preferences.getLastSelectedModeId()
                .combine(repository.getAllModesFlow()) { savedId, modes ->
                    val selected = modes.find { it.id == savedId } ?: modes.firstOrNull()
                    Triple(modes, selected, savedId)
                }
                .collect { (modes, selectedMode, _) ->
                    _uiState.update {
                        it.copy(
                            modes = modes,
                            selectedMode = selectedMode
                        )
                    }
                    loadAssignmentsForMode()
                }
        }
    }

    fun onModeSelected(mode: RoutineMode) {
        _uiState.update { it.copy(selectedMode = mode) }
        viewModelScope.launch {
            preferences.setLastSelectedModeId(mode.id)
        }
        loadAssignmentsForMode()
    }

    fun loadAssignmentsForMode() {
        val modeId = _uiState.value.selectedMode?.id ?: return
        val dayOfWeek = getTodayDayOfWeek()
        loadAssignments(dayOfWeek, modeId)
    }

    fun loadAssignments(dayOfWeek: Int, modeId: Int) {
        viewModelScope.launch {
            repository.getAssignmentsForDayAndModeFlow(dayOfWeek, modeId).collect { assignments ->
                _uiState.update { it.copy(assignments = assignments) }
            }
        }
    }

    private fun getTodayDayOfWeek(): Int {
        return java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK)
            .let { if (it == 1) 7 else it - 1 } // DIM=1 => 7, LUN=2 => 1
    }
}