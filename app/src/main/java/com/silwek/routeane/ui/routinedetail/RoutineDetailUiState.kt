package com.silwek.routeane.ui.routinedetail

import com.silwek.routeane.data.entities.AssignmentForDay
import com.silwek.routeane.data.entities.RoutineItem

data class RoutineDetailUiState(
    val routineItem: RoutineItem = RoutineItem(),
    val assignmentsPerDay: Map<Int, List<AssignmentForDay>> = emptyMap()
)
