package com.silwek.routeane.ui.routines

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.silwek.routeane.data.entities.RoutineItem
import com.silwek.routeane.ui.theme.Dimens
import com.silwek.routeane.ui.theme.RouteaneTheme


@Composable
fun RoutinesScreen(routines: List<RoutineItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.CardVerticalSpacing)
    ) {
        items(routines) { routine ->
            RoutineItemConfigCard(
                routine,
                Modifier.padding(horizontal = Dimens.ScreenHorizontalPadding), {}, {}
            )
        }
    }
}


@Composable
fun RoutinesScreenWithViewModel(
    viewModel: RoutineItemsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(paddingValues = WindowInsets.statusBars.asPaddingValues())
    ) {
        RoutinesScreen(routines = uiState.items)
    }
}


@Preview(showBackground = true)
@Composable
fun RoutinesScreenPreview() {
    val fakeRoutines = listOf(
        RoutineItem(1, "Make bed", 5),
        RoutineItem(2, "Brush teeth", 3),
        RoutineItem(3, "Yoga", 20)
    )
    RouteaneTheme {
        RoutinesScreen(fakeRoutines)
    }
}