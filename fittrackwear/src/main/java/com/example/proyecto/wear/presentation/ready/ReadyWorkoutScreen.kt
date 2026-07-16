package com.example.proyecto.wear.presentation.ready

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.Text
import com.example.proyecto.wear.presentation.data.model.Entrenamiento
import com.example.proyecto.wear.presentation.components.FitTrackScreenContainer

@Composable
fun ReadyWorkoutScreen(
    workout: Entrenamiento,
    onStartWorkout: () -> Unit,
    onCancel: () -> Unit
) {
    FitTrackScreenContainer {

        Text(
            text = workout.nombreRutina.uppercase(),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "${workout.duracionEstimadaMinutos} min")
        Text(text = "${workout.totalEjercicios} ejercicios")

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onStartWorkout) {
            Text(text = "Empezar")
        }

        Spacer(modifier = Modifier.height(6.dp))

        Button(onClick = onCancel) {
            Text(text = "Cancelar")
        }
    }
}