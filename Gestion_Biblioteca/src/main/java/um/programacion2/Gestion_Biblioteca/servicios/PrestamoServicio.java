package um.programacion2.Gestion_Biblioteca.servicios;

import um.programacion2.Gestion_Biblioteca.modelos.Prestamo;

import java.util.List;

public interface PrestamoServicio {
    Prestamo save(Prestamo prestamo);
    Prestamo update(Long id, Prestamo prestamo);
    List<Prestamo> findAll();
    void delete(Long id);
}
