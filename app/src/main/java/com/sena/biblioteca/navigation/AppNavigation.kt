package com.sena.biblioteca.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sena.biblioteca.ui.screen.ConfirmacionScreen
import com.sena.biblioteca.ui.screen.DetalleLibroScreen
import com.sena.biblioteca.ui.screen.DetalleReservaScreen
import com.sena.biblioteca.ui.screen.HomeScreen
import com.sena.biblioteca.ui.screen.ListaLibrosScreen
import com.sena.biblioteca.ui.screen.MisReservasScreen
import com.sena.biblioteca.ui.screen.RenovacionExitosaScreen
import com.sena.biblioteca.ui.screen.RenovarReservaScreen
import com.sena.biblioteca.ui.screen.ReservaExitosaScreen
import com.sena.biblioteca.viewmodel.LibrosViewModel
import com.sena.biblioteca.viewmodel.ReservasViewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    librosViewModel: LibrosViewModel = viewModel(),
    reservasViewModel: ReservasViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Rutas.Home.ruta,
        modifier = modifier
    ) {
        composable(route = Rutas.Home.ruta) {
            HomeScreen(
                viewModel = librosViewModel,
                onVerTodosClick = {
                    navController.navigate(Rutas.ListaLibros.crearRuta("Todas"))
                },
                onLibroClick = { libroId ->
                    navController.navigate(Rutas.DetalleLibro.crearRuta(libroId))
                },
                onMisReservasClick = {
                    navController.navigate(Rutas.MisReservas.ruta)
                }
            )
        }

        composable(
            route = Rutas.ListaLibros.ruta,
            arguments = listOf(
                navArgument("categoria") {
                    type = NavType.StringType
                    defaultValue = "Todas"
                }
            )
        ) { backStackEntry ->
            val categoria = backStackEntry.arguments?.getString("categoria") ?: "Todas"
            ListaLibrosScreen(
                viewModel = librosViewModel,
                categoriaInicial = categoria,
                onVolverClick = { navController.popBackStack() },
                onLibroClick = { libroId ->
                    navController.navigate(Rutas.DetalleLibro.crearRuta(libroId))
                }
            )
        }

        composable(
            route = Rutas.DetalleLibro.ruta,
            arguments = listOf(
                navArgument("libroId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val libroId = backStackEntry.arguments?.getString("libroId") ?: ""
            DetalleLibroScreen(
                libroId = libroId,
                viewModel = librosViewModel,
                onVolverClick = { navController.popBackStack() },
                onReservarClick = { id ->
                    navController.navigate(Rutas.ConfirmacionReserva.crearRuta(id))
                }
            )
        }

        composable(
            route = Rutas.ConfirmacionReserva.ruta,
            arguments = listOf(
                navArgument("libroId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val libroId = backStackEntry.arguments?.getString("libroId") ?: ""
            ConfirmacionScreen(
                libroId = libroId,
                librosViewModel = librosViewModel,
                reservasViewModel = reservasViewModel,
                onVolverClick = { navController.popBackStack() },
                onReservaExitosa = { reservaId ->
                    navController.navigate(Rutas.ReservaExitosa.crearRuta(reservaId)) {
                        popUpTo(Rutas.Home.ruta)
                    }
                }
            )
        }

        composable(
            route = Rutas.ReservaExitosa.ruta,
            arguments = listOf(
                navArgument("reservaId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val reservaId = backStackEntry.arguments?.getString("reservaId") ?: ""
            ReservaExitosaScreen(
                reservaId = reservaId,
                viewModel = reservasViewModel,
                onIrAMisReservasClick = {
                    navController.navigate(Rutas.MisReservas.ruta) {
                        popUpTo(Rutas.Home.ruta)
                    }
                },
                onIrAlInicioClick = {
                    navController.navigate(Rutas.Home.ruta) {
                        popUpTo(Rutas.Home.ruta) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Rutas.MisReservas.ruta) {
            MisReservasScreen(
                viewModel = reservasViewModel,
                onVolverClick = { navController.popBackStack() },
                onReservaClick = { reservaId ->
                    navController.navigate(Rutas.DetalleReserva.crearRuta(reservaId))
                }
            )
        }

        composable(
            route = Rutas.DetalleReserva.ruta,
            arguments = listOf(
                navArgument("reservaId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val reservaId = backStackEntry.arguments?.getString("reservaId") ?: ""
            DetalleReservaScreen(
                reservaId = reservaId,
                viewModel = reservasViewModel,
                onVolverClick = { navController.popBackStack() },
                onRenovarClick = { id ->
                    navController.navigate(Rutas.RenovarReserva.crearRuta(id))
                }
            )
        }

        composable(
            route = Rutas.RenovarReserva.ruta,
            arguments = listOf(
                navArgument("reservaId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val reservaId = backStackEntry.arguments?.getString("reservaId") ?: ""
            RenovarReservaScreen(
                reservaId = reservaId,
                viewModel = reservasViewModel,
                onVolverClick = { navController.popBackStack() },
                onRenovacionExitosa = { id ->
                    navController.navigate(Rutas.RenovacionExitosa.crearRuta(id)) {
                        popUpTo(Rutas.MisReservas.ruta)
                    }
                }
            )
        }

        composable(
            route = Rutas.RenovacionExitosa.ruta,
            arguments = listOf(
                navArgument("reservaId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val reservaId = backStackEntry.arguments?.getString("reservaId") ?: ""
            RenovacionExitosaScreen(
                reservaId = reservaId,
                viewModel = reservasViewModel,
                onIrAMisReservasClick = {
                    navController.navigate(Rutas.MisReservas.ruta) {
                        popUpTo(Rutas.Home.ruta)
                    }
                },
                onIrAlInicioClick = {
                    navController.navigate(Rutas.Home.ruta) {
                        popUpTo(Rutas.Home.ruta) { inclusive = true }
                    }
                }
            )
        }
    }
}
