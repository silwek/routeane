package com.silwek.routeane.ui.mode

import com.silwek.routeane.data.entities.RoutineMode

data class ModesUiState(
    val modes: List<RoutineMode> = emptyList(),
    val modeBeingEdited: RoutineMode? = null,
    val modeToDelete: RoutineMode? = null,
    val showAddDialog: Boolean = false,
    val showPicker: Boolean = false
)