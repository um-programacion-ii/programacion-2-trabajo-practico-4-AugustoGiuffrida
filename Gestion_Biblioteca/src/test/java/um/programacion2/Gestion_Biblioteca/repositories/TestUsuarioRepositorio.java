package um.programacion2.Gestion_Biblioteca.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import um.programacion2.Gestion_Biblioteca.enums.UsuarioEstado;
import um.programacion2.Gestion_Biblioteca.models.Usuario;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestUsuarioRepositorio {
    private UsuarioRepositorioImpl usuarioRepositorio;

    @BeforeEach void setUp(){
        usuarioRepositorio = new UsuarioRepositorioImpl();
    }

    @Test
    void save_ShouldAssignIdAndStoreUser() {
        Usuario usuario = new Usuario();
        Usuario guardado = usuarioRepositorio.save(usuario);

        assertNotNull(guardado.getId());
        assertTrue(usuarioRepositorio.existsById(guardado.getId()));
    }

    @Test
    void save_ShouldReturnUserWithExpectedData(){
       Usuario usuario = new Usuario(null, "Ana Martínez", "ana.martinez@example.com", UsuarioEstado.ACTIVO);
       Usuario guardado = usuarioRepositorio.save(usuario);

        assertNotNull(guardado.getId());
        assertEquals("Ana Martínez", guardado.getNombre());
        assertEquals("ana.martinez@example.com", guardado.getEmail());
        assertEquals(UsuarioEstado.ACTIVO, guardado.getEstado());
    }

    @Test
    void save_ShouldHandleUserWithNullFields() {
        Usuario usuario = new Usuario(null, null, null, null);
        Usuario guardado = usuarioRepositorio.save(usuario);

        assertNotNull(guardado.getId());
        assertNull(guardado.getNombre());
        assertNull(guardado.getEmail());
        assertNull(guardado.getEstado());
    }

    @Test
    void findById_ShouldReturnUserIfExists(){
        Usuario usuario = new Usuario();
        Usuario guardado = usuarioRepositorio.save(usuario);

        Optional<Usuario> encontrado = usuarioRepositorio.findById(guardado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(guardado.getId(), encontrado.get().getId());
    }

    @Test
    void findById_ShouldReturnEmptyIfNotExists() {
        assertTrue(usuarioRepositorio.findById(999L).isEmpty());
    }

    @Test
    void findAll_ShouldReturnAllUser() {
        usuarioRepositorio.save(new Usuario());
        usuarioRepositorio.save(new Usuario());

        assertEquals(2, usuarioRepositorio.findAll().size());
    }

    @Test
    void deleteById_ShouldRemoveUser() {
        Usuario usuario = new Usuario(1L, "Ana Martínez", "ana.martinez@example.com", UsuarioEstado.ACTIVO);
        Usuario guardado = usuarioRepositorio.save(usuario);
        usuarioRepositorio.deleteById(guardado.getId());

        assertFalse(usuarioRepositorio.existsById(guardado.getId()));
    }
}
