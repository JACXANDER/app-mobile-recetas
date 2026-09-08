package com.example.nomeescucha.ui

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nomeescucha.MainApplication
import com.example.nomeescucha.data.Receta
import com.example.nomeescucha.data.RecetaRepository
import com.example.nomeescucha.data.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import com.example.nomeescucha.R

import androidx.annotation.RequiresPermission
import android.Manifest



class RecetaViewModel(private val repository: RecetaRepository) : ViewModel() {

    // -------------------------------
    // LISTA DE RECETAS (STATEFLOW)
    // -------------------------------
    private val _recetas = MutableStateFlow<List<Receta>>(emptyList())
    val recetas: StateFlow<List<Receta>> = _recetas

    // -------------------------------
    // API EXTERNA – CLIMA
    // -------------------------------
    var clima = MutableStateFlow<Double?>(null)
    var climaDescripcion = MutableStateFlow("Cargando clima...")

    fun cargarClima() {
        viewModelScope.launch {
            try {
                val data: WeatherResponse = repository.obtenerClima()
                val temp = data.current_weather.temperature

                clima.value = temp
                climaDescripcion.value = "Temperatura actual: $temp°C"
            } catch (e: Exception) {
                climaDescripcion.value = "No se pudo obtener el clima"
            }
        }
    }

    // -------------------------------
    // CAMPOS DEL FORMULARIO
    // -------------------------------
    var nombre = MutableStateFlow("")
    var descripcion = MutableStateFlow("")
    var imagenUri = MutableStateFlow<Uri?>(null)

    // -------------------------------
    // ERRORES DE VALIDACIÓN
    // -------------------------------
    var nombreError = MutableStateFlow<String?>(null)
    var descripcionError = MutableStateFlow<String?>(null)

    // -------------------------------
    // CARGAR RECETAS DESDE ROOM
    // -------------------------------
    fun cargarRecetas() {
        viewModelScope.launch {
            _recetas.value = repository.obtenerRecetas()
        }
    }

    // -------------------------------
    // VALIDACIONES DEL FORMULARIO
    // -------------------------------
    private fun validarFormulario(): Boolean {
        var valido = true

        if (nombre.value.isBlank()) {
            nombreError.value = "El nombre es obligatorio"
            valido = false
        } else nombreError.value = null

        if (descripcion.value.isBlank()) {
            descripcionError.value = "La descripción es obligatoria"
            valido = false
        } else descripcionError.value = null

        return valido
    }

    // -------------------------------
    // RECETAS POR DEFECTO
    // -------------------------------
    fun inicializarRecetasPorDefecto() {
        viewModelScope.launch {
            val lista = repository.obtenerRecetas()

            if (lista.isEmpty()) {
                val ctx = MainApplication.instance

                val uriChocolate = "android.resource://${ctx.packageName}/drawable/pastelchocolate"
                val uriLimon = "android.resource://${ctx.packageName}/drawable/pastellimon"
                val uriZanahoria = "android.resource://${ctx.packageName}/drawable/pastelzanahoria"

                val descZanahoria = """
                    Ingredientes:
                    - 1 taza de nueces picadas
                    - 450 g de zanahoria rallada
                    - 3 huevos
                    - ½ taza de leche con limón
                    - 2 tazas de azúcar
                    - 1 taza de aceite
                    - 3 tazas de harina
                
                    Preparación:
                    1. Precalienta el horno a 180 °C.
                """.trimIndent()

                val descChocolate = """
                    Ingredientes:
                    - Mantequilla
                    - Leche condensada
                    - Huevos
                    - Harina
                    - Chocolate fundido
                """.trimIndent()

                val descLimon = """
                    Ingredientes:
                    - Mantequilla
                    - Azúcar
                    - Huevos
                    - Harina
                    - Limón
                """.trimIndent()

                repository.insertarReceta(
                    Receta(
                        nombre = "Pastel de Chocolate",
                        descripcion = descChocolate,
                        imagenUri = uriChocolate
                    )
                )

                repository.insertarReceta(
                    Receta(
                        nombre = "Pastel de Limón",
                        descripcion = descLimon,
                        imagenUri = uriLimon
                    )
                )

                repository.insertarReceta(
                    Receta(
                        nombre = "Pastel de Zanahoria",
                        descripcion = descZanahoria,
                        imagenUri = uriZanahoria
                    )
                )

            }
        }
    }

    // -------------------------------
    // INSERTAR NUEVA RECETA
    // -------------------------------
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun agregarReceta() {
        if (!validarFormulario()) return

        viewModelScope.launch {
            val receta = Receta(
                nombre = nombre.value,
                descripcion = descripcion.value,
                imagenUri = imagenUri.value?.toString()
            )

            repository.insertarReceta(receta)
            cargarRecetas()
            limpiarFormulario()

            enviarNotificacion("Receta agregada","se agrego exitoso")

            vibrar()
        }
    }


    // -------------------------------
    // ELIMINAR RECETA
    // -------------------------------
    fun eliminarReceta(receta: Receta) {
        viewModelScope.launch {
            repository.eliminarReceta(receta)
            cargarRecetas()
        }
    }

    // -------------------------------
    // LIMPIAR FORMULARIO
    // -------------------------------
    private fun limpiarFormulario() {
        nombre.value = ""
        descripcion.value = ""
        imagenUri.value = null
        nombreError.value = null
        descripcionError.value = null
    }

    // -------------------------------
    // ASIGNAR IMAGEN
    // -------------------------------
    fun setImagen(uri: Uri?) {
        imagenUri.value = uri
    }

    // -------------------------------
    // RECURSO NATIVO 1: NOTIFICACIONES
    // -------------------------------
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun enviarNotificacion(titulo: String, mensaje: String) {
        val context = MainApplication.instance

        val noti = NotificationCompat.Builder(context, "recetas_channel")
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setSmallIcon(R.drawable.baseline_done_24)
            .setAutoCancel(true)
            .build()

        val manager = NotificationManagerCompat.from(context)
        manager.notify((1..9999).random(), noti)
    }
    // -------------------------------
    // cargar recet
    // -------------------------------
    fun cargarRecetaParaEditar(receta: Receta) {
        nombre.value = receta.nombre
        descripcion.value = receta.descripcion
        imagenUri.value = receta.imagenUri?.let { Uri.parse(it) }
    }

    // -------------------------------
    // RECURSO NATIVO 2: VIBRACIÓN
    // -------------------------------

    fun actualizarReceta(id: Int) {
        val nombreVal = nombre.value.trim()
        val descVal = descripcion.value.trim()

        if (nombreVal.isBlank() || descVal.isBlank()) return

        val recetaActualizada = Receta(
            id = id,
            nombre = nombreVal,
            descripcion = descVal,
            imagenUri = imagenUri.value?.toString()
        )

        viewModelScope.launch {
            repository.actualizarReceta(recetaActualizada)
            cargarRecetas()
        }
    }




    // -------------------------------
    // RECURSO NATIVO 2: VIBRACIÓN
    // -------------------------------
    @Suppress("DEPRECATION")
    private fun vibrar() {
        val context = MainApplication.instance
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    120,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            vibrator.vibrate(120)
        }
    }
}


