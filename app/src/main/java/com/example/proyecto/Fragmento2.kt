package com.example.proyecto

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto.data.model.Cliente
import com.example.proyecto.ui.clientes.ClientesAdapter
import com.example.proyecto.ui.clientes.ClientesViewModel
import com.example.proyecto.utils.TokenManager
import android.widget.EditText
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Fragmento2 : Fragment() {

    private val viewModel: ClientesViewModel by viewModels()
    private lateinit var tokenManager: TokenManager
    private lateinit var adapter: ClientesAdapter

    // Vistas
    private lateinit var etClave: EditText
    private lateinit var etNombre: EditText
    private lateinit var etEdad: EditText
    private lateinit var etFechaNacimiento: EditText
    private lateinit var btnNuevo: View
    private lateinit var btnGuardar: View
    private lateinit var btnEliminar: View
    private lateinit var progressClientes: View
    private lateinit var rvClientes: androidx.recyclerview.widget.RecyclerView
    private lateinit var tvEmptyGrid: View

    // Formato de fecha usado en toda la pantalla: yyyy-MM-dd
    private val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // Evita que buscarPorClave se dispare en bucle mientras autocompletamos el form
    private var autocompletando = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragmento2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tokenManager = TokenManager(requireContext())

        enlazarVistas(view)
        configurarRecyclerView()
        configurarListeners()
        observarViewModel()

        // Carga inicial del grid
        lifecycleScope.launch {
            val token = tokenManager.obtenerBearer()
            viewModel.cargarClientes(token)
        }
    }

    private fun enlazarVistas(view: View) {
        etClave = view.findViewById(R.id.etClave)
        etNombre = view.findViewById(R.id.etNombre)
        etEdad = view.findViewById(R.id.etEdad)
        etFechaNacimiento = view.findViewById(R.id.etFechaNacimiento)
        btnNuevo = view.findViewById(R.id.btnNuevo)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnEliminar = view.findViewById(R.id.btnEliminar)
        progressClientes = view.findViewById(R.id.progressClientes)
        rvClientes = view.findViewById(R.id.rvClientes)
        tvEmptyGrid = view.findViewById(R.id.tvEmptyGrid)
    }

    private fun configurarRecyclerView() {
        adapter = ClientesAdapter(emptyList()) { clienteSeleccionado ->
            // Click en una fila del grid -> llenar el formulario
            viewModel.seleccionarCliente(clienteSeleccionado)
        }
        rvClientes.layoutManager = LinearLayoutManager(requireContext())
        rvClientes.adapter = adapter
    }

    private fun configurarListeners() {

        // ── Botón "Nuevo": limpia la pantalla ────────────────────
        btnNuevo.setOnClickListener {
            limpiarFormulario()
            viewModel.limpiarSeleccion()
        }

        // ── Botón "Guardar": inserta o actualiza ─────────────────
        btnGuardar.setOnClickListener {
            ocultarTeclado()
            val clave = etClave.text?.toString()?.trim() ?: ""
            val nombre = etNombre.text?.toString()?.trim() ?: ""
            val edadTexto = etEdad.text?.toString()?.trim() ?: ""
            val fecha = etFechaNacimiento.text?.toString()?.trim() ?: ""

            if (clave.isEmpty() || nombre.isEmpty()) {
                Toast.makeText(requireContext(), "Clave y Nombre son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (fecha.isEmpty()) {
                Toast.makeText(requireContext(), "Selecciona la fecha de nacimiento", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val edad = edadTexto.toIntOrNull() ?: 0

            lifecycleScope.launch {
                val token = tokenManager.obtenerBearer()
                // El backend espera fecha en formato ISO con hora
                viewModel.guardar(token, clave, nombre, edad, "${fecha}T00:00:00Z")
            }
        }

        // ── Botón "Eliminar": confirma y elimina ─────────────────
        btnEliminar.setOnClickListener {
            val cliente = viewModel.clienteSeleccionado.value
            if (cliente == null) {
                Toast.makeText(requireContext(), "Selecciona un cliente primero", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            AlertDialog.Builder(requireContext())
                .setTitle("Confirmar eliminación")
                .setMessage("¿Seguro que quieres eliminar a \"${cliente.nombre}\"?")
                .setPositiveButton("Eliminar") { _, _ ->
                    lifecycleScope.launch {
                        val token = tokenManager.obtenerBearer()
                        viewModel.eliminar(token)
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        // ── Selector de fecha (DatePickerDialog) ─────────────────
        etFechaNacimiento.setOnClickListener {
            mostrarDatePicker()
        }

        // ── Buscar automáticamente al teclear la clave ───────────
        etClave.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                if (autocompletando) return
                val clave = s?.toString()?.trim() ?: ""
                if (clave.isNotEmpty()) {
                    lifecycleScope.launch {
                        val token = tokenManager.obtenerBearer()
                        viewModel.buscarPorClave(token, clave)
                    }
                } else {
                    viewModel.limpiarSeleccion()
                }
            }
        })
    }

    private fun mostrarDatePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                etFechaNacimiento.setText(formatoFecha.format(calendar.time))
            },
            calendar.get(Calendar.YEAR) - 25, // año inicial sugerido
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun observarViewModel() {

        // Lista de clientes -> refresca el grid
        viewModel.clientes.observe(viewLifecycleOwner) { lista ->
            adapter.actualizarLista(lista)
            tvEmptyGrid.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
            rvClientes.visibility = if (lista.isEmpty()) View.GONE else View.VISIBLE
        }

        // Cliente seleccionado -> autocompleta o limpia el formulario
        viewModel.clienteSeleccionado.observe(viewLifecycleOwner) { cliente ->
            autocompletando = true
            if (cliente != null) {
                llenarFormulario(cliente)
            } else {
                limpiarCamposExceptoClave()
            }
            autocompletando = false
        }

        // Estados de UI (loading, error, éxito)
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ClientesViewModel.ClientesUiState.Loading -> {
                    progressClientes.visibility = View.VISIBLE
                    habilitarBotones(false)
                }
                is ClientesViewModel.ClientesUiState.GuardadoExitoso -> {
                    progressClientes.visibility = View.GONE
                    habilitarBotones(true)
                    Toast.makeText(requireContext(), state.mensaje, Toast.LENGTH_SHORT).show()
                    viewModel.resetState()
                }
                is ClientesViewModel.ClientesUiState.EliminadoExitoso -> {
                    progressClientes.visibility = View.GONE
                    habilitarBotones(true)
                    Toast.makeText(requireContext(), state.mensaje, Toast.LENGTH_SHORT).show()
                    limpiarFormulario()
                    viewModel.resetState()
                }
                is ClientesViewModel.ClientesUiState.Error -> {
                    progressClientes.visibility = View.GONE
                    habilitarBotones(true)
                    Toast.makeText(requireContext(), state.mensaje, Toast.LENGTH_LONG).show()
                    viewModel.resetState()
                }
                else -> {
                    progressClientes.visibility = View.GONE
                    habilitarBotones(true)
                }
            }
        }
    }

    private fun llenarFormulario(cliente: Cliente) {
        etClave.setText(cliente.clave)
        etNombre.setText(cliente.nombre)
        etEdad.setText(cliente.edad.toString())
        // El backend regresa fecha ISO completa; nos quedamos solo con yyyy-MM-dd
        etFechaNacimiento.setText(cliente.fechaNacimiento.take(10))
    }

    private fun limpiarCamposExceptoClave() {
        etNombre.setText("")
        etEdad.setText("")
        etFechaNacimiento.setText("")
    }

    private fun limpiarFormulario() {
        etClave.setText("")
        etNombre.setText("")
        etEdad.setText("")
        etFechaNacimiento.setText("")
        etClave.requestFocus()
    }

    private fun habilitarBotones(habilitado: Boolean) {
        btnGuardar.isEnabled = habilitado
        btnEliminar.isEnabled = habilitado
        btnNuevo.isEnabled = habilitado
    }

    private fun ocultarTeclado() {
        val imm = requireContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE)
                as android.view.inputmethod.InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }
}