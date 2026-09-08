package com.example.nomeescucha.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color // ¡Importante para Color(0xFF...)!

private val LightColors = lightColorScheme(
    // 🎯 Reemplaza PastelPink por un tono morado/azul más estándar o profundo:
    primary = Color(0xFF007BFF), // <-- Color primario por defecto de Material 3 (Tono morado)
    // O puedes usar un azul profundo estándar si lo prefieres:
    // primary = Color(0xFF007BFF), // Ejemplo de azul más tradicional

    secondary = PastelPurple,
    tertiary = PastelYellow,

    background = Color(0xFFFFE4EC),
    surface = Color(0xFFFFF1F4),

    onPrimary = TextDark,
    onSecondary = TextDark,
    onBackground = TextDark,
    onSurface = TextDark
)

private val DarkColors = darkColorScheme(
    primary = PastelPink,
    secondary = PastelPurple,
    tertiary = PastelYellow,

    background = Color(0xFF1C1C1C),
    surface = Color(0xFF2A2A2A),

    onPrimary = TextLight,
    onSecondary = TextLight,
    onBackground = TextLight,
    onSurface = TextLight
)

@Composable
fun NomeescuchaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = Shapes,
        content = content
    )
}





