package com.example.nomeescucha.ui.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.nomeescucha.ui.RecetaViewModel

import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.graphicsLayer
import com.example.nomeescucha.ui.theme.PastelBlue
import com.example.nomeescucha.ui.theme.PastelPurple
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarRecetaScreen(navController: NavController, viewModel: RecetaViewModel) {

    val nombre by viewModel.nombre.collectAsState()
    val descripcion by viewModel.descripcion.collectAsState()
    val imagenUri by viewModel.imagenUri.collectAsState()

    val nombreError by viewModel.nombreError.collectAsState()
    val descripcionError by viewModel.descripcionError.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.setImagen(uri)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar receta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = nombre,
                onValueChange = { viewModel.nombre.value = it },
                label = { Text("Nombre") },
                isError = nombreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (nombreError != null) Text(nombreError!!, color = MaterialTheme.colorScheme.error)

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = { viewModel.descripcion.value = it },
                label = { Text("Descripción") },
                isError = descripcionError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (descripcionError != null) Text(descripcionError!!, color = MaterialTheme.colorScheme.error)

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = PastelPurple
                ),
                onClick = { launcher.launch("image/*") }) {
                Text("Seleccionar imagen")
            }

            imagenUri?.let {
                Spacer(modifier = Modifier.height(10.dp))
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth(),
                // 🎨 Definición ÚNICA de colores:
                colors = ButtonDefaults.buttonColors(
                    containerColor = PastelPurple // Color de fondo del botón
                )
            ) {
                Text("Cancelar")
            }

            Spacer(modifier = Modifier.height(20.dp))

            val scale = remember { Animatable(1f) }
            val scope = rememberCoroutineScope()

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = PastelPurple
                ),
                onClick = {
                    scope.launch {
                        scale.animateTo(0.9f)
                        scale.animateTo(1f)
                    }
                    viewModel.agregarReceta()
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(scaleX = scale.value, scaleY = scale.value)
            ) {
                Text("Guardar")
            }
        }
    }
}


