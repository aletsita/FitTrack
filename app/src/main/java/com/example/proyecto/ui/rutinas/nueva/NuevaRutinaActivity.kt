package com.example.proyecto.ui.rutinas.nueva

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class NuevaRutinaActivity : AppCompatActivity() {

    private lateinit var etNombreRutina: TextInputEditText
    private lateinit var etDescripcionRutina: TextInputEditText
    private lateinit var etNivelRutina: TextInputEditText
    private lateinit var etDuracionRutina: TextInputEditText
    private lateinit var etCantidadEjercicios: TextInputEditText

    private lateinit var btnGuardarRutina: MaterialButton
    private lateinit var btnCancelarRutina: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_nueva_rutina
        )

        inicializarComponentes()

        configurarEventos()
    }

    private fun inicializarComponentes() {

        etNombreRutina = findViewById(
            R.id.etNombreRutina
        )

        etDescripcionRutina = findViewById(
            R.id.etDescripcionRutina
        )

        etNivelRutina = findViewById(
            R.id.etNivelRutina
        )

        etDuracionRutina = findViewById(
            R.id.etDuracionRutina
        )

        etCantidadEjercicios = findViewById(
            R.id.etCantidadEjercicios
        )

        btnGuardarRutina = findViewById(
            R.id.btnGuardarRutina
        )

        btnCancelarRutina = findViewById(
            R.id.btnCancelarRutina
        )
    }

    private fun configurarEventos() {

        btnGuardarRutina.setOnClickListener {

            guardarRutina()
        }

        btnCancelarRutina.setOnClickListener {

            finish()
        }
    }

    private fun guardarRutina() {

        val nombre =
            etNombreRutina.text
                ?.toString()
                ?.trim()
                .orEmpty()

        val descripcion =
            etDescripcionRutina.text
                ?.toString()
                ?.trim()
                .orEmpty()

        val nivel =
            etNivelRutina.text
                ?.toString()
                ?.trim()
                .orEmpty()

        val duracionTexto =
            etDuracionRutina.text
                ?.toString()
                ?.trim()
                .orEmpty()

        val cantidadEjerciciosTexto =
            etCantidadEjercicios.text
                ?.toString()
                ?.trim()
                .orEmpty()


        if (nombre.isBlank()) {

            etNombreRutina.error =
                "Ingresa el nombre de la rutina"

            etNombreRutina.requestFocus()

            return
        }


        if (descripcion.isBlank()) {

            etDescripcionRutina.error =
                "Ingresa una descripción"

            etDescripcionRutina.requestFocus()

            return
        }


        if (nivel.isBlank()) {

            etNivelRutina.error =
                "Ingresa el nivel"

            etNivelRutina.requestFocus()

            return
        }


        val duracion =
            duracionTexto.toIntOrNull()

        if (
            duracion == null ||
            duracion <= 0
        ) {

            etDuracionRutina.error =
                "Ingresa una duración válida"

            etDuracionRutina.requestFocus()

            return
        }


        val cantidadEjercicios =
            cantidadEjerciciosTexto.toIntOrNull()

        if (
            cantidadEjercicios == null ||
            cantidadEjercicios <= 0
        ) {

            etCantidadEjercicios.error =
                "Ingresa una cantidad válida"

            etCantidadEjercicios.requestFocus()

            return
        }


        /*
         * AQUÍ SE CONECTARÁ LA API
         *
         * POST /api/rutinas
         */

        Toast.makeText(
            this,
            "Rutina \"$nombre\" guardada",
            Toast.LENGTH_SHORT
        ).show()


        setResult(
            RESULT_OK
        )

        finish()
    }
}