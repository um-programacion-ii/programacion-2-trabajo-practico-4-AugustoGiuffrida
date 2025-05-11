package um.programacion2.Gestion_Biblioteca.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.programacion2.Gestion_Biblioteca.enums.LibroEstado;
import um.programacion2.Gestion_Biblioteca.models.Libro;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestLibroRepositorio {
    private LibroRepositorioImpl libroRepositorio;

    @BeforeEach
    void setup(){
        libroRepositorio = new LibroRepositorioImpl();
    }

    @Test
    void save_ShouldAssignIdAndStoreBook() {
        Libro libro = new Libro(null, "978-9876543210", "Programación II", "Juan Pérez", LibroEstado.DISPONIBLE);
        Libro guardado = libroRepositorio.save(libro);

        assertNotNull(guardado.getId());
        assertEquals("Programación II", guardado.getTitulo());
        assertTrue(libroRepositorio.existsById(guardado.getId()));
    }

    @Test
    void findById_ShouldReturnBookIfExists() {
        Libro libro = new Libro(null, "978-9876543210", "Programación II", "Juan Pérez", LibroEstado.DISPONIBLE);
        Libro guardado = libroRepositorio.save(libro);

        Optional<Libro> encontrado = libroRepositorio.findById(guardado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Programación II", encontrado.get().getTitulo());
    }


    @Test
    void findByIsbn_ShouldReturnBookIfExists() {
        Libro libro = new Libro(null, "978-9876543210", "Programación II", "Juan Pérez", LibroEstado.DISPONIBLE);
        libroRepositorio.save(libro);

        Optional<Libro> encontrado = libroRepositorio.findByIsbn("978-9876543210");

        assertTrue(encontrado.isPresent());
        assertEquals("Programación II", encontrado.get().getTitulo());
    }

    @Test
    void findAll_ShouldReturnAllBook() {
        libroRepositorio.save(new Libro(null, "978-9876543210", "Programación II", "Juan", LibroEstado.DISPONIBLE));
        libroRepositorio.save(new Libro(null, "978-1111111111", "Programación I", "Pérez", LibroEstado.DISPONIBLE));

        assertEquals(2, libroRepositorio.findAll().size());
    }

    @Test
    void deleteById_ShouldRemoveBook() {
        Libro libro = new Libro(null, "978-9876543210", "Programación II", "Juan Pérez", LibroEstado.DISPONIBLE);
        libroRepositorio.save(libro);
        libroRepositorio.deleteById(libro.getId());

        assertFalse(libroRepositorio.existsById(libro.getId()));
    }
}
