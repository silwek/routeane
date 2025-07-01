package com.silwek.routeane.ui.todayplan

import com.silwek.routeane.data.entities.RoutineDayAssignmentWithItem
import com.silwek.routeane.data.entities.RoutineMode


data class TodayUiState(
    val assignments: List<RoutineDayAssignmentWithItem> = emptyList(),
    val modes: List<RoutineMode> = emptyList(),
    val selectedMode: RoutineMode? = null
)