package com.silwek.routeane.ui.todayplan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.silwek.routeane.data.entities.RoutineDayAssignmentWithItem
import com.silwek.routeane.ui.components.ModeChipsSelector
import com.silwek.routeane.ui.theme.Dimens
import com.silwek.routeane.ui.theme.RouteaneTheme

@Composable
fun TodayScreen(assignments: List<RoutineDayAssignmentWithItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.CardVerticalSpacing)
    ) {
        items(assignments) { assignment ->
            RoutineItemCard(
                assignment,
                Modifier.padding(horizontal = Dimens.ScreenHorizontalPadding)
            )
        }
    }
}

@Composable
fun TodayScreenWithViewModel(
    viewModel: TodayPlanViewModel,
    dayOfWeek: Int? = null,
    modeId: Int? = null
) {
    LaunchedEffect(Unit) {
        if (dayOfWeek != null && modeId != null) {
            viewModel.loadAssignments(dayOfWeek, modeId)
        } else {
            viewModel.loadAssignmentsForMode()
        }
    }

    val uiState by viewModel.uiState.collectAsState()
    val mode = uiState.selectedMode ?: uiState.modes.firstOrNull()

    Column(
        modifier = Modifier
            .padding(paddingValues = WindowInsets.statusBars.asPaddingValues())
    ) {
        if (mode != null)
            ModeChipsSelector(
                modes = uiState.modes,
                selectedMode = mode,
                onModeSelected = viewModel::onModeSelected
            )

        TodayScreen(assignments = uiState.assignments)
    }
}

@Preview(showBackground = true)
@Composable
fun TodayScreenPreview() {
    val fakeAssignments = listOf(
        RoutineDayAssignmentWithItem(1, -1, 1, 1, "Make bed", 5),
        RoutineDayAssignmentWithItem(2, -1, 1, 2, "Brush teeth", 3),
        RoutineDayAssignmentWithItem(3, 1, 1, 3, "Yoga", 20)
    )
    RouteaneTheme {
        TodayScreen(fakeAssignments)
    }
}