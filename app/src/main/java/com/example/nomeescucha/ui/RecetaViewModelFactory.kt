package com.example.nomeescucha.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nomeescucha.data.RecetaRepository

class RecetaViewModelFactory(private val repository: RecetaRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecetaViewModel::class.java)) {
            return RecetaViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconocido")
    }
}
