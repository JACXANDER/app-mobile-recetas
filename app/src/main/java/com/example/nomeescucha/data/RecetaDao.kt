package com.example.nomeescucha.data

import androidx.room.*

@Dao
interface RecetaDao {

    @Query("SELECT * FROM recetas ORDER BY id DESC")
    suspend fun obtenerRecetas(): List<Receta>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarReceta(receta: Receta)

    @Delete
    suspend fun eliminarReceta(receta: Receta)

    @Update
    suspend fun actualizarReceta(receta: Receta)

}
