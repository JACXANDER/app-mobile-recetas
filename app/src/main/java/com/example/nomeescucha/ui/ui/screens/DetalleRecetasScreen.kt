package com.example.nomeescucha.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.nomeescucha.data.Receta
import com.example.nomeescucha.ui.RecetaViewModel

import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.nomeescucha.ui.theme.PastelBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleRecetaScreen(navController: NavController, viewModel: RecetaViewModel, id: Int) {

    val recetas by viewModel.recetas.collectAsState()
    val receta: Receta? = recetas.find { it.id == id }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de receta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        receta?.let {

            if (it.imagenUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(it.imagenUri),
                    contentDescription = it.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(it.nombre, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(12.dp))
            Text(it.descripcion)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = PastelBlue
                ),
                onClick = { viewModel.eliminarReceta(it); navController.popBackStack() }
            ) {
                Text("Eliminar receta")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = PastelBlue
            ),
            onClick = { navController.popBackStack() }) {
            Text("Volver")
        }

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = PastelBlue
            ),
            onClick = { navController.navigate("editar/$id") }) {
            Text("Editar")
        }

    }
    }

}
