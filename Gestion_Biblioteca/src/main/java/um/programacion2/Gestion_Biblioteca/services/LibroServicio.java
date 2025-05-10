package um.programacion2.Gestion_Biblioteca.services;

import um.programacion2.Gestion_Biblioteca.models.Libro;

import java.util.List;

public interface LibroServicio {
    Libro save(Libro libro);
    Libro findByIsbn(String isbn);
    Libro findById(Long id);
    List<Libro> findAll();
    Libro update(Long id,Libro libro);
    void delete(Long id);
}
