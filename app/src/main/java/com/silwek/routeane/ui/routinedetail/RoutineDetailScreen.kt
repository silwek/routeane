package com.silwek.routeane.ui.routinedetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silwek.routeane.R
import com.silwek.routeane.data.entities.AssignmentForDay
import com.silwek.routeane.data.entities.RoutineDayAssignment.Companion.EVERYDAY
import com.silwek.routeane.data.entities.RoutineItem
import com.silwek.routeane.data.entities.RoutineMode
import com.silwek.routeane.getDayName
import com.silwek.routeane.ui.components.IconPickerDialog
import com.silwek.routeane.ui.components.RouteaneIcons
import com.silwek.routeane.ui.components.RoutineFormDialog
import com.silwek.routeane.ui.components.getIconResource
import com.silwek.routeane.ui.mode.ModesViewModel
import com.silwek.routeane.ui.theme.Primary
import com.silwek.routeane.ui.theme.RouteaneTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineDetailScreen(
    routine: RoutineItem,
    assignmentsPerDay: Map<Int, List<AssignmentForDay>>,
    modes: List<RoutineMode>,
    onAddAssignment: (day: Int, modeId: Int) -> Unit,
    onRemoveAssignment: (assignmentId: Int, day: Int) -> Unit,
    onEditRoutine: (name: String, duration: Int) -> Unit = { _, _ -> },
    onEditRoutineIcon: (iconName: String) -> Unit = { },
    onClearAssignmentAll: () -> Unit = {},
    onBack: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    var selectedDay by remember { mutableStateOf<Int?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showEditIconDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .wrapContentSize()
                    .clickable(
                        enabled = true,
                        onClick = {
                            showEditDialog = true
                        })
            ) {

                IconButton(onClick = {
                    showEditIconDialog = true
                }) {
                    Box {

                        Icon(
                            painter = painterResource(
                                id = getIconResource(routine.iconName)
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .padding(horizontal = 2.dp, vertical = 4.dp),
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = stringResource(R.string.edit_routine_action),
                            modifier = Modifier
                                .size(14.dp)
                                .background(color = Primary, shape = MaterialTheme.shapes.small)
                                .padding(2.dp)
                        )
                    }
                }
                Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                    Text(
                        routine.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        stringResource(
                            R.string.routine_duration_min,
                            routine.defaultDurationMinutes ?: 5
                        ),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Icon(
                    Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_routine_action),
                    modifier = Modifier
                        .size(14.dp)
                        .background(color = Primary, shape = MaterialTheme.shapes.small)
                        .padding(2.dp)
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedButton(
                onClick = { selectedDay = EVERYDAY },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Faire tous les jours"
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Icon(
                        painter = painterResource(R.drawable.schedule_add__icon),
                        contentDescription = stringResource(R.string.add_routine_assignment_action),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            LazyColumn {
                assignmentsPerDay.forEach { (day, assignments) ->
                    stickyHeader {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                getDayName(day, stringResource(R.string.everyday)),
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleLarge,
                            )
                            IconButton({
                                selectedDay = day
                                coroutineScope.launch { sheetState.show() }
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.schedule_add__icon),
                                    contentDescription = stringResource(R.string.add_routine_assignment_action),
                                    tint = MaterialTheme.colorScheme.onBackground,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                    if (!assignments.isEmpty()) {
                        items(assignments) { assignment ->
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                ChipMode(assignment.modeName, assignment.iconName) {
                                    onRemoveAssignment(assignment.assignmentId, day)
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.padding(16.dp))
                    OutlinedButton(
                        onClick = { onClearAssignmentAll() },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.error),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(R.string.clear_all_assignments)
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = stringResource(R.string.clear_all_assignments),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }

        if (selectedDay != null) {
            ModalBottomSheet(
                onDismissRequest = {
                    selectedDay = null
                },
                sheetState = sheetState
            ) {
                Text(
                    "Sélectionner un mode",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    items(modes) { mode ->
                        ChipMode(
                            modeName = mode.name,
                            iconName = mode.iconName,
                            onClick = {
                                onAddAssignment(selectedDay!!, mode.id)
                                coroutineScope.launch {
                                    sheetState.hide()
                                    selectedDay = null
                                }
                            }
                        )
                    }
                }
            }
        }

        if (showEditDialog) {
            RoutineFormDialog(
                initialName = routine.name,
                initialDuration = routine.defaultDurationMinutes ?: 5,
                onDismiss = {
                    showEditDialog = false
                },
                onConfirm = { name, duration ->
                    onEditRoutine(name, duration)
                    showEditDialog = false
                }
            )
        }

        if (showEditIconDialog) {
            IconPickerDialog(
                icons = RouteaneIcons.allIcons,
                onIconSelected = { selectedIcon ->
                    onEditRoutineIcon(selectedIcon)
                    showEditIconDialog = false
                },
                onDismiss = {
                    showEditIconDialog = false
                }
            )
        }
    }
}

@Composable
fun SmallTopAppBar(title: () -> Unit, navigationIcon: () -> Unit) {
    TODO("Not yet implemented")
}

@Composable
fun RoutineDetailScreenWithViewModel(
    viewModel: RoutineDetailViewModel,
    modesViewModel: ModesViewModel,
    itemId: Int,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val modesState by modesViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadRoutineDetail(itemId)
    }
    RoutineDetailScreen(
        routine = uiState.routineItem,
        assignmentsPerDay = uiState.assignmentsPerDay,
        modes = modesState.modes,
        onAddAssignment = { day, modeId ->
            viewModel.addAssignment(itemId, day, modeId)
        },
        onRemoveAssignment = { assignmentId, day ->
            viewModel.removeAssignment(assignmentId, day)
        },
        onEditRoutine = { name, duration ->
            viewModel.editRoutine(itemId, name, duration)

        },
        onEditRoutineIcon = { iconName ->
            viewModel.editRoutineIcon(itemId, iconName)
        },
        onBack = onBack
    )
}

@Suppress("VisualLintBounds")
@Preview(showBackground = true)
@Composable
fun RoutineDetailScreenPreview() {

    RouteaneTheme {
        RoutineDetailScreen(
            routine = RoutineItem(
                1,
                "Routine",
                defaultDurationMinutes = 15,
                iconName = "ic_lib_calendar"
            ),
            assignmentsPerDay = mapOf(
                1 to listOf(
                    AssignmentForDay(
                        assignmentId = 0,
                        dayOfWeek = 1,
                        modeId = 1,
                        modeName = "Travail",
                        iconName = "ic_lib_calendar"
                    ),
                    AssignmentForDay(
                        assignmentId = 0,
                        dayOfWeek = 1,
                        modeId = 2,
                        modeName = "Santé",
                        iconName = "ic_lib_none"
                    ),
                    AssignmentForDay(
                        assignmentId = 0,
                        dayOfWeek = -1,
                        modeId = 4,
                        modeName = "Lecture",
                        iconName = "ic_lib_book"
                    )
                ),
                2 to listOf(
                    AssignmentForDay(
                        assignmentId = 0,
                        dayOfWeek = -1,
                        modeId = 4,
                        modeName = "Lecture",
                        iconName = "ic_lib_book"
                    ),
                    AssignmentForDay(
                        assignmentId = 0,
                        dayOfWeek = 2,
                        modeId = 1,
                        modeName = "Travail",
                        iconName = "ic_lib_calendar"
                    )
                ),
                3 to listOf(
                    AssignmentForDay(
                        assignmentId = 0,
                        dayOfWeek = -1,
                        modeId = 4,
                        modeName = "Lecture",
                        iconName = "ic_lib_book"
                    ),
                    AssignmentForDay(
                        assignmentId = 0,
                        dayOfWeek = 3,
                        modeId = 3,
                        modeName = "Vacances",
                        iconName = "ic_lib_calendar"
                    )
                ),
                4 to listOf(
                ),
                5 to listOf(
                    AssignmentForDay(
                        assignmentId = 0,
                        dayOfWeek = -1,
                        modeId = 4,
                        modeName = "Lecture",
                        iconName = "ic_lib_book"
                    ),
                    AssignmentForDay(
                        assignmentId = 0,
                        dayOfWeek = 5,
                        modeId = 5,
                        modeName = "Sport",
                        iconName = "ic_lib_calendar"
                    )
                ),
                6 to listOf(
                    AssignmentForDay(
                        assignmentId = 0,
                        dayOfWeek = -1,
                        modeId = 4,
                        modeName = "Lecture",
                        iconName = "ic_lib_book"
                    ),
                ),
                7 to listOf(
                    AssignmentForDay(
                        assignmentId = 0,
                        dayOfWeek = -1,
                        modeId = 4,
                        modeName = "Lecture",
                        iconName = "ic_lib_book"
                    ),
                    AssignmentForDay(
                        assignmentId = 0,
                        dayOfWeek = 7,
                        modeId = 2,
                        modeName = "Santé",
                        iconName = "ic_lib_calendar"
                    )
                )
            ), listOf(), { _, _ -> }, { _, _ -> }, { _, _ -> }, {}, {}, onBack = {})
    }
}