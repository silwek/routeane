package com.silwek.routeane.ui.routines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silwek.routeane.R
import com.silwek.routeane.data.entities.RoutineItem
import com.silwek.routeane.ui.components.ConfirmDeleteDialog
import com.silwek.routeane.ui.components.RoutineFormDialog
import com.silwek.routeane.ui.theme.Dimens
import com.silwek.routeane.ui.theme.RouteaneTheme
import timber.log.Timber


@Composable
fun RoutinesScreen(
    routines: List<RoutineItem>,
    onNavigateToAssignments: (Int) -> Unit = { },
    onAddRoutine: (String, Int) -> Unit = { _, _ -> },
    onDeleteRoutine: (Int) -> Unit = { }
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var editItem by remember { mutableStateOf<Int?>(null) }
    var editItemName by remember { mutableStateOf<String?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_routine_action)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(Dimens.CardVerticalSpacing)
            ) {
                items(routines) { routine ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { value ->
                            if (value == SwipeToDismissBoxValue.EndToStart || value == SwipeToDismissBoxValue.StartToEnd) {
                                editItem = routine.id
                                editItemName = routine.name
                                showDeleteDialog = true
                            }
                            false
                        }
                    )

                    SwipeToDismissBox(
                        enableDismissFromEndToStart = true,
                        enableDismissFromStartToEnd = false,
                        state = dismissState,
                        backgroundContent = {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = Dimens.ScreenHorizontalPadding)
                            ) {
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.error)
                                        .padding(16.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = stringResource(
                                            R.string.delete_routine_action,
                                            routine.name
                                        ),
                                        tint = Color.White,
                                        modifier = Modifier.align(Alignment.CenterEnd)
                                    )
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        RoutineItemConfigCard(
                            routine,
                            Modifier.padding(horizontal = Dimens.ScreenHorizontalPadding),
                            onClick = {
                                onNavigateToAssignments(routine.id)
                            },
                            onWantToDelete = {
                                editItem = routine.id
                                editItemName = routine.name
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
        }
    }
    if (showAddDialog) {
        RoutineFormDialog(
            onDismiss = {
                showAddDialog = false
            },
            onConfirm = { name, duration ->
                onAddRoutine(name, duration)
                showAddDialog = false
            }
        )
    }

    if (showDeleteDialog) {
        ConfirmDeleteDialog(
            title = stringResource(R.string.delete_mode_title, editItemName ?: ""),
            onConfirm = {
                editItem?.let {
                    onDeleteRoutine(it)
                }
                showDeleteDialog = false
                editItemName = null
                editItem = null
            },
            onDismiss = {
                showDeleteDialog = false
                editItemName = null
                editItem = null
            }
        )
    }
}


@Composable
fun RoutinesScreenWithViewModel(
    viewModel: RoutineItemsViewModel,
    onNavigateToAssignments: (Int) -> Unit = { }
) {

    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(uiState) {
        Timber.d("Recompose due to uiState: ${uiState.items.hashCode()}")
    }
    SideEffect {
        Timber.d("Recomposition")
    }

    Column(
        modifier = Modifier
            .padding(paddingValues = WindowInsets.statusBars.asPaddingValues())
    ) {
        RoutinesScreen(
            routines = uiState.items,
            onNavigateToAssignments = onNavigateToAssignments,
            onAddRoutine = { name, duration ->
                viewModel.addRoutine(name, duration)
            },
            onDeleteRoutine = { id ->
                viewModel.deleteRoutine(id)
            }
        )
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