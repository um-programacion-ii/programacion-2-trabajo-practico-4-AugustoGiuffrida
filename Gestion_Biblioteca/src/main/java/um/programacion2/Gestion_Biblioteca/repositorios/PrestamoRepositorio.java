package um.programacion2.Gestion_Biblioteca.repositorios;

import um.programacion2.Gestion_Biblioteca.modelos.Prestamo;

import java.util.List;
import java.util.Optional;

public interface PrestamoRepositorio {
    Prestamo save(Prestamo prestamo);
    Optional<Prestamo> findById(Long id);
    List<Prestamo> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
