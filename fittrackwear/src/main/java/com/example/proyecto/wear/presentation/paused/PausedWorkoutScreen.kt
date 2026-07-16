package com.example.proyecto.wear.presentation.paused

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
fun PausedWorkoutScreen(
    elapsedSeconds: Long,
    onResume: () -> Unit,
    onFinish: () -> Unit
) {
    FitTrackScreenContainer {

        Text(
            text = "ENTRENAMIENTO",
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "PAUSADO",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = TimeUtils.formatSeconds(elapsedSeconds),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = onResume) {
            Text(text = "Reanudar")
        }

        Spacer(modifier = Modifier.height(6.dp))

        Button(onClick = onFinish) {
            Text(text = "Finalizar")
        }
    }
}