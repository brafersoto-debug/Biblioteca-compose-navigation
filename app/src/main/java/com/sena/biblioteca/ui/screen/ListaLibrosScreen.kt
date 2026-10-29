package com.sena.biblioteca.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sena.biblioteca.viewmodel.LibrosViewModel
import com.sena.biblioteca.ui.section.LibroItemSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaLibrosScreen(
    viewModel: LibrosViewModel,
    onVolverClick: () -> Unit,
    onLibroClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    categoriaInicial: String = "Todas"
) {
    val libros by viewModel.libros.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de Libros") },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(libros) { libro ->
                LibroItemSection(
                    libro = libro,
                    onClick = { onLibroClick(libro.id) }
                )
            }
        }
    }
}
