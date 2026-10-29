package com.sena.biblioteca.viewmodel

import androidx.lifecycle.ViewModel
import com.sena.biblioteca.data.BibliotecaRepository
import com.sena.biblioteca.model.Reserva
import kotlinx.coroutines.flow.StateFlow

class ReservasViewModel(
    private val repository: BibliotecaRepository = BibliotecaRepository.getInstance()
) : ViewModel() {

    val reservas: StateFlow<List<Reserva>> = repository.reservas

    fun obtenerReservaPorId(id: String): Reserva? {
        return repository.obtenerReservaPorId(id)
    }

    fun confirmarReserva(libroId: String, diasPrestamo: Int): Reserva? {
        return repository.confirmarReserva(libroId, diasPrestamo)
    }

    fun renovarReserva(reservaId: String, diasAdicionales: Int): Boolean {
        return repository.renovarReserva(reservaId, diasAdicionales)
    }

    fun cancelarReserva(reservaId: String) {
        repository.cancelarReserva(reservaId)
    }
}
