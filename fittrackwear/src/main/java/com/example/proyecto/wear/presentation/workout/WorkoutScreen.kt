package com.example.proyecto.wear.presentation.workout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.Text
import com.example.proyecto.wear.presentation.data.model.Entrenamiento
import com.example.proyecto.wear.presentation.components.FitTrackScreenContainer
import com.example.proyecto.wear.presentation.utils.TimeUtils

@Composable
fun WorkoutScreen(
    workout: Entrenamiento,
    elapsedSeconds: Long,
    heartRate: Int,
    onPause: () -> Unit,
    onRest: () -> Unit,
    onFinish: () -> Unit
) {
    FitTrackScreenContainer {

        Text(
            text = workout.ejercicioActual.uppercase(),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Serie ${workout.serieActual}/${workout.totalSeries}"
        )

        Text(
            text = "${workout.repeticiones} REP",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = TimeUtils.formatSeconds(elapsedSeconds),
            fontSize = 20.sp
        )

        Text(text = "♥ $heartRate BPM")

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Button(onClick = onPause) {
                Text(text = "Ⅱ")
            }

            Spacer(modifier = Modifier.width(6.dp))

            Button(onClick = onRest) {
                Text(text = "✓")
            }

            Spacer(modifier = Modifier.width(6.dp))

            Button(onClick = onFinish) {
                Text(text = "■")
            }
        }
    }
}