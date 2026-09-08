package com.example.nomeescucha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.ViewModelProvider
import com.example.nomeescucha.data.RecetaDatabase
import com.example.nomeescucha.data.RecetaRepository
import com.example.nomeescucha.ui.AppNavigation
import com.example.nomeescucha.ui.RecetaViewModel
import com.example.nomeescucha.ui.RecetaViewModelFactory
import com.example.nomeescucha.ui.theme.NomeescuchaTheme
import androidx.compose.ui.graphics.Color


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- Room ---
        val database = RecetaDatabase.getDatabase(this)
        val repository = RecetaRepository(database.recetaDao())

        // --- ViewModel ---
        val viewModelFactory = RecetaViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[RecetaViewModel::class.java]

        setContent {
            NomeescuchaTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFA67C63)// <-- Añadir FF para opacidad
                ) {
                    AppNavigation(navController, viewModel)
                }
            }
        }
    }
}

