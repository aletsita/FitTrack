package com.example.proyecto.ui.clientes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.data.model.Cliente

class ClientesAdapter(
    private var clientes: List<Cliente>,
    private val onClienteClick: (Cliente) -> Unit
) : RecyclerView.Adapter<ClientesAdapter.ClienteViewHolder>() {

    inner class ClienteViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvInicialCliente: TextView =
            itemView.findViewById(
                R.id.tvInicialCliente
            )

        private val tvNombreCliente: TextView =
            itemView.findViewById(
                R.id.tvNombreCliente
            )

        private val tvClaveCliente: TextView =
            itemView.findViewById(
                R.id.tvClaveCliente
            )

        private val tvEdadCliente: TextView =
            itemView.findViewById(
                R.id.tvEdadCliente
            )

        fun bind(
            cliente: Cliente
        ) {

            tvInicialCliente.text =
                cliente.nombre
                    .firstOrNull()
                    ?.uppercase()
                    ?: "A"

            tvNombreCliente.text =
                cliente.nombre

            tvClaveCliente.text =
                "ID atleta • ${cliente.clave}"

            tvEdadCliente.text =
                "${cliente.edad} años"

            itemView.setOnClickListener {

                onClienteClick(cliente)

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClienteViewHolder {

        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_cliente,
                    parent,
                    false
                )

        return ClienteViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ClienteViewHolder,
        position: Int
    ) {

        holder.bind(
            clientes[position]
        )
    }

    override fun getItemCount(): Int {

        return clientes.size
    }

    fun actualizarLista(
        nuevaLista: List<Cliente>
    ) {

        clientes =
            nuevaLista

        notifyDataSetChanged()
    }
}