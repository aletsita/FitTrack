package com.example.proyecto.ui.progreso.objetivo

import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.R
import com.example.proyecto.data.model.ObjetivoSemanal
import com.example.proyecto.data.repository.ProgresoRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class ConfigurarObjetivoActivity :
    AppCompatActivity() {

    private lateinit var etHorasObjetivo:
            TextInputEditText

    private lateinit var etSesionesObjetivo:
            TextInputEditText

    private lateinit var cbLunes: CheckBox
    private lateinit var cbMartes: CheckBox
    private lateinit var cbMiercoles: CheckBox
    private lateinit var cbJueves: CheckBox
    private lateinit var cbViernes: CheckBox
    private lateinit var cbSabado: CheckBox
    private lateinit var cbDomingo: CheckBox

    private lateinit var cbPushDay: CheckBox
    private lateinit var cbPullDay: CheckBox
    private lateinit var cbLegDay: CheckBox
    private lateinit var cbFullBody: CheckBox

    private lateinit var btnGuardarObjetivo:
            MaterialButton

    private lateinit var btnCancelarObjetivo:
            MaterialButton

    private lateinit var repository:
            ProgresoRepository

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_configurar_objetivo
        )

        repository =
            ProgresoRepository(this)

        enlazarVistas()

        cargarObjetivoActual()

        configurarEventos()
    }

    private fun enlazarVistas() {
        etHorasObjetivo =
            findViewById(
                R.id.etHorasObjetivo
            )

        etSesionesObjetivo =
            findViewById(
                R.id.etSesionesObjetivo
            )

        cbLunes =
            findViewById(R.id.cbLunes)

        cbMartes =
            findViewById(R.id.cbMartes)

        cbMiercoles =
            findViewById(R.id.cbMiercoles)

        cbJueves =
            findViewById(R.id.cbJueves)

        cbViernes =
            findViewById(R.id.cbViernes)

        cbSabado =
            findViewById(R.id.cbSabado)

        cbDomingo =
            findViewById(R.id.cbDomingo)

        cbPushDay =
            findViewById(R.id.cbPushDay)

        cbPullDay =
            findViewById(R.id.cbPullDay)

        cbLegDay =
            findViewById(R.id.cbLegDay)

        cbFullBody =
            findViewById(R.id.cbFullBody)

        btnGuardarObjetivo =
            findViewById(
                R.id.btnGuardarObjetivo
            )

        btnCancelarObjetivo =
            findViewById(
                R.id.btnCancelarObjetivo
            )
    }

    private fun cargarObjetivoActual() {
        val objetivo =
            repository.obtenerObjetivo()

        etHorasObjetivo.setText(
            objetivo.horasObjetivo.toString()
        )

        etSesionesObjetivo.setText(
            objetivo.sesionesObjetivo.toString()
        )

        cbLunes.isChecked =
            "Lunes" in objetivo.diasSeleccionados

        cbMartes.isChecked =
            "Martes" in objetivo.diasSeleccionados

        cbMiercoles.isChecked =
            "Miércoles" in objetivo.diasSeleccionados

        cbJueves.isChecked =
            "Jueves" in objetivo.diasSeleccionados

        cbViernes.isChecked =
            "Viernes" in objetivo.diasSeleccionados

        cbSabado.isChecked =
            "Sábado" in objetivo.diasSeleccionados

        cbDomingo.isChecked =
            "Domingo" in objetivo.diasSeleccionados

        cbPushDay.isChecked =
            "Push Day" in objetivo.rutinasSeleccionadas

        cbPullDay.isChecked =
            "Pull Day" in objetivo.rutinasSeleccionadas

        cbLegDay.isChecked =
            "Leg Day" in objetivo.rutinasSeleccionadas

        cbFullBody.isChecked =
            "Full Body" in objetivo.rutinasSeleccionadas
    }

    private fun configurarEventos() {
        btnGuardarObjetivo.setOnClickListener {
            guardarObjetivo()
        }

        btnCancelarObjetivo.setOnClickListener {
            finish()
        }
    }

    private fun guardarObjetivo() {
        val horas =
            etHorasObjetivo.text
                ?.toString()
                ?.trim()
                ?.toFloatOrNull()

        val sesiones =
            etSesionesObjetivo.text
                ?.toString()
                ?.trim()
                ?.toIntOrNull()

        if (
            horas == null ||
            horas <= 0
        ) {
            etHorasObjetivo.error =
                "Ingresa un número de horas válido"

            return
        }

        if (
            sesiones == null ||
            sesiones <= 0
        ) {
            etSesionesObjetivo.error =
                "Ingresa un número de sesiones válido"

            return
        }

        val dias =
            obtenerDiasSeleccionados()

        val rutinas =
            obtenerRutinasSeleccionadas()

        if (dias.isEmpty()) {
            Toast.makeText(
                this,
                "Selecciona al menos un día",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (rutinas.isEmpty()) {
            Toast.makeText(
                this,
                "Selecciona al menos una rutina",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        repository.guardarObjetivo(
            ObjetivoSemanal(
                horasObjetivo = horas,
                sesionesObjetivo = sesiones,
                diasSeleccionados = dias,
                rutinasSeleccionadas = rutinas
            )
        )

        Toast.makeText(
            this,
            "Objetivo semanal guardado",
            Toast.LENGTH_SHORT
        ).show()

        setResult(
            RESULT_OK
        )

        finish()
    }

    private fun obtenerDiasSeleccionados():
            List<String> {

        val dias =
            mutableListOf<String>()

        if (cbLunes.isChecked) {
            dias.add("Lunes")
        }

        if (cbMartes.isChecked) {
            dias.add("Martes")
        }

        if (cbMiercoles.isChecked) {
            dias.add("Miércoles")
        }

        if (cbJueves.isChecked) {
            dias.add("Jueves")
        }

        if (cbViernes.isChecked) {
            dias.add("Viernes")
        }

        if (cbSabado.isChecked) {
            dias.add("Sábado")
        }

        if (cbDomingo.isChecked) {
            dias.add("Domingo")
        }

        return dias
    }

    private fun obtenerRutinasSeleccionadas():
            List<String> {

        val rutinas =
            mutableListOf<String>()

        if (cbPushDay.isChecked) {
            rutinas.add("Push Day")
        }

        if (cbPullDay.isChecked) {
            rutinas.add("Pull Day")
        }

        if (cbLegDay.isChecked) {
            rutinas.add("Leg Day")
        }

        if (cbFullBody.isChecked) {
            rutinas.add("Full Body")
        }

        return rutinas
    }
}