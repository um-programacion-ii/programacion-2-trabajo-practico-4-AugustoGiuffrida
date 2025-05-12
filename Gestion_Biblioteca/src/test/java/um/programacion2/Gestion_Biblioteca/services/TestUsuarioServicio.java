package um.programacion2.Gestion_Biblioteca.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import um.programacion2.Gestion_Biblioteca.enums.UsuarioEstado;
import um.programacion2.Gestion_Biblioteca.exceptions.PrestamoNoEncontradoExcepcion;
import um.programacion2.Gestion_Biblioteca.exceptions.UsuarioNoEncontradoExcepcion;
import um.programacion2.Gestion_Biblioteca.models.Usuario;
import um.programacion2.Gestion_Biblioteca.repositories.UsuarioRepositorio;

import java.awt.*;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TestUsuarioServicio {

    @Mock
    UsuarioRepositorio usuarioRepositorio;

    @InjectMocks
    UsuarioServicioImpl usuarioServicio;
    Usuario usuario;

    @BeforeEach
    void setUp(){
         usuario = new Usuario(1L, "Ana Martínez", "ana.martinez@example.com", UsuarioEstado.ACTIVO);
    }

    @Test
    void saveUser(){
        when(usuarioRepositorio.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioServicio.save(usuario);

        assertEquals(usuario,resultado);
        verify(usuarioRepositorio, times(1)).save(usuario);
    }

    @Test
    void updateExistentUser(){
        when(usuarioRepositorio.existsById(1L)).thenReturn(true);
        when(usuarioRepositorio.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioServicio.update(1L,usuario);

        assertEquals(usuario.getId(), resultado.getId());
        verify(usuarioRepositorio, times(1)).save(usuario);
    }

    @Test
    void updateNonExistentUser(){
        when(usuarioRepositorio.existsById(1L)).thenReturn(false);

        assertThrows(UsuarioNoEncontradoExcepcion.class,()->usuarioServicio.update(1L,usuario));
        verify(usuarioRepositorio, never()).save(any());
    }

    @Test
    void findAllUsers(){
        when(usuarioRepositorio.findAll()).thenReturn(List.of(usuario));

        List<Usuario> resultado = usuarioServicio.findAll();

        assertEquals(1, resultado.size());
        verify(usuarioRepositorio, times(1)).findAll();
    }

    @Test
    void deleteExistentUser(){
        when(usuarioRepositorio.existsById(1L)).thenReturn(true);

        usuarioServicio.delete(1L);

        verify(usuarioRepositorio).deleteById(1L);
    }

    @Test
    void deleteNonExistentUser(){
        when(usuarioRepositorio.existsById(11L)).thenReturn(false);

        assertThrows(UsuarioNoEncontradoExcepcion.class, ()->usuarioServicio.delete(11L));
        verify(usuarioRepositorio,never()).deleteById(11L);
    }

    @Test
    void findUserById(){
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioServicio.findById(1L);

        assertEquals(usuario, resultado);
        verify(usuarioRepositorio, times(1)).findById(1L);
    }

    @Test
    void findNonExistentUserByID(){
        when(usuarioRepositorio.findById(11L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoExcepcion.class, ()->usuarioServicio.findById(11L));
        verify(usuarioRepositorio, times(1)).findById(11L);
    }


}
