package com.example.nomeescucha.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nomeescucha.ui.ui.screens.AgregarRecetaScreen
import com.example.nomeescucha.ui.screens.DetalleRecetaScreen
import com.example.nomeescucha.ui.ui.screens.ListaRecetasScreen // <-- Debe ser como las demás
import com.example.nomeescucha.ui.ui.screens.EditarRecetaScreen
import com.example.nomeescucha.ui.RecetaViewModel




@Composable
fun AppNavigation(navController: NavHostController, viewModel: RecetaViewModel) {
    NavHost(
        navController = navController,
        startDestination = "lista"
    ) {
        composable("lista") {
            ListaRecetasScreen(navController, viewModel)
        }

        composable("detalle/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            DetalleRecetaScreen(navController, viewModel, id)
        }

        composable("agregar") {
            AgregarRecetaScreen(navController, viewModel)
        }

        composable("editar/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")!!.toInt()
            EditarRecetaScreen(navController, viewModel, id)
        }

    }
}
