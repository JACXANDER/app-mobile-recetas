package com.example.nomeescucha.ui.ui.screens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape // <-- IMPORTANTE
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nomeescucha.data.Receta
import com.example.nomeescucha.ui.RecetaViewModel
import com.example.nomeescucha.ui.theme.PastelBlue
// Importa las constantes de color, si están en un archivo separado dentro del mismo paquete
import com.example.nomeescucha.ui.theme.PastelPink


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaRecetasScreen(navController: NavController, viewModel: RecetaViewModel) {

    val recetas by viewModel.recetas.collectAsState()
    val climaDescripcion by viewModel.climaDescripcion.collectAsState()

    // Cargar recetas y clima al abrir
    LaunchedEffect(Unit) {
        viewModel.inicializarRecetasPorDefecto()
        viewModel.cargarRecetas()
        viewModel.cargarClima()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Recetario de Pasteles")
                        Text(
                            text = climaDescripcion,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp) // Solo padding horizontal para dejar espacio a las Cards
        ) {

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = PastelBlue
                ),
                onClick = { navController.navigate("agregar") },
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text("Agregar receta")
            }

            LazyColumn {
                items(recetas) { receta ->
                    RecetaItem(receta) {
                        navController.navigate("detalle/${receta.id}")
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// FUNCIÓN RecetaItem CON LA CARD ESTILIZADA
// -------------------------------------------------------------

@Composable
fun RecetaItem(receta: Receta, onClick: () -> Unit) {

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically(initialOffsetY = { 40 }),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp) // <-- Padding alrededor de la Card
                .clickable { onClick() }, // <-- Permite hacer clic en la Card
            shape = RoundedCornerShape(16.dp), // <-- Esquinas redondeadas
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = PastelBlue // <-- ¡Color de fondo PasteL CELESTE!
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(receta.nombre, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                // Muestra los primeros 100 caracteres de la descripción
                Text(receta.descripcion.take(100) + "...")
            }
        }
    }
}

