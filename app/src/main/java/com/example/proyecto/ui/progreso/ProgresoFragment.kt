package com.example.proyecto.ui.progreso

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.data.model.ObjetivoSemanal
import com.example.proyecto.data.model.SesionEntrenamiento
import com.example.proyecto.data.repository.ProgresoRepository
import com.example.proyecto.ui.progreso.detalle.DetalleSesionActivity
import com.example.proyecto.ui.progreso.objetivo.ConfigurarObjetivoActivity
import com.google.android.material.button.MaterialButton
import java.util.Locale

class ProgresoFragment : Fragment() {

    private lateinit var btnConfigurarObjetivo:
            MaterialButton

    private lateinit var tvPorcentajeSemanal:
            TextView

    private lateinit var progressSemanal:
            ProgressBar

    private lateinit var tvResumenObjetivo:
            TextView

    private lateinit var tvResumenSesiones:
            TextView

    private lateinit var tvTotalEntrenamientos:
            TextView

    private lateinit var tvTiempoAcumulado:
            TextView

    private lateinit var tvPlanSemanal:
            TextView

    private lateinit var tvSinEntrenamientos:
            TextView

    private lateinit var rvHistorialEntrenamientos:
            RecyclerView

    private lateinit var historialAdapter:
            HistorialAdapter

    private lateinit var repository:
            ProgresoRepository

    private val configurarObjetivoLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { resultado ->

            if (
                resultado.resultCode ==
                Activity.RESULT_OK
            ) {
                cargarDatos()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_progreso,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )

        repository =
            ProgresoRepository(
                requireContext()
            )

        enlazarVistas(view)

        configurarRecyclerView()

        configurarEventos()
    }

    override fun onResume() {
        super.onResume()

        if (
            this::repository.isInitialized
        ) {
            cargarDatos()
        }
    }

    private fun enlazarVistas(
        view: View
    ) {
        btnConfigurarObjetivo =
            view.findViewById(
                R.id.btnConfigurarObjetivo
            )

        tvPorcentajeSemanal =
            view.findViewById(
                R.id.tvPorcentajeSemanal
            )

        progressSemanal =
            view.findViewById(
                R.id.progressSemanal
            )

        tvResumenObjetivo =
            view.findViewById(
                R.id.tvResumenObjetivo
            )

        tvResumenSesiones =
            view.findViewById(
                R.id.tvResumenSesiones
            )

        tvTotalEntrenamientos =
            view.findViewById(
                R.id.tvTotalEntrenamientos
            )

        tvTiempoAcumulado =
            view.findViewById(
                R.id.tvTiempoAcumulado
            )

        tvPlanSemanal =
            view.findViewById(
                R.id.tvPlanSemanal
            )

        tvSinEntrenamientos =
            view.findViewById(
                R.id.tvSinEntrenamientos
            )

        rvHistorialEntrenamientos =
            view.findViewById(
                R.id.rvHistorialEntrenamientos
            )
    }

    private fun configurarRecyclerView() {
        historialAdapter =
            HistorialAdapter(
                emptyList()
            ) { sesion ->

                abrirDetalleSesion(
                    sesion
                )
            }

        rvHistorialEntrenamientos.layoutManager =
            LinearLayoutManager(
                requireContext()
            )

        rvHistorialEntrenamientos.adapter =
            historialAdapter
    }

    private fun configurarEventos() {
        btnConfigurarObjetivo
            .setOnClickListener {

                val intent =
                    Intent(
                        requireContext(),
                        ConfigurarObjetivoActivity::class.java
                    )

                configurarObjetivoLauncher.launch(
                    intent
                )
            }
    }

    private fun cargarDatos() {
        val objetivo =
            repository.obtenerObjetivo()

        val sesionesSemana =
            repository.obtenerSesionesSemanaActual()

        val todasLasSesiones =
            repository.obtenerSesiones()

        val segundosSemana =
            repository.obtenerSegundosSemana()

        val porcentaje =
            repository.obtenerPorcentajeSemanal()

        tvPorcentajeSemanal.text =
            "$porcentaje%"

        progressSemanal.progress =
            porcentaje

        tvResumenObjetivo.text =
            "${formatearTiempo(segundosSemana)} de " +
                    "${formatearHorasObjetivo(objetivo.horasObjetivo)}"

        tvResumenSesiones.text =
            "${sesionesSemana.size} de " +
                    "${objetivo.sesionesObjetivo} sesiones"

        tvTotalEntrenamientos.text =
            sesionesSemana.size.toString()

        tvTiempoAcumulado.text =
            formatearTiempo(
                segundosSemana
            )

        mostrarPlanSemanal(
            objetivo,
            sesionesSemana
        )

        historialAdapter.actualizarLista(
            todasLasSesiones
        )

        val estaVacio =
            todasLasSesiones.isEmpty()

        tvSinEntrenamientos.visibility =
            if (estaVacio) {
                View.VISIBLE
            } else {
                View.GONE
            }

        rvHistorialEntrenamientos.visibility =
            if (estaVacio) {
                View.GONE
            } else {
                View.VISIBLE
            }
    }

    private fun mostrarPlanSemanal(
        objetivo: ObjetivoSemanal,
        sesionesSemana:
        List<SesionEntrenamiento>
    ) {
        if (
            objetivo.rutinasSeleccionadas.isEmpty()
        ) {
            tvPlanSemanal.text =
                "Todavía no has configurado un plan."

            return
        }

        val texto =
            objetivo.rutinasSeleccionadas
                .mapIndexed { indice, rutina ->

                    val completada =
                        sesionesSemana.any {
                            it.nombreRutina.equals(
                                rutina,
                                ignoreCase = true
                            )
                        }

                    val simbolo =
                        if (completada) {
                            "✓"
                        } else {
                            "○"
                        }

                    val dia =
                        objetivo.diasSeleccionados
                            .getOrNull(indice)
                            ?: "Día libre"

                    "$simbolo  $dia · $rutina"
                }
                .joinToString(
                    separator = "\n\n"
                )

        tvPlanSemanal.text =
            texto
    }

    private fun abrirDetalleSesion(
        sesion: SesionEntrenamiento
    ) {
        val intent =
            Intent(
                requireContext(),
                DetalleSesionActivity::class.java
            )

        intent.putExtra(
            DetalleSesionActivity.EXTRA_SESION_ID,
            sesion.id
        )

        startActivity(intent)
    }

    private fun formatearTiempo(
        segundos: Int
    ): String {
        val horas =
            segundos / 3600

        val minutos =
            (segundos % 3600) / 60

        return when {
            horas > 0 ->
                "${horas}h ${minutos}min"

            minutos > 0 ->
                "$minutos min"

            else ->
                "$segundos s"
        }
    }

    private fun formatearHorasObjetivo(
        horas: Float
    ): String {
        return if (
            horas % 1f == 0f
        ) {
            String.format(
                Locale.getDefault(),
                "%.0f horas",
                horas
            )
        } else {
            String.format(
                Locale.getDefault(),
                "%.1f horas",
                horas
            )
        }
    }
}