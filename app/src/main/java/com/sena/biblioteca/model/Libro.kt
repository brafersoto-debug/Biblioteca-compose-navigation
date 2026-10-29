package com.sena.biblioteca.model

data class Libro(
    val id: String,
    val titulo: String,
    val autor: String,
    val categoria: String,
    val sinopsis: String,
    val disponible: Boolean = true,
    val paginas: Int,
    val anioPublicacion: Int,
    val editorial: String,
    val isbn: String,
    val calificacion: Double,
    val imagenUrl: String = ""
)
