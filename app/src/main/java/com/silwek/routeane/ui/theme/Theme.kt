package com.silwek.routeane.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = PrimaryDark,
    primaryContainer = PrimaryLight,
    onPrimary = Color.White,
    onPrimaryContainer = SecondaryDark,
    secondary = SecondaryDark,
    onSecondary = Color.White,
    background = BackgroundLight,
    onBackground = Color.Black,
)

private val DarkColors = darkColorScheme(
    primary = PrimaryLight,
    primaryContainer = PrimaryDark,
    onPrimary = Color.Black,
    onPrimaryContainer = Color.White,
    secondary = SecondaryLight,
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
)


val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(0.dp)
)

val Typography = Typography(
    bodyLarge = androidx.compose.ui.text.TextStyle(
        fontSize = 16.sp
    ),
    titleLarge = androidx.compose.ui.text.TextStyle(
        fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
    ),
    titleMedium = androidx.compose.ui.text.TextStyle(
        fontSize = 18.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
    ),
    labelSmall = androidx.compose.ui.text.TextStyle(
        fontSize = 12.sp
    )
)

@Composable
fun RouteaneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun SystemBarColorEffect() {
    val view = LocalView.current
    val color = MaterialTheme.colorScheme.background

    SideEffect {
        val window = (view.context as? Activity)?.window ?: return@SideEffect
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
            color.luminance() > 0.5
    }
}