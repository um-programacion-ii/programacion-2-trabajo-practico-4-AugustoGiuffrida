package um.programacion2.Gestion_Biblioteca.services;

import org.springframework.stereotype.Service;
import um.programacion2.Gestion_Biblioteca.exceptions.PrestamoNoEncontradoExcepcion;
import um.programacion2.Gestion_Biblioteca.models.Prestamo;
import um.programacion2.Gestion_Biblioteca.repositories.PrestamoRepositorio;

import java.util.List;

@Service
public class PrestamoServicioImpl implements PrestamoServicio{
    public PrestamoRepositorio prestamoRepositorio;

    public PrestamoServicioImpl(PrestamoRepositorio prestamoRepositorio){
        this.prestamoRepositorio = prestamoRepositorio;
    }

    @Override
    public Prestamo save(Prestamo prestamo){
        return prestamoRepositorio.save(prestamo);
    }
    @Override
    public Prestamo update(Long id, Prestamo prestamo){
        if(!prestamoRepositorio.existsById(id)){
            throw new PrestamoNoEncontradoExcepcion(id);
        }
        prestamo.setId(id);
        return prestamoRepositorio.save(prestamo);
    }

    @Override
    public List<Prestamo> findAll(){
        return prestamoRepositorio.findAll();
    }

    @Override
    public void delete(Long id) {
        if (!prestamoRepositorio.existsById(id)) {
            throw new PrestamoNoEncontradoExcepcion(id);
        }
        prestamoRepositorio.deleteById(id);
    }
}
