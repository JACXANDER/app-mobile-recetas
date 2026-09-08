package com.example.nomeescucha.ui.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.nomeescucha.ui.RecetaViewModel
import com.example.nomeescucha.ui.theme.PastelBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarRecetaScreen(
    navController: NavController,
    viewModel: RecetaViewModel,
    recetaId: Int
) {
    val recetas = viewModel.recetas.collectAsState().value
    val receta = recetas.find { it.id == recetaId }

    // Cargar datos en el ViewModel
    LaunchedEffect(recetaId) {
        receta?.let { viewModel.cargarRecetaParaEditar(it) }
    }

    val nombre by viewModel.nombre.collectAsState()
    val descripcion by viewModel.descripcion.collectAsState()
    val imagenUri by viewModel.imagenUri.collectAsState()

    // Selector de imagen
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.setImagen(uri)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar receta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = nombre,
                onValueChange = { viewModel.nombre.value = it },
                label = { Text("Nombre de la receta") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = { viewModel.descripcion.value = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Botón seleccionar imagen
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = PastelBlue
                ),
                onClick = { launcher.launch("image/*") }) {
                Text("Cambiar imagen")
            }

            // Mostrar imagen actual
            imagenUri?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Botón actualizar
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = PastelBlue
                ),
                onClick = {
                    viewModel.actualizarReceta(recetaId)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar")
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Botón cancelar

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth(),
                // 🎨 Definición ÚNICA de colores:
                colors = ButtonDefaults.buttonColors(
                    containerColor = PastelBlue // Color de fondo del botón
                )
            ) {
                Text("Cancelar")
            }
        }
    }
}
