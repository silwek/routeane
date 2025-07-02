package com.silwek.routeane.ui.routines

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import com.silwek.routeane.ui.theme.RouteaneTheme

@Composable
fun RoutineItemConfigCard(
    routine: RoutineItem,
    modifier: Modifier,
    onAddAssignment: () -> Unit = {},
    onEditClicked: () -> Unit = {},
) {

    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 8.dp)) {
            Column(
                Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(routine.name, style = MaterialTheme.typography.titleMedium)
                routine.defaultDurationMinutes?.let {
                    Text("Duration: $it min", style = MaterialTheme.typography.bodyMedium)
                }
            }

            IconButton(onClick = onAddAssignment) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.schedule_add__icon
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(horizontal = 2.dp, vertical = 4.dp)
                )
            }
            IconButton(onClick = onEditClicked) {
                Icon(
                    Icons.Default.Edit,
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
    val fakeItem = RoutineItem(1, "Yoga", 20)
    RouteaneTheme {
        RoutineItemConfigCard(fakeItem, Modifier, onAddAssignment = {})
    }
}