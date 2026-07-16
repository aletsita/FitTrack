package com.example.proyecto.wear.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material3.MaterialTheme

// Paleta FitTrack — inspirada en las pantallas del smartwatch del documento del proyecto
val FitTrackBackground = Color(0xFF000000)
val FitTrackSurface = Color(0xFF19191D)
val FitTrackGreen = Color(0xFFC6FF3B)   // rutina / acciones principales
val FitTrackOrange = Color(0xFFFF7A45)  // cronómetro / entrenamiento activo
val FitTrackBlue = Color(0xFF4FA8FF)    // descanso
val FitTrackRed = Color(0xFFFF3B57)     // frecuencia cardiaca
val FitTrackTextSecondary = Color(0xFF9A9AA5)
val FitTrackTrack = Color(0xFF232328)   // fondo del anillo (track)

@Composable
fun ProyectoTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme.copy(
        primary = FitTrackGreen,
        onPrimary = Color.Black,
        secondary = FitTrackOrange,
        onSecondary = Color.Black,
        tertiary = FitTrackBlue,
        onTertiary = Color.Black,
        error = FitTrackRed,
        onError = Color.Black,
        background = FitTrackBackground,
        onBackground = Color.White,
        onSurface = Color.White
    )

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
