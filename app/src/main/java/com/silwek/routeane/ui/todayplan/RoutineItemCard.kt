package com.silwek.routeane.ui.todayplan


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silwek.routeane.R
import com.silwek.routeane.data.entities.RoutineDayAssignmentWithItem
import com.silwek.routeane.ui.components.getIconResource
import com.silwek.routeane.ui.theme.RouteaneTheme

@Composable
fun RoutineItemCard(assignment: RoutineDayAssignmentWithItem, modifier: Modifier = Modifier) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.padding(4.dp))
            Icon(
                painter = painterResource(
                    id = getIconResource(assignment.itemIconName)
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Column(
                Modifier
                    .padding(vertical = 16.dp)
                    .padding(end = 16.dp)
                    .weight(1f)
            ) {
                Text(assignment.itemName, style = MaterialTheme.typography.titleMedium)
                assignment.itemDefaultDuration?.let {
                    Text(
                        stringResource(R.string.routine_duration_min, it),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoutineItemCardPreview() {
    val fakeAssignment = RoutineDayAssignmentWithItem(3, 1, 1, 3, "Yoga", 20, "ic_lib_calendar")
    RouteaneTheme {
        RoutineItemCard(fakeAssignment)
    }
}