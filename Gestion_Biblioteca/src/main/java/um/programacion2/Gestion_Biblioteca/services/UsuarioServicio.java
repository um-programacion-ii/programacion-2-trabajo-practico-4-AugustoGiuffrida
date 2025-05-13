package um.programacion2.Gestion_Biblioteca.services;

import um.programacion2.Gestion_Biblioteca.models.Usuario;

import java.util.List;

public interface UsuarioServicio {
    Usuario save(Usuario usuario);
    Usuario update(Long id, Usuario usuario);
    List<Usuario> findAll();
    Usuario findById(Long id);
    void delete(Long id);
}
