package um.programacion2.Gestion_Biblioteca.services;

import um.programacion2.Gestion_Biblioteca.models.Libro;
import um.programacion2.Gestion_Biblioteca.models.Prestamo;

import java.util.List;

public interface PrestamoServicio {
    Prestamo save(Prestamo prestamo);
    Prestamo findById(Long id);
    Prestamo update(Long id, Prestamo prestamo);
    List<Prestamo> findAll();
    void delete(Long id);
}
