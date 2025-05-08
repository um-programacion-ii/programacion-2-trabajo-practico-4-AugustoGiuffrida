package um.programacion2.Gestion_Biblioteca.servicios;

import um.programacion2.Gestion_Biblioteca.modelos.Prestamo;

import java.util.List;

public interface PrestamoServicio {
    Prestamo guardar(Prestamo prestamo);
    Prestamo actualizar(Long id, Prestamo prestamo);
    List<Prestamo> obtenerTodos();
    void eliminar(Long id);
}
