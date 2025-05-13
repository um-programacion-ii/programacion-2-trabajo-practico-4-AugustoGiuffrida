package um.programacion2.Gestion_Biblioteca.repositories;

import um.programacion2.Gestion_Biblioteca.models.Libro;

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
