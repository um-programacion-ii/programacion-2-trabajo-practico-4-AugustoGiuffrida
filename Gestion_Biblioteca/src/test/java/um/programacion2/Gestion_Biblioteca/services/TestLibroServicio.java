package um.programacion2.Gestion_Biblioteca.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import um.programacion2.Gestion_Biblioteca.enums.LibroEstado;
import um.programacion2.Gestion_Biblioteca.exceptions.LibroNoEncontradoException;
import um.programacion2.Gestion_Biblioteca.models.Libro;
import um.programacion2.Gestion_Biblioteca.repositories.LibroRepositorio;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestLibroServicio {

    @Mock
    LibroRepositorio libroRepositorio;

    @InjectMocks
    LibroServicioImpl libroServicio;
    Libro libro;

    @BeforeEach
    void setUp(){
         libro = new Libro(1L, "978-9876543210", "Programación II", "Juan Pérez", LibroEstado.DISPONIBLE);
    }

    @Test
    void saveBook(){
        when(libroRepositorio.save(libro)).thenReturn(libro);

        Libro resultado = libroServicio.save(libro);

        assertEquals(libro,resultado);
        verify(libroRepositorio, times(1)).save(libro);
    }

    @Test
    void findBookById(){
        when(libroRepositorio.findById(1L)).thenReturn(Optional.of(libro));

        Libro resultado = libroServicio.findById(1L);

        assertEquals(libro, resultado);
        verify(libroRepositorio, times(1)).findById(1L);
    }

    @Test
    void findNonExistentBookById(){
        when(libroRepositorio.findById(11L)).thenReturn(Optional.empty());

        assertThrows(LibroNoEncontradoException.class,()->libroServicio.findById(11L));
        verify(libroRepositorio, times(1)).findById(11L);
    }

    @Test
    void findBookByIsbn(){
        when(libroRepositorio.findByIsbn("978-9876543210")).thenReturn(Optional.of(libro));

        Libro resultado = libroServicio.findByIsbn("978-9876543210");

        assertEquals(libro, resultado);
        verify(libroRepositorio, times(1)).findByIsbn("978-9876543210");
    }

    @Test
    void findNonExistentBookByIsbn(){
        when(libroRepositorio.findByIsbn("978-9876543210")).thenReturn(Optional.empty());

        assertThrows(LibroNoEncontradoException.class, ()-> libroServicio.findByIsbn("978-9876543210"));
        verify(libroRepositorio, times(1)).findByIsbn("978-9876543210");
    }

    @Test
    void findAllBooks(){
        when(libroRepositorio.findAll()).thenReturn(List.of(libro));

        List<Libro> resultado = libroServicio.findAll();

        assertEquals(1, resultado.size());
        verify(libroRepositorio,times(1)).findAll();
    }

    @Test
    void updateExistentBook(){
        when(libroRepositorio.existsById(1L)).thenReturn(true);
        when(libroRepositorio.save(libro)).thenReturn(libro);

        Libro resultado = libroServicio.update(1L, libro);

        assertEquals(libro.getId(), resultado.getId());
        verify(libroRepositorio, times(1)).save(libro);
    }

    @Test
    void updateNonExistentBook(){
        when(libroRepositorio.existsById(11L)).thenReturn(false);

        assertThrows(LibroNoEncontradoException.class, ()->libroServicio.update(11L,libro));
        verify(libroRepositorio, never()).save(any());
    }

    @Test
    void deleteExistentBook(){
        when(libroRepositorio.existsById(1L)).thenReturn(true);

        libroServicio.delete(1L);
        verify(libroRepositorio).deleteById(1L);
    }

    @Test
    void deleteNonExistentBook(){
        when(libroRepositorio.existsById(11L)).thenReturn(false);

        assertThrows(LibroNoEncontradoException.class, ()->libroServicio.delete(11L));
        verify(libroRepositorio, never()).deleteById(any());
    }

}
