package com.silwek.routeane.ui.todayplan

import com.silwek.routeane.data.entities.RoutineDayAssignmentWithItem


data class TodayUiState(
    val assignments: List<RoutineDayAssignmentWithItem> = emptyList()
)