package com.silwek.routeane.ui.routinedetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.silwek.routeane.R
import com.silwek.routeane.ui.components.getIconResource
import com.silwek.routeane.ui.theme.RouteaneTheme

@Composable
fun ChipMode(
    modeName: String,
    iconName: String,
    onClick: (() -> Unit)? = null,
    onWantDelete: (() -> Unit)? = null
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier
            .padding(4.dp)
            .clickable(enabled = onClick != null, onClick = { onClick?.invoke() })
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            if (iconName != "ic_lib_none") {
                Icon(
                    painter = painterResource(id = getIconResource(iconName)),
                    contentDescription = modeName,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(4.dp))
            }
            Text(
                text = modeName,
                style = MaterialTheme.typography.bodySmall
            )
            if (onWantDelete != null)
                IconButton({ onWantDelete.invoke() }) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = stringResource(R.string.delete_mode_assignement),
                        modifier = Modifier.size(18.dp)
                    )
                }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChipModePreview() {
    RouteaneTheme {
        ChipMode("Travail", "ic_lib_calendar", {}, {})
    }
}