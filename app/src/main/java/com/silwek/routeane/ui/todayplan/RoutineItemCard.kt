package com.silwek.routeane.ui.todayplan


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silwek.routeane.data.entities.RoutineDayAssignmentWithItem
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
        Column(
            Modifier
                .padding(16.dp)
        ) {
            Text(assignment.itemName, style = MaterialTheme.typography.titleMedium)
            assignment.itemDefaultDuration?.let {
                Text("Duration: $it min", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoutineItemCardPreview() {
    val fakeAssignment = RoutineDayAssignmentWithItem(3, 1, 1, 3, "Yoga", 20)
    RouteaneTheme {
        RoutineItemCard(fakeAssignment)
    }
}