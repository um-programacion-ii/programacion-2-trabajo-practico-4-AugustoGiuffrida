package um.programacion2.Gestion_Biblioteca.repositories;

import org.springframework.stereotype.Repository;
import um.programacion2.Gestion_Biblioteca.models.Prestamo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class PrestamoRepositorioImpl implements PrestamoRepositorio{
    private final HashMap<Long, Prestamo> prestamos = new HashMap<>();
    private Long nextId = 1L;

    @Override
    public Prestamo save(Prestamo prestamo){
        if(prestamo.getId() == null){
            prestamo.setId(nextId++);
        }
        prestamos.put(prestamo.getId(), prestamo);
        return prestamo;
    }

    @Override
    public Optional<Prestamo> findById(Long id){
        return prestamos.values().stream().filter(prestamo -> prestamo.getId().equals(id)).findFirst();
    }

    @Override
    public List<Prestamo> findAll(){
        return new ArrayList<>(prestamos.values());
    }

    @Override
    public void deleteById(Long id){
        prestamos.remove(id);
    }

    @Override
    public boolean existsById(Long id){
        return prestamos.containsKey(id);
    }
}
