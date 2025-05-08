package um.programacion2.Gestion_Biblioteca.servicios;

import org.springframework.stereotype.Service;
import um.programacion2.Gestion_Biblioteca.excepciones.LibroNoEncontradoException;
import um.programacion2.Gestion_Biblioteca.modelos.Libro;
import um.programacion2.Gestion_Biblioteca.repositorios.LibroRepositorio;

import java.util.List;


@Service
public class LibroServicioImpl implements LibroServicio{
    public LibroRepositorio libroRepositorio;

    public LibroServicioImpl(LibroRepositorio libroRepositorio){
        this.libroRepositorio = libroRepositorio;
    }

    @Override
    public Libro save(Libro libro){
        return libroRepositorio.save(libro);
    }

    @Override
    public Libro findByIsbn(String isbn){
        return libroRepositorio.findByIsbn(isbn).orElseThrow(()->new LibroNoEncontradoException(isbn));
    }

    @Override
    public List<Libro> findAll(){
        return libroRepositorio.findAll();
    }

    @Override
    public Libro update(Long id, Libro libro){
        if(!libroRepositorio.existsById(id)){
            throw new LibroNoEncontradoException(id);
        }
        libro.setId(id);
        return libroRepositorio.save(libro);
    }

    @Override
    public void delete(Long id){
        if(!libroRepositorio.existsById(id)){
            throw new LibroNoEncontradoException(id);
        }

         libroRepositorio.deleteById(id);
    }

}
