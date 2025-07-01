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
import androidx.compose.ui.unit.dp
import com.silwek.routeane.R
import com.silwek.routeane.data.entities.RoutineMode
import com.silwek.routeane.ui.components.IconPickerDialog
import com.silwek.routeane.ui.components.RouteaneIcons


@Composable
fun ModesScreen(viewModel: ModesViewModel) {
    val modes by viewModel.modes.collectAsState()
    var editingModeId by remember { mutableStateOf<Int?>(null) }
    var editingText by remember { mutableStateOf("") }
    var modeToDelete by remember { mutableStateOf<RoutineMode?>(null) }
    var showPicker by remember { mutableStateOf(false) }
    var modeBeingEdited by remember { mutableStateOf<RoutineMode?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }

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
                                modeToDelete = mode
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
                            isEditing = (editingModeId == mode.id),
                            editingText = editingText,
                            onEditingTextChange = { editingText = it },
                            onClickEdit = {
                                editingModeId = mode.id
                                editingText = mode.name
                            },
                            onClickIconEdit = {
                                modeBeingEdited = mode
                                showPicker = true
                            },
                            onSave = {
                                viewModel.updateModeName(mode.id, editingText)
                                editingModeId = null
                            },
                            onDeleteClicked = { modeToDelete = mode }

                        )
                    }
                }
            }

            if (modeToDelete != null) {
                ConfirmDeleteDialog(
                    mode = modeToDelete!!,
                    onConfirm = {
                        viewModel.deleteMode(modeToDelete!!)
                        modeToDelete = null
                    },
                    onDismiss = { modeToDelete = null }
                )
            }
            if (showPicker && modeBeingEdited != null) {
                IconPickerDialog(
                    icons = RouteaneIcons.allIcons,
                    onIconSelected = { selectedIcon ->
                        viewModel.updateModeIcon(modeBeingEdited!!.id, selectedIcon)
                    },
                    onDismiss = {
                        showPicker = false
                        modeBeingEdited = null
                    }
                )
            }
            if (showAddDialog) {
                AddModeDialog(
                    onSubmit = { mode ->
                        viewModel.insertMode(mode)
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