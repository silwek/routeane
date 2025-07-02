package com.silwek.routeane.ui.mode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
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
import com.silwek.routeane.data.entities.RoutineMode
import com.silwek.routeane.ui.components.IconPickerDialog
import com.silwek.routeane.ui.components.RouteaneIcons
import com.silwek.routeane.ui.theme.RouteaneTheme


@Composable
fun ModesScreen(
    modes: List<RoutineMode>,
    currentMode: RoutineMode?,
    onAddMode: (RoutineMode) -> Unit,
    onEditMode: (RoutineMode?) -> Unit,
    onUpdateName: (String) -> Unit,
    onUpdateIcon: (String) -> Unit,
    onDeleteConfirm: () -> Unit
) {
    var editingText by remember { mutableStateOf("") }
    var showDelete by remember { mutableStateOf(false) }
    var showPicker by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    val isEditMode = (currentMode != null)
    val isEditingText = isEditMode && !showPicker

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_mode_action)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Card(
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    stringResource(R.string.mode_explanation_1),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                )
                Text(
                    stringResource(R.string.mode_explanation_2),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp)
                )
            }

            Spacer(Modifier.height(16.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(modes) { mode ->

                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = { value ->
                            if (value == SwipeToDismissBoxValue.EndToStart || value == SwipeToDismissBoxValue.StartToEnd) {
                                onEditMode(mode)
                                showDelete = true
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
                                modifier = Modifier.fillMaxWidth()
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
                                            R.string.delete_mode_action,
                                            mode.name
                                        ),
                                        tint = Color.White,
                                        modifier = Modifier.align(Alignment.CenterEnd)
                                    )
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        ModeItemCard(
                            mode = mode,
                            isEditing = isEditingText,
                            editingText = editingText,
                            onEditingTextChange = { editingText = it },
                            onClickEdit = {
                                onEditMode(mode)
                                editingText = mode.name
                            },
                            onClickIconEdit = {
                                onEditMode(mode)
                                showPicker = true
                            },
                            onSave = {
                                onUpdateName(editingText)
                            },
                            onDeleteClicked = {
                                onEditMode(mode)
                                showDelete = true
                            }
                        )
                    }
                }
            }

            if (showDelete && isEditMode) {
                ConfirmDeleteDialog(
                    mode = currentMode,
                    onConfirm = {
                        onDeleteConfirm()
                        showDelete = false
                    },
                    onDismiss = {
                        onEditMode(null)
                        showDelete = false
                    }
                )
            }
            if (showPicker && isEditMode) {
                IconPickerDialog(
                    icons = RouteaneIcons.allIcons,
                    onIconSelected = { selectedIcon ->
                        onUpdateIcon(selectedIcon)
                    },
                    onDismiss = {
                        showPicker = false
                        onEditMode(null)
                    }
                )
            }
            if (showAddDialog) {
                AddModeDialog(
                    onSubmit = { mode ->
                        onAddMode(mode)
                        showAddDialog = false
                    },
                    onDismiss = {
                        showAddDialog = false
                    }
                )
            }
        }
    }
}

@Composable
fun ModesScreenWithViewModel(viewModel: ModesViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val modes = uiState.modes
    val modeBeingEdited = uiState.modeBeingEdited
    val isEditMode = (modeBeingEdited != null)
    val modeId = modeBeingEdited?.id ?: -1

    ModesScreen(
        modes = modes,
        currentMode = modeBeingEdited,
        onAddMode = { mode ->
            viewModel.insertMode(mode)
        },
        onEditMode = { mode ->
            viewModel.onUpdatingMode(mode)
        },
        onUpdateName = { name ->
            if (isEditMode)
                viewModel.updateModeName(modeId, name)
            viewModel.onUpdatingMode(null)
        },
        onUpdateIcon = { selectedIcon ->
            if (isEditMode)
                viewModel.updateModeIcon(modeId, selectedIcon)
            viewModel.onUpdatingMode(null)
        },
        onDeleteConfirm = {
            if (isEditMode)
                viewModel.deleteMode(modeBeingEdited)
        }
    )
}


@Preview(showBackground = true)
@Composable
fun ModesScreenPreview() {
    val modes = listOf(
        RoutineMode(1, "Routine", "ic_lib_calendar"),
        RoutineMode(2, "Holidays", "ic_lib_swimming"),
        RoutineMode(3, "Sick days", "ic_lib_none")
    )
    RouteaneTheme {
        ModesScreen(modes, null, {}, {}, { }, { }, {})
    }
}