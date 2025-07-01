package com.silwek.routeane.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.silwek.routeane.ui.theme.RouteaneTheme

@Composable
fun DialogTitle(title: String) {
    Text(text = title, color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.titleLarge)
}

@Preview(showBackground = true)
@Composable
fun DialogTitlePreview() {
    RouteaneTheme {
        DialogTitle("Test title")
    }
}