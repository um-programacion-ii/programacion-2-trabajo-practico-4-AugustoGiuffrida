package um.programacion2.Gestion_Biblioteca.repositorios;

import um.programacion2.Gestion_Biblioteca.modelos.Libro;

import java.util.List;
import java.util.Optional;

public interface LibroRepositorio {
    Libro save(Libro libro);
    Optional <Libro> findById(Long id);
    Optional <Libro> findByIsbn(String isbn);
    List<Libro> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
