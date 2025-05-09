package um.programacion2.Gestion_Biblioteca.repositories;

import um.programacion2.Gestion_Biblioteca.models.Prestamo;

import java.util.List;
import java.util.Optional;

public interface PrestamoRepositorio {
    Prestamo save(Prestamo prestamo);
    Optional<Prestamo> findById(Long id);
    List<Prestamo> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
