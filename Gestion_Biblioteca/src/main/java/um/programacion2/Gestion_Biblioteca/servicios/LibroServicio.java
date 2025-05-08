package um.programacion2.Gestion_Biblioteca.servicios;

import um.programacion2.Gestion_Biblioteca.modelos.Libro;

import java.util.List;

public interface LibroServicio {
    Libro save(Libro libro);
    Libro findByIsbn(String isbn);
    List<Libro> findAll();
    Libro update(Long id,Libro libro);
    void delete(Long id);
}
