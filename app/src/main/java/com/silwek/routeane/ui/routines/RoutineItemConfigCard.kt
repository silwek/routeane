package com.silwek.routeane.ui.routines

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silwek.routeane.R
import com.silwek.routeane.data.entities.RoutineItem
import com.silwek.routeane.ui.components.getIconResource
import com.silwek.routeane.ui.theme.RouteaneTheme

@Composable
fun RoutineItemConfigCard(
    routine: RoutineItem,
    modifier: Modifier,
    onClick: () -> Unit = {},
    onWantToDelete: () -> Unit,
) {

    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = true, onClick = onClick)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Spacer(modifier = Modifier.padding(4.dp))
            Icon(
                painter = painterResource(
                    id = getIconResource(routine.iconName)
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Column(
                Modifier
                    .padding(vertical = 16.dp)
                    .weight(1f)
            ) {
                Text(routine.name, style = MaterialTheme.typography.titleMedium)
                routine.defaultDurationMinutes?.let {
                    Text(
                        stringResource(R.string.routine_duration_min, it),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            IconButton(onClick = onWantToDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_mode_confirm),
                    modifier = Modifier
                        .size(32.dp)
                        .padding(horizontal = 2.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoutineItemConfigCardPreview() {
    val fakeItem = RoutineItem(1, "Yoga", 20, iconName = "ic_lib_calendar")
    RouteaneTheme {
        RoutineItemConfigCard(
            fakeItem,
            Modifier,
            onClick = {},
            onWantToDelete = {})
    }
}