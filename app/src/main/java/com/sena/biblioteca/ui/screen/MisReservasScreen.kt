package com.sena.biblioteca.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sena.biblioteca.model.EstadoReserva
import com.sena.biblioteca.viewmodel.ReservasViewModel
import com.sena.biblioteca.ui.section.ReservaItemSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisReservasScreen(
    viewModel: ReservasViewModel,
    onVolverClick: () -> Unit,
    onReservaClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val reservas by viewModel.reservas.collectAsState()
    var tabSeleccionada by remember { mutableIntStateOf(0) } // 0 = Activas, 1 = Historial

    val reservasMostradas = if (tabSeleccionada == 0) {
        reservas.filter { it.estado == EstadoReserva.ACTIVA }
    } else {
        reservas.filter { it.estado != EstadoReserva.ACTIVA }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Mis Reservas") },
                navigationIcon = {
                    IconButton(onClick = onVolverClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PrimaryTabRow(selectedTabIndex = tabSeleccionada) {
                Tab(
                    selected = tabSeleccionada == 0,
                    onClick = { tabSeleccionada = 0 },
                    text = { Text("Activas (${reservas.count { it.estado == EstadoReserva.ACTIVA }})") }
                )
                Tab(
                    selected = tabSeleccionada == 1,
                    onClick = { tabSeleccionada = 1 },
                    text = { Text("Historial (${reservas.count { it.estado != EstadoReserva.ACTIVA }})") }
                )
            }

            if (reservasMostradas.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (tabSeleccionada == 0) "No tienes reservas activas en este momento." else "No tienes reservas en tu historial.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(reservasMostradas) { reserva ->
                        ReservaItemSection(
                            reserva = reserva,
                            onClick = { onReservaClick(reserva.id) },
                            onVerReservaClick = { onReservaClick(reserva.id) }
                        )
                    }
                }
            }
        }
    }
}
