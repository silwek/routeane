package com.silwek.routeane.ui.mode

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.silwek.routeane.R
import com.silwek.routeane.data.entities.RoutineMode
import com.silwek.routeane.ui.components.DialogTitle

@Composable
fun ConfirmDeleteDialog(
    mode: RoutineMode,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text(stringResource(R.string.delete_mode_confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(stringResource(R.string.cancel))
            }
        },
        title = { DialogTitle(stringResource(R.string.delete_mode_title, mode.name)) },
        text = { Text(stringResource(R.string.delete_mode_warning)) }
    )
}
