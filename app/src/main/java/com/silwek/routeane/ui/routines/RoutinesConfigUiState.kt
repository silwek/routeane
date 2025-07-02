package com.silwek.routeane.ui.routines

import com.silwek.routeane.data.entities.RoutineItem

data class RoutinesConfigUiState(
    val items: List<RoutineItem> = emptyList(),
)