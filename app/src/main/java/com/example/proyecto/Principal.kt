package com.example.proyecto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class Principal : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_principal)

        inicializarComponentes()
        configurarViewPager()
        configurarNavegacionInferior()
    }

    private fun inicializarComponentes() {
        viewPager = findViewById(R.id.viewPager)
        bottomNavigation = findViewById(R.id.bottomNavigation)
    }

    private fun configurarViewPager() {
        viewPager.adapter = ViewPagerAdapter(this)

        viewPager.offscreenPageLimit = 4

        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    val itemId = when (position) {
                        PANTALLA_INICIO -> R.id.navInicio
                        PANTALLA_CLIENTES -> R.id.navClientes
                        PANTALLA_RUTINAS -> R.id.navRutinas
                        PANTALLA_PROGRESO -> R.id.navProgreso
                        PANTALLA_USUARIOS -> R.id.navUsuarios
                        else -> R.id.navInicio
                    }

                    bottomNavigation.selectedItemId = itemId
                }
            }
        )
    }

    private fun configurarNavegacionInferior() {
        bottomNavigation.setOnItemSelectedListener { item ->

            val posicion = when (item.itemId) {
                R.id.navInicio -> PANTALLA_INICIO
                R.id.navClientes -> PANTALLA_CLIENTES
                R.id.navRutinas -> PANTALLA_RUTINAS
                R.id.navProgreso -> PANTALLA_PROGRESO
                R.id.navUsuarios -> PANTALLA_USUARIOS
                else -> PANTALLA_INICIO
            }

            viewPager.setCurrentItem(posicion, false)

            true
        }
    }

    fun irAPantalla(posicion: Int) {
        if (posicion in 0 until TOTAL_PANTALLAS) {
            viewPager.setCurrentItem(posicion, true)
        }
    }

    companion object {
        const val PANTALLA_INICIO = 0
        const val PANTALLA_CLIENTES = 1
        const val PANTALLA_RUTINAS = 2
        const val PANTALLA_PROGRESO = 3
        const val PANTALLA_USUARIOS = 4
        const val TOTAL_PANTALLAS = 5
    }
}