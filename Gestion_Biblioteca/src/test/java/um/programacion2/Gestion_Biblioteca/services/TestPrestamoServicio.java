package um.programacion2.Gestion_Biblioteca.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import um.programacion2.Gestion_Biblioteca.enums.LibroEstado;
import um.programacion2.Gestion_Biblioteca.enums.UsuarioEstado;
import um.programacion2.Gestion_Biblioteca.exceptions.PrestamoNoEncontradoExcepcion;
import um.programacion2.Gestion_Biblioteca.models.Libro;
import um.programacion2.Gestion_Biblioteca.models.Prestamo;
import um.programacion2.Gestion_Biblioteca.models.Usuario;
import um.programacion2.Gestion_Biblioteca.repositories.PrestamoRepositorio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestPrestamoServicio {

    @Mock
    PrestamoRepositorio prestamoRepositorio;

    @InjectMocks
    PrestamoServicioImpl prestamoServicio;
    Prestamo prestamo;

    @BeforeEach
    void setUp(){
        Libro libro = new Libro(1L, "978-9876543210", "Programación II", "Juan Pérez", LibroEstado.DISPONIBLE);
        Usuario usuario = new Usuario(1L, "Ana Martínez", "ana.martinez@example.com", UsuarioEstado.ACTIVO);
        prestamo = new Prestamo(1L, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(7));
    }

    @Test
    void saveLoan(){
        when(prestamoRepositorio.save(prestamo)).thenReturn(prestamo);

        Prestamo resultado = prestamoRepositorio.save(prestamo);

        assertEquals(prestamo, resultado);
        verify(prestamoRepositorio, times(1)).save(prestamo);
    }

    @Test
    void updateExistentLoan(){
        when(prestamoRepositorio.existsById(1L)).thenReturn(true);
        when(prestamoRepositorio.save(prestamo)).thenReturn(prestamo);

        Prestamo resultado = prestamoServicio.update(1L, prestamo);

        assertEquals(prestamo.getId(), resultado.getId());
        verify(prestamoRepositorio).save(prestamo);
    }

    @Test
    void updateNonExistentLoan(){
        when(prestamoRepositorio.existsById(11L)).thenReturn(false);

        assertThrows(PrestamoNoEncontradoExcepcion.class, ()->prestamoServicio.update(11L,prestamo));
        verify(prestamoRepositorio, never()).save(any());
    }

    @Test
    void findAllLoans(){
        when(prestamoRepositorio.findAll()).thenReturn(List.of(prestamo));

        List<Prestamo> resultado = prestamoServicio.findAll();

        assertEquals(1,resultado.size());
        verify(prestamoRepositorio).findAll();
    }

    @Test
    void findAllLoansNonExistentLoans(){
        when(prestamoRepositorio.findAll()).thenReturn(List.of());

        List<Prestamo> resultado = prestamoServicio.findAll();

        assertEquals(0,resultado.size());
        verify(prestamoRepositorio).findAll();
    }

    @Test
    void deleteExistentLoan(){
        when(prestamoRepositorio.existsById(1L)).thenReturn(true);

        prestamoServicio.delete(1L);

        verify(prestamoRepositorio).deleteById(1L);
    }

    @Test
    void deleteNonExistentLoan(){
        when(prestamoRepositorio.existsById(11L)).thenReturn(false);

        assertThrows(PrestamoNoEncontradoExcepcion.class, ()->prestamoServicio.delete(11L));
        verify(prestamoRepositorio, never()).deleteById(11L);
    }

    @Test
    void findLoanById(){
        when(prestamoRepositorio.findById(1L)).thenReturn(Optional.ofNullable(prestamo));

        Prestamo resultado = prestamoServicio.findById(1L);

        assertEquals(prestamo, resultado);
        verify(prestamoRepositorio, times(1)).findById(1L);
    }

    @Test
    void findNonExistentLoanById(){
        when(prestamoRepositorio.findById(11L)).thenReturn(Optional.empty());

        assertThrows(PrestamoNoEncontradoExcepcion.class, ()->prestamoServicio.findById(11L));
        verify(prestamoRepositorio, times(1)).findById(11L);
    }


}
