package um.programacion2.Gestion_Biblioteca.repositorios;

import um.programacion2.Gestion_Biblioteca.modelos.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositorio {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(Long id);
    List<Usuario> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
