package com.sena.biblioteca.viewmodel

import androidx.lifecycle.ViewModel
import com.sena.biblioteca.data.BibliotecaRepository
import com.sena.biblioteca.model.Libro
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LibrosViewModel(
    private val repository: BibliotecaRepository = BibliotecaRepository.getInstance()
) : ViewModel() {

    val libros: StateFlow<List<Libro>> = repository.libros

    private val _categoriaSeleccionada = MutableStateFlow("Todas")
    val categoriaSeleccionada: StateFlow<String> = _categoriaSeleccionada.asStateFlow()

    private val _busquedaQuery = MutableStateFlow("")
    val busquedaQuery: StateFlow<String> = _busquedaQuery.asStateFlow()

    val categorias = repository.categorias

    fun seleccionarCategoria(categoria: String) {
        _categoriaSeleccionada.value = categoria
    }

    fun buscarLibros(query: String) {
        _busquedaQuery.value = query
    }

    fun obtenerLibroPorId(id: String): Libro? {
        return repository.obtenerLibroPorId(id)
    }
}
