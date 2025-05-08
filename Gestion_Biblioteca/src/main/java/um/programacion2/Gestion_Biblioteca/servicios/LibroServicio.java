package um.programacion2.Gestion_Biblioteca.servicios;

import um.programacion2.Gestion_Biblioteca.modelos.Libro;

import java.util.List;

public interface LibroServicio {
    Libro guardar(Libro libro);
    Libro buscarPorIsbn(String isbn);
    List<Libro> obtenerTodos();
    Libro actualizar(Long id,Libro libro);
    void eliminar(Long id);
}
