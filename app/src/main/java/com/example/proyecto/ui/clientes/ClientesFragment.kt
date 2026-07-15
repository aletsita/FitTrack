package com.example.proyecto.ui.clientes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.data.model.Cliente
import com.example.proyecto.utils.TokenManager
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class ClientesFragment : Fragment() {

    private val viewModel: ClientesViewModel by viewModels()

    private lateinit var rvClientes: RecyclerView
    private lateinit var etBuscarCliente: EditText
    private lateinit var btnNuevoCliente: MaterialButton
    private lateinit var tvCantidadClientes: TextView

    private lateinit var clientesAdapter: ClientesAdapter
    private lateinit var tokenManager: TokenManager

    private var listaCompletaClientes: List<Cliente> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_clientes,
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

        observarViewModel()

        configurarBuscador()

        configurarBotones()

        cargarClientes()
    }

    private fun inicializarComponentes(
        view: View
    ) {

        rvClientes =
            view.findViewById(
                R.id.rvClientes
            )

        etBuscarCliente =
            view.findViewById(
                R.id.etBuscarCliente
            )

        btnNuevoCliente =
            view.findViewById(
                R.id.btnNuevoCliente
            )

        tvCantidadClientes =
            view.findViewById(
                R.id.tvCantidadClientes
            )

        tokenManager =
            TokenManager(
                requireContext()
            )
    }

    private fun configurarRecyclerView() {

        clientesAdapter =
            ClientesAdapter(
                emptyList()
            ) { cliente ->

                seleccionarCliente(cliente)

            }

        rvClientes.apply {

            layoutManager =
                LinearLayoutManager(
                    requireContext()
                )

            adapter =
                clientesAdapter

            setHasFixedSize(true)
        }
    }

    private fun observarViewModel() {

        viewModel.clientes.observe(
            viewLifecycleOwner
        ) { clientes ->

            listaCompletaClientes =
                clientes

            clientesAdapter.actualizarLista(
                clientes
            )

            actualizarCantidadClientes(
                clientes.size
            )
        }

        viewModel.uiState.observe(
            viewLifecycleOwner
        ) { estado ->

            when (estado) {

                is ClientesViewModel.ClientesUiState.Error -> {

                    Toast.makeText(
                        requireContext(),
                        estado.mensaje,
                        Toast.LENGTH_LONG
                    ).show()
                }

                else -> Unit
            }
        }
    }

    private fun cargarClientes() {

        lifecycleScope.launch {

            val token =
                tokenManager.obtenerBearer()

            viewModel.cargarClientes(
                token
            )
        }
    }

    private fun configurarBuscador() {

        etBuscarCliente
            .addTextChangedListener { texto ->

                val busqueda =
                    texto
                        ?.toString()
                        ?.trim()
                        ?.lowercase()
                        .orEmpty()

                val listaFiltrada =

                    if (busqueda.isEmpty()) {

                        listaCompletaClientes

                    } else {

                        listaCompletaClientes.filter {
                                cliente ->

                            cliente.nombre
                                .lowercase()
                                .contains(busqueda) ||

                                    cliente.clave
                                        .lowercase()
                                        .contains(busqueda)
                        }
                    }

                clientesAdapter.actualizarLista(
                    listaFiltrada
                )

                actualizarCantidadClientes(
                    listaFiltrada.size
                )
            }
    }

    private fun configurarBotones() {

        btnNuevoCliente.setOnClickListener {

            Toast.makeText(
                requireContext(),
                "Nuevo atleta",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun seleccionarCliente(
        cliente: Cliente
    ) {

        viewModel.seleccionarCliente(
            cliente
        )

        Toast.makeText(
            requireContext(),
            "Atleta: ${cliente.nombre}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun actualizarCantidadClientes(
        cantidad: Int
    ) {

        tvCantidadClientes.text =

            if (cantidad == 1) {

                "1 atleta activo"

            } else {

                "$cantidad atletas activos"

            }
    }
}