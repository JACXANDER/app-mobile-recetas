
package com.example.nomeescucha.data

import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET("v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("hourly") hourly: String = "temperature_2m",
        @Query("current_weather") currentWeather: Boolean = true
    ): WeatherResponse
}


