package com.example.proyecto

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.proyecto.ui.clientes.ClientesFragment
import com.example.proyecto.ui.dashboard.DashboardFragment
import com.example.proyecto.ui.progreso.ProgresoFragment
import com.example.proyecto.ui.rutinas.RutinasFragment
import com.example.proyecto.ui.usuarios.UsuariosFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DashboardFragment()
            1 -> ClientesFragment()
            2 -> RutinasFragment()
            3 -> ProgresoFragment()
            4 -> UsuariosFragment()
            else -> DashboardFragment()
        }
    }
}