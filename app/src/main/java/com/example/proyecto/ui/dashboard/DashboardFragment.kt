package com.example.proyecto.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.proyecto.Principal
import com.example.proyecto.R
import com.google.android.material.card.MaterialCardView

class DashboardFragment : Fragment() {

    // INFORMACIÓN DEL USUARIO

    private lateinit var tvSaludo: TextView
    private lateinit var tvRolUsuario: TextView
    private lateinit var tvMensajeDashboard: TextView


    // ESTADÍSTICAS

    private lateinit var tvEntrenamientosHoy: TextView
    private lateinit var tvNumeroEntrenamientos: TextView
    private lateinit var tvTotalClientes: TextView
    private lateinit var tvTotalRutinas: TextView
    private lateinit var tvTotalEntrenadores: TextView
    private lateinit var tvProgresoPromedio: TextView


    // TARJETAS

    private lateinit var cardEntrenamientoHoy: MaterialCardView
    private lateinit var cardClientes: MaterialCardView
    private lateinit var cardRutinas: MaterialCardView
    private lateinit var cardEntrenadores: MaterialCardView
    private lateinit var cardProgreso: MaterialCardView


    // ACCESOS RÁPIDOS

    private lateinit var btnVerClientes: MaterialCardView
    private lateinit var btnVerRutinas: MaterialCardView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.dashboard_fragment,
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

        inicializarComponentes(view)

        configurarInformacionUsuario()

        configurarEstadisticas()

        configurarNavegacion()
    }


    private fun inicializarComponentes(
        view: View
    ) {

        // USUARIO

        tvSaludo = view.findViewById(
            R.id.tvSaludo
        )

        tvRolUsuario = view.findViewById(
            R.id.tvRolUsuario
        )

        tvMensajeDashboard = view.findViewById(
            R.id.tvMensajeDashboard
        )


        // ESTADÍSTICAS

        tvEntrenamientosHoy = view.findViewById(
            R.id.tvEntrenamientosHoy
        )

        tvNumeroEntrenamientos = view.findViewById(
            R.id.tvNumeroEntrenamientos
        )

        tvTotalClientes = view.findViewById(
            R.id.tvTotalClientes
        )

        tvTotalRutinas = view.findViewById(
            R.id.tvTotalRutinas
        )

        tvTotalEntrenadores = view.findViewById(
            R.id.tvTotalEntrenadores
        )

        tvProgresoPromedio = view.findViewById(
            R.id.tvProgresoPromedio
        )


        // TARJETAS

        cardEntrenamientoHoy = view.findViewById(
            R.id.cardEntrenamientoHoy
        )

        cardClientes = view.findViewById(
            R.id.cardClientes
        )

        cardRutinas = view.findViewById(
            R.id.cardRutinas
        )

        cardEntrenadores = view.findViewById(
            R.id.cardEntrenadores
        )

        cardProgreso = view.findViewById(
            R.id.cardProgreso
        )


        // ACCESOS RÁPIDOS

        btnVerClientes = view.findViewById(
            R.id.btnVerClientes
        )

        btnVerRutinas = view.findViewById(
            R.id.btnVerRutinas
        )
    }


    private fun configurarInformacionUsuario() {

        val nombreUsuario = "Alexa"

        val rolUsuario = "Administrador"

        tvSaludo.text =
            "¡Hola, $nombreUsuario!"

        tvRolUsuario.text =
            "$rolUsuario de FitTrack"

        tvMensajeDashboard.text =
            "El gimnasio está activo. " +
                    "Revisa el rendimiento de tus atletas " +
                    "y organiza los entrenamientos."
    }


    private fun configurarEstadisticas() {

        tvNumeroEntrenamientos.text = "12"

        tvEntrenamientosHoy.text =
            "sesiones programadas"

        tvTotalClientes.text = "48"

        tvTotalRutinas.text = "15"

        tvTotalEntrenadores.text = "8"

        tvProgresoPromedio.text = "78%"
    }


    private fun configurarNavegacion() {

        // ACTIVIDAD DE HOY -> RUTINAS

        cardEntrenamientoHoy.setOnClickListener {

            navegarA(
                Principal.PANTALLA_RUTINAS
            )
        }


        // ATLETAS ACTIVOS -> CLIENTES

        cardClientes.setOnClickListener {

            navegarA(
                Principal.PANTALLA_CLIENTES
            )
        }


        // RUTINAS ACTIVAS -> RUTINAS

        cardRutinas.setOnClickListener {

            navegarA(
                Principal.PANTALLA_RUTINAS
            )
        }


        // COACHES -> USUARIOS

        cardEntrenadores.setOnClickListener {

            navegarA(
                Principal.PANTALLA_USUARIOS
            )
        }


        // PROGRESO -> PROGRESO

        cardProgreso.setOnClickListener {

            navegarA(
                Principal.PANTALLA_PROGRESO
            )
        }


        // MIS ATLETAS -> CLIENTES

        btnVerClientes.setOnClickListener {

            navegarA(
                Principal.PANTALLA_CLIENTES
            )
        }


        // ZONA DE ENTRENAMIENTO -> RUTINAS

        btnVerRutinas.setOnClickListener {

            navegarA(
                Principal.PANTALLA_RUTINAS
            )
        }
    }


    private fun navegarA(
        pantalla: Int
    ) {

        val principal =
            activity as? Principal

        principal?.irAPantalla(
            pantalla
        )
    }
}