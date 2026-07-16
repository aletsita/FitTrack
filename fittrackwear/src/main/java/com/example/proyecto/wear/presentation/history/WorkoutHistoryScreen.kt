package com.example.proyecto.wear.presentation.history

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.Text
import com.example.proyecto.wear.presentation.components.FitTrackScreenContainer
import com.example.proyecto.wear.presentation.components.SectionLabel
import com.example.proyecto.wear.presentation.theme.FitTrackGreen
import com.example.proyecto.wear.presentation.theme.FitTrackSurface
import com.example.proyecto.wear.presentation.theme.FitTrackTextSecondary

@Composable
fun WorkoutHistoryScreen(
    onBack: () -> Unit
) {
    FitTrackScreenContainer {

        SectionLabel(text = "Historial", color = FitTrackGreen)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "✓ Push Day · 52 min", color = Color.White, fontSize = 12.sp)
        Text(text = "✓ Pull Day · 48 min", color = FitTrackTextSecondary, fontSize = 12.sp)
        Text(text = "✓ Leg Day · 61 min", color = FitTrackTextSecondary, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = FitTrackSurface,
                contentColor = Color.White
            )
        ) {
            Text(text = "Volver")
        }
    }
}
