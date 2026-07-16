package com.example.proyecto.wear.presentation.history

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.Text
import com.example.proyecto.wear.presentation.components.FitTrackScreenContainer

@Composable
fun WorkoutHistoryScreen(
    onBack: () -> Unit
) {
    FitTrackScreenContainer {

        Text(
            text = "HISTORIAL",
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "✓ Push Day · 52 min")
        Text(text = "✓ Pull Day · 48 min")
        Text(text = "✓ Leg Day · 61 min")

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = onBack) {
            Text(text = "Volver")
        }
    }
}