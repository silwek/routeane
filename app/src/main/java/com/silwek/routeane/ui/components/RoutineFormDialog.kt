package com.silwek.routeane.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.silwek.routeane.R

@Composable
fun RoutineFormDialog(
    initialName: String? = null,
    initialDuration: Int = 5,
    onDismiss: () -> Unit,
    onConfirm: (name: String, duration: Int) -> Unit,
    onDelete: (() -> Unit)? = null
) {
    val isEditMode = initialName != null
    var name by remember { mutableStateOf(initialName ?: "") }
    var duration by remember { mutableIntStateOf(initialDuration) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nouvelle routine") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nom") },
                    modifier = Modifier.focusRequester(focusRequester)
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = duration.toString(),
                    onValueChange = { duration = it.toIntOrNull() ?: 0 },
                    label = { Text("Durée (min)") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(name, duration) }) {
                Text(if (isEditMode) "Editer" else "Valider")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}