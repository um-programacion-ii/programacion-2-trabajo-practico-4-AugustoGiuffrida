package um.programacion2.Gestion_Biblioteca.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.programacion2.Gestion_Biblioteca.models.Libro;
import um.programacion2.Gestion_Biblioteca.models.Prestamo;
import um.programacion2.Gestion_Biblioteca.models.Usuario;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestPrestamoRepositorio {

    private PrestamoRepositorioImpl prestamoRepositorio;

    @BeforeEach
    void setUp() {
        prestamoRepositorio = new PrestamoRepositorioImpl();
    }

    @Test
    void save_ShouldAssignIdAndStoreLoan() {
        Prestamo prestamo = new Prestamo();
        Prestamo guardado = prestamoRepositorio.save(prestamo);

        assertNotNull(guardado.getId());
        assertTrue(prestamoRepositorio.existsById(guardado.getId()));
    }

    @Test
    void save_ShouldReturnLoanWithExpectedData() {
        Libro libro = new Libro();
        Usuario usuario = new Usuario();
        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaDevolucion = fechaPrestamo.plusDays(7);

        Prestamo prestamo = new Prestamo(null, libro, usuario, fechaPrestamo, fechaDevolucion);
        Prestamo guardado = prestamoRepositorio.save(prestamo);

        assertNotNull(guardado.getId());
        assertEquals(libro, guardado.getLibro());
        assertEquals(usuario, guardado.getUsuario());
        assertEquals(fechaPrestamo, guardado.getFechaPrestamo());
        assertEquals(fechaDevolucion, guardado.getFechaDevolucion());
    }

    @Test
    void save_ShouldHandleLoanWithNullFields() {
        Prestamo prestamo = new Prestamo(null, null, null, null, null);
        Prestamo guardado = prestamoRepositorio.save(prestamo);

        assertNotNull(guardado.getId());
        assertNull(guardado.getLibro());
        assertNull(guardado.getUsuario());
        assertNull(guardado.getFechaPrestamo());
        assertNull(guardado.getFechaDevolucion());
    }

    @Test
    void findById_ShouldReturnLoanIfExists() {
        Prestamo prestamo = new Prestamo();
        Prestamo guardado = prestamoRepositorio.save(prestamo);

        Optional<Prestamo> encontrado = prestamoRepositorio.findById(guardado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(guardado.getId(), encontrado.get().getId());
    }

    @Test
    void findById_ShouldReturnEmptyIfNotExists() {
        assertTrue(prestamoRepositorio.findById(999L).isEmpty());
    }

    @Test
    void findAll_ShouldReturnAllLoan() {
        prestamoRepositorio.save(new Prestamo());
        prestamoRepositorio.save(new Prestamo());

        assertEquals(2, prestamoRepositorio.findAll().size());
    }

    @Test
    void deleteById_ShouldRemoveLoan() {
        Prestamo prestamo = new Prestamo();
        Prestamo guardado = prestamoRepositorio.save(prestamo);
        prestamoRepositorio.deleteById(guardado.getId());

        assertFalse(prestamoRepositorio.existsById(guardado.getId()));
    }
}
