package com.example.proyecto.wear.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.proyecto.wear.presentation.communication.PhoneMessageSender
import com.example.proyecto.wear.presentation.communication.WearConstants
import com.example.proyecto.wear.presentation.data.model.WorkoutScreen
import com.example.proyecto.wear.presentation.data.model.WorkoutState
import com.example.proyecto.wear.presentation.finished.WorkoutFinishedScreen
import com.example.proyecto.wear.presentation.history.WorkoutHistoryScreen
import com.example.proyecto.wear.presentation.home.HomeScreen
import com.example.proyecto.wear.presentation.data.model.RoutineCatalog
import com.example.proyecto.wear.presentation.paused.PausedWorkoutScreen
import com.example.proyecto.wear.presentation.ready.ReadyWorkoutScreen
import com.example.proyecto.wear.presentation.rest.RestScreen
import com.example.proyecto.wear.presentation.routines.RoutineListScreen
import com.example.proyecto.wear.presentation.service.HeartRateSimulator
import com.example.proyecto.wear.presentation.service.RestTimer
import com.example.proyecto.wear.presentation.service.WorkoutTimer
import com.example.proyecto.wear.presentation.theme.ProyectoTheme
import com.example.proyecto.wear.presentation.workout.WorkoutScreen as ActiveWorkoutScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProyectoTheme {
                FitTrackWearApp()
            }
        }
    }

    @Composable
    private fun FitTrackWearApp() {
        when (WorkoutState.currentScreen) {

            WorkoutScreen.HOME -> {
                HomeScreen(
                    onOpenHistory = {
                        WorkoutState.showHistory()
                    },
                    onSeeRoutines = {
                        WorkoutState.showRoutineList()
                    }
                )
            }

            WorkoutScreen.ROUTINE_LIST -> {
                RoutineListScreen(
                    routines = RoutineCatalog.routines,
                    onSelectRoutine = { entrenamiento ->
                        WorkoutState.selectRoutine(entrenamiento)
                    },
                    onBack = {
                        WorkoutState.showHome()
                    }
                )
            }

            WorkoutScreen.READY -> {
                ReadyWorkoutScreen(
                    workout = WorkoutState.workout,
                    onStartWorkout = {
                        iniciarEntrenamiento()
                    },
                    onCancel = {
                        WorkoutState.showRoutineList()
                    }
                )
            }

            WorkoutScreen.ACTIVE -> {
                ActiveWorkoutScreen(
                    workout = WorkoutState.workout,
                    elapsedSeconds = WorkoutState.elapsedSeconds,
                    heartRate = WorkoutState.heartRate,
                    onPause = {
                        pausarEntrenamiento()
                    },
                    onFinish = {
                        finalizarEntrenamiento()
                    }
                )
            }

            WorkoutScreen.REST -> {
                RestScreen(
                    restSeconds = WorkoutState.restSeconds,
                    nextExercise = WorkoutState.workout.siguienteEjercicio,
                    heartRate = WorkoutState.heartRate,
                    onSkipRest = {
                        terminarDescanso()
                    }
                )
            }

            WorkoutScreen.PAUSED -> {
                PausedWorkoutScreen(
                    elapsedSeconds = WorkoutState.elapsedSeconds,
                    onResume = {
                        reanudarEntrenamiento()
                    },
                    onFinish = {
                        finalizarEntrenamiento()
                    }
                )
            }

            WorkoutScreen.FINISHED -> {
                WorkoutFinishedScreen(
                    workoutName = WorkoutState.workout.nombreRutina,
                    elapsedSeconds = WorkoutState.elapsedSeconds,
                    exercises = WorkoutState.workout.totalEjercicios,
                    heartRate = WorkoutState.heartRate,
                    calories = WorkoutState.calories,
                    onAccept = {
                        reiniciarAplicacion()
                    }
                )
            }

            WorkoutScreen.HISTORY -> {
                WorkoutHistoryScreen(
                    onBack = {
                        WorkoutState.showHome()
                    }
                )
            }
        }
    }

    private fun iniciarEntrenamiento() {
        WorkoutState.startWorkout()
        WorkoutTimer.start()
        HeartRateSimulator.start()

        enviarMensajeAlTelefono(
            path = WearConstants.PATH_START_WORKOUT,
            message = "START"
        )
    }

    private fun pausarEntrenamiento() {
        WorkoutTimer.pause()
        WorkoutState.pauseWorkout()

        enviarMensajeAlTelefono(
            path = WearConstants.PATH_PAUSE_WORKOUT,
            message = "PAUSE"
        )
    }

    private fun reanudarEntrenamiento() {
        WorkoutTimer.resume()
        WorkoutState.resumeWorkout()

        enviarMensajeAlTelefono(
            path = WearConstants.PATH_RESUME_WORKOUT,
            message = "RESUME"
        )
    }

    private fun terminarDescanso() {
        RestTimer.stop()
        WorkoutState.finishRest()
        WorkoutTimer.resume()

        enviarMensajeAlTelefono(
            path = WearConstants.PATH_RESUME_WORKOUT,
            message = "RESUME"
        )
    }

    private fun finalizarEntrenamiento() {
        WorkoutTimer.pause()
        RestTimer.stop()
        HeartRateSimulator.stop()
        WorkoutState.finishWorkout()

        enviarMensajeAlTelefono(
            path = WearConstants.PATH_FINISH_WORKOUT,
            message = "FINISH"
        )
    }

    private fun reiniciarAplicacion() {
        WorkoutTimer.stop()
        RestTimer.stop()
        HeartRateSimulator.stop()
        WorkoutState.showHome()
    }

    private fun enviarMensajeAlTelefono(
        path: String,
        message: String
    ) {
        PhoneMessageSender.sendMessage(
            context = this,
            path = path,
            message = message
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        if (isFinishing) {
            WorkoutTimer.stop()
            RestTimer.stop()
            HeartRateSimulator.stop()
        }
    }
}