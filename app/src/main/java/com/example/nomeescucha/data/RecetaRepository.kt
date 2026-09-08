package com.example.nomeescucha.data

import com.example.nomeescucha.data.WeatherResponse
import com.example.nomeescucha.data.RetrofitClient

class RecetaRepository(private val recetaDao: RecetaDao) {

    suspend fun obtenerRecetas() = recetaDao.obtenerRecetas()

    suspend fun insertarReceta(receta: Receta) =
        recetaDao.insertarReceta(receta)

    suspend fun eliminarReceta(receta: Receta) =
        recetaDao.eliminarReceta(receta)

    suspend fun actualizarReceta(receta: Receta) =
        recetaDao.actualizarReceta(receta)


    // -------------------------------
    // API EXTERNA: Open-Meteo
    // -------------------------------
    suspend fun obtenerClima(): WeatherResponse {
        return RetrofitClient.api.getCurrentWeather(
            lat = -33.45,   // Santiago como ejemplo
            lon = -70.66
        )
    }
}

