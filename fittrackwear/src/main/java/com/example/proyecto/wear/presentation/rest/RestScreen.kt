package com.example.proyecto.wear.presentation.rest

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
fun RestScreen(
    restSeconds: Int,
    nextExercise: String,
    heartRate: Int,
    onSkipRest: () -> Unit
) {
    FitTrackScreenContainer {

        Text(
            text = "DESCANSO",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = TimeUtils.formatRestSeconds(restSeconds),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Siguiente")

        Text(
            text = nextExercise,
            fontWeight = FontWeight.Bold
        )

        Text(text = "♥ $heartRate BPM")

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onSkipRest) {
            Text(text = "Omitir")
        }
    }
}