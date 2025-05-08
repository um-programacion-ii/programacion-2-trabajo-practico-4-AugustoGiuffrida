package um.programacion2.Gestion_Biblioteca.servicios;

import org.springframework.stereotype.Service;
import um.programacion2.Gestion_Biblioteca.excepciones.PrestamoNoEncontradoExcepcion;
import um.programacion2.Gestion_Biblioteca.modelos.Prestamo;
import um.programacion2.Gestion_Biblioteca.repositorios.PrestamoRepositorio;

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
