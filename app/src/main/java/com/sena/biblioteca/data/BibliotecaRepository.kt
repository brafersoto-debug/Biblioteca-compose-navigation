package com.sena.biblioteca.data

import com.sena.biblioteca.model.EstadoReserva
import com.sena.biblioteca.model.Libro
import com.sena.biblioteca.model.Reserva
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BibliotecaRepository {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros.asStateFlow()

    private val _reservas = MutableStateFlow<List<Reserva>>(emptyList())
    val reservas: StateFlow<List<Reserva>> = _reservas.asStateFlow()

    val categorias = listOf("Todas", "Ficción", "Tecnología", "Historia", "Ciencia", "Filosofía")

    init {
        cargarDatosIniciales()
    }

    private fun cargarDatosIniciales() {
        _libros.value = listOf(
            Libro(
                id = "1",
                titulo = "Cien años de soledad",
                autor = "Gabriel García Márquez",
                categoria = "Ficción",
                sinopsis = "Una de las obras maestras de la literatura hispanoamericana y universal. Narra la historia de la familia Buendía a lo largo de siete generaciones en el pueblo ficticio de Macondo.",
                disponible = true,
                paginas = 471,
                anioPublicacion = 1967,
                editorial = "Editorial Sudamericana",
                isbn = "978-0307474728",
                calificacion = 4.9,
                imagenUrl = "https://covers.openlibrary.org/b/isbn/9780307474728-L.jpg"
            ),
            Libro(
                id = "2",
                titulo = "Clean Code",
                autor = "Robert C. Martin",
                categoria = "Tecnología",
                sinopsis = "Incluso un código malo puede funcionar. Pero si el código no es limpio, puede acabar con una empresa de desarrollo. Una guía de estilo para escribir código elegante y mantenible.",
                disponible = true,
                paginas = 464,
                anioPublicacion = 2008,
                editorial = "Prentice Hall",
                isbn = "978-0132350884",
                calificacion = 4.8,
                imagenUrl = "https://covers.openlibrary.org/b/isbn/9780132350884-L.jpg"
            ),
            Libro(
                id = "3",
                titulo = "Sapiens: De animales a dioses",
                autor = "Yuval Noah Harari",
                categoria = "Historia",
                sinopsis = "Un recorrido fascinante por la historia de la humanidad, desde los primeros homínidos que caminaron sobre la Tierra hasta las revoluciones tecnológica y científica.",
                disponible = false,
                paginas = 496,
                anioPublicacion = 2014,
                editorial = "Debate",
                isbn = "978-8499926223",
                calificacion = 4.7,
                imagenUrl = "https://covers.openlibrary.org/b/isbn/9780062316097-L.jpg"
            ),
            Libro(
                id = "4",
                titulo = "El Principito",
                autor = "Antoine de Saint-Exupéry",
                categoria = "Ficción",
                sinopsis = "Un cuento poético que viene acompañado de ilustraciones hechas con acuarelas por el mismo autor. Trata de la historia de un pequeño príncipe que parte de su asteroide.",
                disponible = true,
                paginas = 96,
                anioPublicacion = 1943,
                editorial = "Reynal & Hitchcock",
                isbn = "978-0156013987",
                calificacion = 4.9,
                imagenUrl = "https://covers.openlibrary.org/b/isbn/9780156013987-L.jpg"
            ),
            Libro(
                id = "5",
                titulo = "Breves respuestas a las grandes preguntas",
                autor = "Stephen Hawking",
                categoria = "Ciencia",
                sinopsis = "El reconocido físico teórico Stephen Hawking ofrece sus reflexiones sobre las preguntas más profundas sobre el universo, el origen de la vida y el futuro de la humanidad.",
                disponible = true,
                paginas = 256,
                anioPublicacion = 2018,
                editorial = "Crítica",
                isbn = "978-8491990413",
                calificacion = 4.6,
                imagenUrl = "https://covers.openlibrary.org/b/isbn/9781984819192-L.jpg"
            ),
            Libro(
                id = "6",
                titulo = "Meditaciones",
                autor = "Marco Aurelio",
                categoria = "Filosofía",
                sinopsis = "Una serie de escritos personales del emperador romano Marco Aurelio sobre el estoicismo, la autodisciplina, la virtud y la resiliencia personal.",
                disponible = true,
                paginas = 208,
                anioPublicacion = 180,
                editorial = "Gredos",
                isbn = "978-8424938222",
                calificacion = 4.8,
                imagenUrl = "https://covers.openlibrary.org/b/isbn/9780140449334-L.jpg"
            )
        )

        val hoy = LocalDate.now()
        _reservas.value = listOf(
            Reserva(
                id = "RES-101",
                libroId = "3",
                libroTitulo = "Sapiens: De animales a dioses",
                libroAutor = "Yuval Noah Harari",
                libroCategoria = "Historia",
                libroImagenUrl = "https://covers.openlibrary.org/b/isbn/9780062316097-L.jpg",
                fechaReserva = hoy.minusDays(5).format(dateFormatter),
                fechaDevolucion = hoy.plusDays(9).format(dateFormatter),
                estado = EstadoReserva.ACTIVA,
                diasRestantes = 9,
                renovacionesRealizadas = 0
            )
        )
    }

    fun obtenerLibroPorId(id: String): Libro? {
        return _libros.value.find { it.id == id }
    }

    fun obtenerReservaPorId(id: String): Reserva? {
        return _reservas.value.find { it.id == id }
    }

    fun confirmarReserva(libroId: String, diasPrestamo: Int): Reserva? {
        val libro = obtenerLibroPorId(libroId) ?: return null
        val hoy = LocalDate.now()
        val fechaDev = hoy.plusDays(diasPrestamo.toLong())

        val nuevaReserva = Reserva(
            id = "RES-${(100..999).random()}",
            libroId = libro.id,
            libroTitulo = libro.titulo,
            libroAutor = libro.autor,
            libroCategoria = libro.categoria,
            libroImagenUrl = libro.imagenUrl,
            fechaReserva = hoy.format(dateFormatter),
            fechaDevolucion = fechaDev.format(dateFormatter),
            estado = EstadoReserva.ACTIVA,
            diasRestantes = diasPrestamo,
            renovacionesRealizadas = 0
        )

        _libros.value = _libros.value.map {
            if (it.id == libroId) it.copy(disponible = false) else it
        }

        _reservas.value = listOf(nuevaReserva) + _reservas.value
        return nuevaReserva
    }

    fun renovarReserva(reservaId: String, diasAdicionales: Int): Boolean {
        val reservaActual = obtenerReservaPorId(reservaId) ?: return false
        if (reservaActual.renovacionesRealizadas >= reservaActual.maxRenovaciones) {
            return false
        }

        val hoy = LocalDate.now()
        val fechaDevActual = try {
            LocalDate.parse(reservaActual.fechaDevolucion, dateFormatter)
        } catch (_: Exception) {
            hoy.plusDays(7)
        }
        val nuevaFechaDev = fechaDevActual.plusDays(diasAdicionales.toLong())

        _reservas.value = _reservas.value.map { r ->
            if (r.id == reservaId) {
                r.copy(
                    fechaDevolucion = nuevaFechaDev.format(dateFormatter),
                    diasRestantes = r.diasRestantes + diasAdicionales,
                    renovacionesRealizadas = r.renovacionesRealizadas + 1
                )
            } else r
        }
        return true
    }

    fun cancelarReserva(reservaId: String) {
        val reserva = obtenerReservaPorId(reservaId) ?: return
        _reservas.value = _reservas.value.map { r ->
            if (r.id == reservaId) r.copy(estado = EstadoReserva.CANCELADA) else r
        }
        _libros.value = _libros.value.map { l ->
            if (l.id == reserva.libroId) l.copy(disponible = true) else l
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: BibliotecaRepository? = null

        fun getInstance(): BibliotecaRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = BibliotecaRepository()
                INSTANCE = instance
                instance
            }
        }
    }
}
