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
        Libro libro = new Libro();
        Libro guardado = libroRepositorio.save(libro);

        assertNotNull(guardado.getId());
        assertTrue(libroRepositorio.existsById(guardado.getId()));
    }

    @Test
    void save_ShouldReturnBookWithExpectedData(){
        Libro libro = new Libro(null, "978-9876543210", "Programación II", "Juan", LibroEstado.DISPONIBLE);
        Libro guardado = libroRepositorio.save(libro);

        assertEquals("978-9876543210", guardado.getIsbn());
        assertEquals("Programación II", guardado.getTitulo());
        assertEquals("Juan", guardado.getAutor());
        assertEquals(LibroEstado.DISPONIBLE,guardado.getEstado());
        assertNotNull(guardado.getId());
    }

    @Test
    void save_ShouldHandleUserWithNullFields() {
        Libro libro = new Libro(null, null, null, null, null);
        Libro guardado = libroRepositorio.save(libro);

        assertNotNull(guardado.getId());
        assertNull(guardado.getIsbn());
        assertNull(guardado.getTitulo());
        assertNull(guardado.getAutor());
        assertNull(guardado.getEstado());
    }

    @Test
    void findById_ShouldReturnBookIfExists() {
        Libro libro = new Libro();
        Libro guardado = libroRepositorio.save(libro);

        Optional<Libro> encontrado = libroRepositorio.findById(guardado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(guardado.getId(), encontrado.get().getId());
    }

    @Test
    void findByIsbn_ShouldReturnBookIfExists() {
        Libro libro = new Libro();
        libro.setIsbn("978-9876543210");
        libroRepositorio.save(libro);

        Optional<Libro> encontrado = libroRepositorio.findByIsbn("978-9876543210");

        assertTrue(encontrado.isPresent());
    }

    @Test
    void findById_ShouldReturnEmptyIfNotExists() {
        assertTrue(libroRepositorio.findById(999L).isEmpty());
    }

    @Test
    void findByIsbn_ShouldReturnEmptyIfNotExists() {
        assertTrue(libroRepositorio.findByIsbn("no-existe").isEmpty());
    }


    @Test
    void findAll_ShouldReturnAllBook() {
        libroRepositorio.save(new Libro());
        libroRepositorio.save(new Libro());

        assertEquals(2, libroRepositorio.findAll().size());
    }

    @Test
    void deleteById_ShouldRemoveBook() {
        Libro libro = new Libro(null, "978-9876543210", "Programación II", "Juan Pérez", LibroEstado.DISPONIBLE);
        Libro guardado = libroRepositorio.save(libro);
        libroRepositorio.deleteById(guardado.getId());

        assertFalse(libroRepositorio.existsById(guardado.getId()));
    }
}
