package com.silwek.routeane.ui.mode

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silwek.routeane.R
import com.silwek.routeane.data.entities.RoutineMode
import com.silwek.routeane.ui.components.getIconResource
import com.silwek.routeane.ui.theme.RouteaneTheme

@Composable
fun ModeItemCard(
    mode: RoutineMode,
    isEditing: Boolean,
    editingText: String,
    onEditingTextChange: (String) -> Unit,
    onClickEdit: () -> Unit,
    onClickIconEdit: () -> Unit,
    onDeleteClicked: () -> Unit,
    onSave: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isEditing) {
                TextField(
                    value = editingText,
                    onValueChange = onEditingTextChange,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        Row {
                            if (isEditing) {
                                IconButton(onClick = {
                                    onSave()
                                    focusManager.clearFocus()
                                }) {
                                    Icon(
                                        Icons.Default.Check,
                                        contentDescription = stringResource(R.string.validate)
                                    )
                                }
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onSave()
                            focusManager.clearFocus()
                        }
                    )
                )
            } else {
                IconButton(onClick = onClickIconEdit) {
                    Icon(
                        painter = painterResource(
                            id = getIconResource(mode.iconName)
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(horizontal = 2.dp, vertical = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.size(4.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        mode.name,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onClickEdit)
                    )
                }
                IconButton(onClick = onDeleteClicked) {
                    Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete_mode_confirm))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ModeItemCardPreview() {
    RouteaneTheme {
        RouteaneTheme {
            ModeItemCard(
                mode = RoutineMode(id = 0, name = "Test mode", "ic_lib_calendar"),
                isEditing = false,
                editingText = "",
                onEditingTextChange = {},
                onClickEdit = {},
                onClickIconEdit = {},
                onSave = {},
                onDeleteClicked = {}
            )
        }
    }
}