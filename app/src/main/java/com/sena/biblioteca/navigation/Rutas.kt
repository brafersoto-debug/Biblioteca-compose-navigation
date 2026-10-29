package com.sena.biblioteca.navigation

sealed class Rutas(val ruta: String) {
    object Home : Rutas("home")
    object ListaLibros : Rutas("lista_libros?categoria={categoria}") {
        fun crearRuta(categoria: String = "Todas") = "lista_libros?categoria=$categoria"
    }
    object DetalleLibro : Rutas("detalle_libro/{libroId}") {
        fun crearRuta(libroId: String) = "detalle_libro/$libroId"
    }
    object ConfirmacionReserva : Rutas("confirmacion_reserva/{libroId}") {
        fun crearRuta(libroId: String) = "confirmacion_reserva/$libroId"
    }
    object ReservaExitosa : Rutas("reserva_exitosa/{reservaId}") {
        fun crearRuta(reservaId: String) = "reserva_exitosa/$reservaId"
    }
    object MisReservas : Rutas("mis_reservas")
    object DetalleReserva : Rutas("detalle_reserva/{reservaId}") {
        fun crearRuta(reservaId: String) = "detalle_reserva/$reservaId"
    }
    object RenovarReserva : Rutas("renovar_reserva/{reservaId}") {
        fun crearRuta(reservaId: String) = "renovar_reserva/$reservaId"
    }
    object RenovacionExitosa : Rutas("renovacion_exitosa/{reservaId}") {
        fun crearRuta(reservaId: String) = "renovacion_exitosa/$reservaId"
    }
}
