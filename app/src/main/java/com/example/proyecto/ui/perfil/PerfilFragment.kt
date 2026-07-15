package com.example.proyecto.ui.perfil

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.proyecto.MainActivity
import com.example.proyecto.R
import com.example.proyecto.data.repository.PerfilRepository
import com.example.proyecto.ui.perfil.editar.EditarPerfilActivity
import com.google.android.material.button.MaterialButton
import java.util.Locale

class PerfilFragment : Fragment() {

    private lateinit var tvAvatar: TextView
    private lateinit var tvNombre: TextView
    private lateinit var tvRol: TextView
    private lateinit var tvPeso: TextView
    private lateinit var tvAltura: TextView
    private lateinit var tvImc: TextView
    private lateinit var tvObjetivo: TextView
    private lateinit var tvSmartwatch: TextView

    private lateinit var repository:
            PerfilRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_perfil,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        repository =
            PerfilRepository(requireContext())

        tvAvatar =
            view.findViewById(R.id.tvAvatarPerfil)

        tvNombre =
            view.findViewById(R.id.tvNombrePerfil)

        tvRol =
            view.findViewById(R.id.tvRolPerfil)

        tvPeso =
            view.findViewById(R.id.tvPesoPerfil)

        tvAltura =
            view.findViewById(R.id.tvAlturaPerfil)

        tvImc =
            view.findViewById(R.id.tvImcPerfil)

        tvObjetivo =
            view.findViewById(R.id.tvObjetivoPerfil)

        tvSmartwatch =
            view.findViewById(R.id.tvSmartwatchPerfil)

        view.findViewById<MaterialButton>(
            R.id.btnEditarPerfil
        ).setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    EditarPerfilActivity::class.java
                )
            )
        }

        view.findViewById<MaterialButton>(
            R.id.btnCerrarSesionPerfil
        ).setOnClickListener {
            val intent = Intent(
                requireContext(),
                MainActivity::class.java
            )

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        if (this::repository.isInitialized) {
            cargarPerfil()
        }
    }

    private fun cargarPerfil() {
        val perfil =
            repository.obtenerPerfil()

        tvAvatar.text =
            perfil.nombre
                .firstOrNull()
                ?.uppercase()
                ?: "U"

        tvNombre.text =
            perfil.nombre

        tvRol.text =
            perfil.rol

        tvPeso.text =
            String.format(
                Locale.getDefault(),
                "%.1f kg",
                perfil.peso
            )

        tvAltura.text =
            String.format(
                Locale.getDefault(),
                "%.2f m",
                perfil.altura
            )

        tvImc.text =
            String.format(
                Locale.getDefault(),
                "%.1f",
                perfil.calcularImc()
            )

        tvObjetivo.text =
            perfil.objetivo

        tvSmartwatch.text =
            if (perfil.smartwatchConectado) {
                "CONECTADO"
            } else {
                "DESCONECTADO"
            }
    }
}