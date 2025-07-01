package com.silwek.routeane.ui.components

import android.content.res.Resources
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silwek.routeane.R
import com.silwek.routeane.ui.theme.RouteaneTheme

class RouteaneIcons {
    companion object {
        const val EMPTY_ICON = "ic_lib_none"
        val allIcons = listOf(
            EMPTY_ICON,
            "ic_lib_baby_milk_bottle",
            "ic_lib_book",
            "ic_lib_bowl_with_chopsticks",
            "ic_lib_broom",
            "ic_lib_bucket",
            "ic_lib_calendar",
            "ic_lib_car",
            "ic_lib_cardboard_box",
            "ic_lib_cat_paw",
            "ic_lib_fork_and_spoon",
            "ic_lib_garbage_bags",
            "ic_lib_garden_fence",
            "ic_lib_hand_wash",
            "ic_lib_health",
            "ic_lib_leaf",
            "ic_lib_music",
            "ic_lib_paint_palette",
            "ic_lib_pencil",
            "ic_lib_refrigerator",
            "ic_lib_roast_chicken",
            "ic_lib_scales",
            "ic_lib_scoop",
            "ic_lib_shopping_cart",
            "ic_lib_shower",
            "ic_lib_spray",
            "ic_lib_stethoscope",
            "ic_lib_sunflower",
            "ic_lib_swimming",
            "ic_lib_syringe",
            "ic_lib_toothbrush",
            "ic_lib_vacuum_cleaner",
            "ic_lib_volleyball",
            "ic_lib_water_drop",
        )
    }
}

@Composable
fun getIconResource(name: String): Int {
    val context = LocalContext.current
    return remember(name) {
        try {
            context.resources.getIdentifier(name, "drawable", context.packageName)
        } catch (e: Resources.NotFoundException) {
            R.drawable.ic_lib_none
        }
    }
}

@Composable
fun IconPickerDialog(
    icons: List<String>,
    onIconSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = { Text(stringResource(R.string.icon_picker_title)) },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(icons) { iconName ->
                    Icon(
                        painter = painterResource(getIconResource(iconName)),
                        contentDescription = iconName,
                        modifier = Modifier
                            .size(48.dp)
                            .clickable {
                                onIconSelected(iconName)
                                onDismiss()
                            }
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun IconPickerDialogPreview() {
    val icons = RouteaneIcons.allIcons
    RouteaneTheme {
        IconPickerDialog(
            icons = icons,
            onIconSelected = {},
            onDismiss = {}
        )
    }
}