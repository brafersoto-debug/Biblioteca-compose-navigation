package com.sena.biblioteca.model

enum class EstadoReserva(val label: String) {
    ACTIVA("Activa"),
    COMPLETADA("Completada"),
    VENCIDA("Vencida"),
    CANCELADA("Cancelada")
}

data class Reserva(
    val id: String,
    val libroId: String,
    val libroTitulo: String,
    val libroAutor: String,
    val libroCategoria: String,
    val libroImagenUrl: String = "",
    val fechaReserva: String,
    val fechaDevolucion: String,
    val estado: EstadoReserva = EstadoReserva.ACTIVA,
    val diasRestantes: Int,
    val renovacionesRealizadas: Int = 0,
    val maxRenovaciones: Int = 2
)
