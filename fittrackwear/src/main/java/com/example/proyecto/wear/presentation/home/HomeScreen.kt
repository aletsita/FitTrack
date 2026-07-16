package com.example.proyecto.wear.presentation.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.Text
import com.example.proyecto.wear.presentation.data.model.WorkoutState
import com.example.proyecto.wear.presentation.components.FitTrackScreenContainer

@Composable
fun HomeScreen(
    onOpenHistory: () -> Unit,
    onDemoWorkout: () -> Unit
) {
    FitTrackScreenContainer {

        Text(
            text = "FITTRACK",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (WorkoutState.phoneConnected) {
                "● Teléfono conectado"
            } else {
                "○ Esperando teléfono"
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Sin entrenamiento activo")

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onDemoWorkout) {
            Text(text = "Probar")
        }

        Spacer(modifier = Modifier.height(6.dp))

        Button(onClick = onOpenHistory) {
            Text(text = "Historial")
        }
    }
}