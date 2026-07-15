package com.example.proyecto.ui.entrenamiento

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.R
import com.google.android.material.button.MaterialButton
import java.util.Locale
import kotlin.random.Random
import com.example.proyecto.data.model.SesionEntrenamiento
import com.example.proyecto.data.repository.ProgresoRepository

class EntrenamientoActivoActivity : AppCompatActivity() {

    private lateinit var tvNombreRutina: TextView
    private lateinit var tvEjercicioActual: TextView
    private lateinit var tvSerieActual: TextView
    private lateinit var tvRepeticiones: TextView
    private lateinit var tvCronometro: TextView
    private lateinit var tvFrecuenciaCardiaca: TextView
    private lateinit var tvEstadoEntrenamiento: TextView

    private lateinit var btnCompletarSerie: MaterialButton
    private lateinit var btnPausar: MaterialButton
    private lateinit var btnFinalizar: MaterialButton

    private var segundosTranscurridos = 0
    private var entrenamientoActivo = true

    private var serieActual = 1
    private val totalSeries = 4

    private var indiceEjercicio = 0

    private val ejercicios = listOf(
        EjercicioEntrenamiento(
            "Press de banca",
            4,
            10,
            60
        ),
        EjercicioEntrenamiento(
            "Press militar",
            4,
            12,
            60
        ),
        EjercicioEntrenamiento(
            "Fondos de tríceps",
            3,
            12,
            45
        )
    )

    private val handlerCronometro =
        Handler(Looper.getMainLooper())

    private val handlerFrecuencia =
        Handler(Looper.getMainLooper())

    private var temporizadorDescanso:
            CountDownTimer? = null


    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_entrenamiento_activo
        )

        inicializarComponentes()

        cargarRutina()

        mostrarEjercicioActual()

        configurarEventos()

        fechaInicioEntrenamiento = System.currentTimeMillis()

        iniciarCronometro()

        iniciarFrecuenciaCardiaca()
    }

    private var fechaInicioEntrenamiento: Long = 0L

    private var seriesCompletadasTotal: Int = 0


    private fun inicializarComponentes() {

        tvNombreRutina =
            findViewById(
                R.id.tvNombreRutinaEntrenamiento
            )

        tvEjercicioActual =
            findViewById(
                R.id.tvEjercicioActual
            )

        tvSerieActual =
            findViewById(
                R.id.tvSerieActual
            )

        tvRepeticiones =
            findViewById(
                R.id.tvRepeticiones
            )

        tvCronometro =
            findViewById(
                R.id.tvCronometro
            )

        tvFrecuenciaCardiaca =
            findViewById(
                R.id.tvFrecuenciaCardiaca
            )

        tvEstadoEntrenamiento =
            findViewById(
                R.id.tvEstadoEntrenamiento
            )

        btnCompletarSerie =
            findViewById(
                R.id.btnCompletarSerie
            )

        btnPausar =
            findViewById(
                R.id.btnPausarEntrenamiento
            )

        btnFinalizar =
            findViewById(
                R.id.btnFinalizarEntrenamiento
            )
    }


    private fun cargarRutina() {

        val nombreRutina =
            intent.getStringExtra(
                "RUTINA_NOMBRE"
            ) ?: "Entrenamiento"

        tvNombreRutina.text =
            nombreRutina.uppercase()
    }


    private fun configurarEventos() {

        btnCompletarSerie.setOnClickListener {

            completarSerie()
        }

        btnPausar.setOnClickListener {

            cambiarEstadoEntrenamiento()
        }

        btnFinalizar.setOnClickListener {

            finalizarEntrenamiento()
        }
    }


    private fun mostrarEjercicioActual() {

        val ejercicio =
            ejercicios[indiceEjercicio]

        tvEjercicioActual.text =
            ejercicio.nombre

        tvSerieActual.text =
            "SERIE $serieActual / ${ejercicio.series}"

        tvRepeticiones.text =
            "${ejercicio.repeticiones} REPETICIONES"

        tvEstadoEntrenamiento.text =
            "ENTRENAMIENTO ACTIVO"

        btnCompletarSerie.text =
            "COMPLETAR SERIE"
    }


    private fun completarSerie() {

        seriesCompletadasTotal++

        val ejercicio =
            ejercicios[indiceEjercicio]

        iniciarDescanso(
            ejercicio.descanso
        )
    }


    private fun iniciarDescanso(
        segundos: Int
    ) {

        btnCompletarSerie.isEnabled = false

        temporizadorDescanso?.cancel()

        temporizadorDescanso =

            object : CountDownTimer(
                segundos * 1000L,
                1000L
            ) {

                override fun onTick(
                    millisUntilFinished: Long
                ) {

                    val segundosRestantes =
                        millisUntilFinished / 1000

                    tvEstadoEntrenamiento.text =
                        "DESCANSO"

                    btnCompletarSerie.text =
                        "DESCANSO ${segundosRestantes}s"
                }


                override fun onFinish() {

                    avanzarSerie()

                    btnCompletarSerie.isEnabled =
                        true
                }

            }.start()
    }


    private fun avanzarSerie() {

        val ejercicio =
            ejercicios[indiceEjercicio]

        if (
            serieActual < ejercicio.series
        ) {

            serieActual++

            mostrarEjercicioActual()

        } else {

            avanzarEjercicio()
        }
    }


    private fun avanzarEjercicio() {

        if (
            indiceEjercicio <
            ejercicios.lastIndex
        ) {

            indiceEjercicio++

            serieActual = 1

            mostrarEjercicioActual()

        } else {

            finalizarEntrenamiento()
        }
    }


    private fun iniciarCronometro() {

        handlerCronometro.post(
            object : Runnable {

                override fun run() {

                    if (entrenamientoActivo) {

                        segundosTranscurridos++

                        actualizarCronometro()
                    }

                    handlerCronometro.postDelayed(
                        this,
                        1000
                    )
                }
            }
        )
    }


    private fun actualizarCronometro() {

        val horas =
            segundosTranscurridos / 3600

        val minutos =
            (segundosTranscurridos % 3600) / 60

        val segundos =
            segundosTranscurridos % 60

        tvCronometro.text =
            String.format(
                Locale.getDefault(),
                "%02d:%02d:%02d",
                horas,
                minutos,
                segundos
            )
    }


    private fun iniciarFrecuenciaCardiaca() {

        handlerFrecuencia.post(
            object : Runnable {

                override fun run() {

                    if (entrenamientoActivo) {

                        val frecuencia =
                            Random.nextInt(
                                110,
                                151
                            )

                        tvFrecuenciaCardiaca.text =
                            "$frecuencia BPM"
                    }

                    handlerFrecuencia.postDelayed(
                        this,
                        3000
                    )
                }
            }
        )
    }


    private fun cambiarEstadoEntrenamiento() {

        entrenamientoActivo =
            !entrenamientoActivo

        if (entrenamientoActivo) {

            btnPausar.text =
                "PAUSAR"

            tvEstadoEntrenamiento.text =
                "ENTRENAMIENTO ACTIVO"

        } else {

            btnPausar.text =
                "REANUDAR"

            tvEstadoEntrenamiento.text =
                "ENTRENAMIENTO PAUSADO"
        }
    }


    private fun finalizarEntrenamiento() {

        entrenamientoActivo = false

        temporizadorDescanso?.cancel()

        handlerCronometro.removeCallbacksAndMessages(null)

        handlerFrecuencia.removeCallbacksAndMessages(null)

        guardarSesionEntrenamiento()

        finish()
    }

    private fun guardarSesionEntrenamiento() {

        val fechaFin =
            System.currentTimeMillis()

        val rutinaId =
            intent.getIntExtra(
                "RUTINA_ID",
                0
            )

        val nombreRutina =
            intent.getStringExtra(
                "RUTINA_NOMBRE"
            ) ?: "Entrenamiento"

        val sesion =
            SesionEntrenamiento(

                rutinaId = rutinaId,

                nombreRutina = nombreRutina,

                fechaInicio = fechaInicioEntrenamiento,

                fechaFin = fechaFin,

                duracionSegundos = segundosTranscurridos,

                ejerciciosCompletados = ejercicios.size,

                seriesCompletadas = seriesCompletadasTotal,

                frecuenciaPromedio =
                    tvFrecuenciaCardiaca.text
                        .toString()
                        .replace(" BPM", "")
                        .toIntOrNull()
                        ?: 120
            )

        ProgresoRepository(this)
            .guardarSesion(
                sesion
            )
    }


    override fun onDestroy() {

        super.onDestroy()

        handlerCronometro.removeCallbacksAndMessages(
            null
        )

        handlerFrecuencia.removeCallbacksAndMessages(
            null
        )

        temporizadorDescanso?.cancel()
    }
}


data class EjercicioEntrenamiento(
    val nombre: String,
    val series: Int,
    val repeticiones: Int,
    val descanso: Int
)