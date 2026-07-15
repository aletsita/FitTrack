package com.example.proyecto.ui.rutinas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.data.model.Rutina
import com.example.proyecto.ui.rutinas.detalle.DetalleRutinaActivity
import com.example.proyecto.ui.rutinas.nueva.NuevaRutinaActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class RutinasFragment : Fragment() {

    private lateinit var rvRutinas: RecyclerView

    private lateinit var btnNuevaRutina:
            ExtendedFloatingActionButton

    private lateinit var btnVerRutinaDestacada:
            MaterialButton

    private lateinit var tvCantidadRutinas:
            TextView

    private lateinit var adapter:
            RutinasAdapter

    private var rutinas:
            List<Rutina> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_rutinas,
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

        configurarRecyclerView()

        cargarRutinas()

        configurarEventos()
    }


    private fun inicializarComponentes(
        view: View
    ) {

        rvRutinas =
            view.findViewById(
                R.id.rvRutinas
            )

        btnNuevaRutina =
            view.findViewById(
                R.id.btnNuevaRutina
            )

        btnVerRutinaDestacada =
            view.findViewById(
                R.id.btnVerRutinaDestacada
            )

        tvCantidadRutinas =
            view.findViewById(
                R.id.tvCantidadRutinas
            )

        // TEMPORAL PARA PRUEBAS:
        // El botón siempre estará visible.
        btnNuevaRutina.visibility =
            View.VISIBLE
    }


    private fun configurarRecyclerView() {

        adapter = RutinasAdapter(
            emptyList()
        ) { rutina: Rutina ->

            abrirDetalleRutina(
                rutina
            )
        }

        rvRutinas.layoutManager =
            LinearLayoutManager(
                requireContext()
            )

        rvRutinas.adapter =
            adapter
    }


    private fun cargarRutinas() {

        rutinas = listOf(

            Rutina(
                id = 1,
                nombre = "Push Day",
                descripcion = "Pecho, hombro y tríceps",
                nivel = "Intermedio",
                duracion = 60,
                ejercicios = 6
            ),

            Rutina(
                id = 2,
                nombre = "Pull Day",
                descripcion = "Espalda y bíceps",
                nivel = "Intermedio",
                duracion = 55,
                ejercicios = 7
            ),

            Rutina(
                id = 3,
                nombre = "Leg Day",
                descripcion = "Pierna y glúteo",
                nivel = "Avanzado",
                duracion = 70,
                ejercicios = 8
            ),

            Rutina(
                id = 4,
                nombre = "Full Body",
                descripcion = "Entrenamiento de cuerpo completo",
                nivel = "Principiante",
                duracion = 45,
                ejercicios = 6
            )
        )

        adapter.actualizarLista(
            rutinas
        )

        tvCantidadRutinas.text =
            "${rutinas.size} planes disponibles"
    }


    private fun configurarEventos() {

        btnNuevaRutina.setOnClickListener {

            abrirNuevaRutina()
        }


        btnVerRutinaDestacada.setOnClickListener {

            val rutinaDestacada =
                rutinas.firstOrNull()

            if (rutinaDestacada != null) {

                abrirDetalleRutina(
                    rutinaDestacada
                )
            }
        }
    }


    private fun abrirDetalleRutina(
        rutina: Rutina
    ) {

        val intent = Intent(
            requireContext(),
            DetalleRutinaActivity::class.java
        )

        intent.putExtra(
            "RUTINA_ID",
            rutina.id
        )

        intent.putExtra(
            "RUTINA_NOMBRE",
            rutina.nombre
        )

        intent.putExtra(
            "RUTINA_DESCRIPCION",
            rutina.descripcion
        )

        intent.putExtra(
            "RUTINA_NIVEL",
            rutina.nivel
        )

        intent.putExtra(
            "RUTINA_DURACION",
            rutina.duracion
        )

        intent.putExtra(
            "RUTINA_EJERCICIOS",
            rutina.ejercicios
        )

        startActivity(intent)
    }


    private fun abrirNuevaRutina() {

        Toast.makeText(
            requireContext(),
            "Abriendo nueva rutina",
            Toast.LENGTH_SHORT
        ).show()

        val intent = Intent(
            requireContext(),
            NuevaRutinaActivity::class.java
        )

        startActivity(intent)
    }
}