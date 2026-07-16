package com.example.proyecto.wear.presentation.finished

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.Text
import com.example.proyecto.wear.presentation.components.FitTrackScreenContainer
import com.example.proyecto.wear.presentation.utils.TimeUtils

@Composable
fun WorkoutFinishedScreen(
    workoutName: String,
    elapsedSeconds: Long,
    exercises: Int,
    heartRate: Int,
    calories: Int,
    onAccept: () -> Unit
) {
    FitTrackScreenContainer {

        Text(
            text = "✓",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "COMPLETADO",
            fontWeight = FontWeight.Bold
        )

        Text(text = workoutName)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = TimeUtils.formatSeconds(elapsedSeconds))
        Text(text = "$exercises ejercicios")
        Text(text = "♥ $heartRate BPM")
        Text(text = "$calories kcal")

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onAccept) {
            Text(text = "Aceptar")
        }
    }
}