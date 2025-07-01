package com.silwek.routeane.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silwek.routeane.data.entities.RoutineMode
import com.silwek.routeane.ui.theme.Dimens
import com.silwek.routeane.ui.theme.RouteaneTheme


@Composable
fun ModeChipsSelector(
    modes: List<RoutineMode>,
    selectedMode: RoutineMode,
    onModeSelected: (RoutineMode) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = Dimens.ScreenHorizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(modes) { mode ->
            val textColor = if (mode.id == selectedMode.id)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.onSecondary
            AssistChip(
                onClick = { onModeSelected(mode) },
                label = { Text(mode.name) },
                leadingIcon = {
                    if (mode.iconName == RouteaneIcons.EMPTY_ICON) {
                        null
                    } else {
                        Icon(
                            painter = painterResource(
                                id = getIconResource(mode.iconName)
                            ),
                            tint = textColor,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp).padding(horizontal = 2.dp, vertical = 4.dp)
                        )
                    }
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (mode.id == selectedMode.id)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.secondary,
                    labelColor = textColor,
                ),
                border = BorderStroke(0.dp, Color.Transparent)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModeChipSelectorPreview() {
    RouteaneTheme {
        var selectedMode by remember { mutableStateOf(RoutineMode(1, "Default")) }
        val modes = listOf(
            RoutineMode(1, "Default"),
            RoutineMode(2, "Holidays", iconName = "ic_lib_calendar"),
            RoutineMode(3, "Sick"),
            RoutineMode(4, "Energy+"),
            RoutineMode(5, "Stressed"),
            RoutineMode(6, "Minimal")
        )
        ModeChipsSelector(
            modes = modes,
            selectedMode = selectedMode,
            onModeSelected = { selectedMode = it }
        )
    }
}