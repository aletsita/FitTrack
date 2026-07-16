package com.example.proyecto.wear.presentation.communication

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.proyecto.wear.presentation.data.model.Entrenamiento
import com.example.proyecto.wear.presentation.data.model.WorkoutState
import com.example.proyecto.wear.presentation.service.HeartRateSimulator
import com.example.proyecto.wear.presentation.service.RestTimer
import com.example.proyecto.wear.presentation.service.WorkoutTimer
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class WearListenerService : WearableListenerService() {

    companion object {
        private const val TAG = "WearListenerService"
    }

    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onMessageReceived(messageEvent: MessageEvent) {
        super.onMessageReceived(messageEvent)

        val path = messageEvent.path
        val message = messageEvent.data.toString(Charsets.UTF_8)

        Log.d(TAG, "Mensaje recibido. Ruta: $path, contenido: $message")

        mainHandler.post {
            processMessage(path, message)
        }
    }

    private fun processMessage(
        path: String,
        message: String
    ) {
        when (path) {

            WearConstants.PATH_CONNECTION -> {
                WorkoutState.setConnected(true)
            }

            WearConstants.PATH_WORKOUT_READY -> {
                val workout = parseWorkout(message)
                WorkoutState.prepareWorkout(workout)
            }

            WearConstants.PATH_START_WORKOUT -> {
                WorkoutState.startWorkout()
                WorkoutTimer.start()
                HeartRateSimulator.start()
            }

            WearConstants.PATH_PAUSE_WORKOUT -> {
                WorkoutTimer.pause()
                WorkoutState.pauseWorkout()
            }

            WearConstants.PATH_RESUME_WORKOUT -> {
                WorkoutTimer.resume()
                WorkoutState.resumeWorkout()
            }

            WearConstants.PATH_REST -> {
                WorkoutTimer.pause()
                WorkoutState.startRest(45)
                RestTimer.start(45)
            }

            WearConstants.PATH_NEXT_EXERCISE -> {
                val updatedWorkout = parseWorkout(message)
                WorkoutState.updateWorkout(updatedWorkout)
                WorkoutState.startWorkout()
            }

            WearConstants.PATH_UPDATE_WORKOUT -> {
                WorkoutState.updateWorkout(parseWorkout(message))
            }

            WearConstants.PATH_FINISH_WORKOUT -> {
                WorkoutTimer.pause()
                RestTimer.stop()
                HeartRateSimulator.stop()
                WorkoutState.finishWorkout()
            }

            else -> {
                Log.w(TAG, "Ruta desconocida: $path")
            }
        }
    }

    /*
     * Formato esperado:
     *
     * Push Day|Press de banca|Press militar|2|4|10|6|45
     */
    private fun parseWorkout(message: String): Entrenamiento {
        if (message.isBlank()) {
            return Entrenamiento()
        }

        return try {
            val values = message.split("|")

            Entrenamiento(
                nombreRutina = values.getOrNull(0) ?: "Entrenamiento",
                ejercicioActual = values.getOrNull(1) ?: "Ejercicio",
                siguienteEjercicio = values.getOrNull(2) ?: "Siguiente ejercicio",
                serieActual = values.getOrNull(3)?.toIntOrNull() ?: 1,
                totalSeries = values.getOrNull(4)?.toIntOrNull() ?: 4,
                repeticiones = values.getOrNull(5)?.toIntOrNull() ?: 10,
                totalEjercicios = values.getOrNull(6)?.toIntOrNull() ?: 1,
                duracionEstimadaMinutos =
                    values.getOrNull(7)?.toIntOrNull() ?: 45
            )
        } catch (exception: Exception) {
            Log.e(TAG, "No se pudo interpretar el entrenamiento.", exception)
            Entrenamiento()
        }
    }
}