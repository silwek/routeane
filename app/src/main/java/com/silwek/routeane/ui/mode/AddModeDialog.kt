package com.silwek.routeane.ui.mode

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.silwek.routeane.R
import com.silwek.routeane.data.entities.RoutineMode
import com.silwek.routeane.ui.components.DialogTitle

@Composable
fun AddModeDialog(
    onSubmit: (mode: RoutineMode) -> Unit,
    onDismiss: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(false) }
    if (text.isNotBlank()) isValid = true
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                DialogTitle(stringResource(R.string.add_mode_title))
                Text(text = stringResource(R.string.add_mode_desc), style = MaterialTheme.typography.bodyMedium)
            }
        },
        text = {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(stringResource(R.string.add_mode_placeholder)) }
            )
        },
        confirmButton = {
            TextButton(
                enabled = isValid,
                onClick = {
                    onSubmit(RoutineMode(name = text))
                },
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(stringResource(R.string.add_mode_confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}