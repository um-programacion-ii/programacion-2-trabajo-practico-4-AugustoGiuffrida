package um.programacion2.Gestion_Biblioteca.servicios;

import um.programacion2.Gestion_Biblioteca.modelos.Usuario;

import java.util.List;

public interface UsuarioServicio {
    Usuario save(Usuario usuario);
    Usuario update(Long id, Usuario usuario);
    List<Usuario> findAll();
    void delete(Long id);
}
