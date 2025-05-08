package um.programacion2.Gestion_Biblioteca.servicios;

import um.programacion2.Gestion_Biblioteca.modelos.Usuario;

import java.util.List;

public interface UsuarioServicio {
    Usuario guardar(Usuario usuario);
    Usuario actualizar(Long id, Usuario usuario);
    List<Usuario> obtenerTodos();
    void eliminar(Long id);
}
