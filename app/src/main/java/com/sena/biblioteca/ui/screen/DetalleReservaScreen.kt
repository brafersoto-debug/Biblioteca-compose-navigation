package com.sena.biblioteca.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sena.biblioteca.viewmodel.ReservasViewModel
import com.sena.biblioteca.model.EstadoReserva
import com.sena.biblioteca.ui.section.InfoReservaSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleReservaScreen(
    reservaId: String,
    viewModel: ReservasViewModel,
    onVolverClick: () -> Unit,
    onRenovarClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val reserva = viewModel.obtenerReservaPorId(reservaId)
    var mostrarDialogoCancelar by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Reserva") },
                navigationIcon = {
                    IconButton(onClick = onVolverClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (reserva != null && reserva.estado == EstadoReserva.ACTIVA) {
                Surface(
                    shadowElevation = 8.dp,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onRenovarClick(reserva.id) },
                            enabled = reserva.renovacionesRealizadas < reserva.maxRenovaciones,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Renovar Reserva")
                        }

                        OutlinedButton(
                            onClick = { mostrarDialogoCancelar = true },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text("Cancelar Reserva")
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        if (reserva == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No se encontró la reserva.")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                InfoReservaSection(reserva = reserva)
            }
        }

        if (mostrarDialogoCancelar && reserva != null) {
            AlertDialog(
                onDismissRequest = { mostrarDialogoCancelar = false },
                title = { Text("Cancelar Reserva") },
                text = { Text("¿Estás seguro de que deseas cancelar la reserva de '${reserva.libroTitulo}'?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.cancelarReserva(reserva.id)
                            mostrarDialogoCancelar = false
                            onVolverClick()
                        }
                    ) {
                        Text("Sí, cancelar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogoCancelar = false }) {
                        Text("No, mantener")
                    }
                }
            )
        }
    }
}
