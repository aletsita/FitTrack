package com.example.proyecto.wear.presentation.finished

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.Text
import com.example.proyecto.wear.presentation.components.AccentRing
import com.example.proyecto.wear.presentation.components.FitTrackScreenContainer
import com.example.proyecto.wear.presentation.components.SectionLabel
import com.example.proyecto.wear.presentation.theme.FitTrackGreen
import com.example.proyecto.wear.presentation.theme.FitTrackRed
import com.example.proyecto.wear.presentation.theme.FitTrackTextSecondary
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

        AccentRing(progress = 1f, color = FitTrackGreen, size = 90.dp, strokeWidth = 5.dp) {
            Text(
                text = "✓",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = FitTrackGreen
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        SectionLabel(text = "Completado", color = FitTrackGreen)

        Text(
            text = workoutName,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = TimeUtils.formatSeconds(elapsedSeconds), fontSize = 11.sp, color = FitTrackTextSecondary)
        Text(text = "$exercises ejercicios", fontSize = 11.sp, color = FitTrackTextSecondary)
        Text(text = "♥ $heartRate BPM", fontSize = 11.sp, color = FitTrackRed)
        Text(text = "$calories kcal", fontSize = 11.sp, color = FitTrackTextSecondary)

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onAccept,
            colors = ButtonDefaults.buttonColors(
                containerColor = FitTrackGreen,
                contentColor = Color.Black
            )
        ) {
            Text(text = "Aceptar", fontWeight = FontWeight.Bold)
        }
    }
}
