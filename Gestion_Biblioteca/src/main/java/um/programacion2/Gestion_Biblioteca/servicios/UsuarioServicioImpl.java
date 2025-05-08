package um.programacion2.Gestion_Biblioteca.servicios;

import org.springframework.stereotype.Service;
import um.programacion2.Gestion_Biblioteca.excepciones.UsuarioNoEncontradoExcepcion;
import um.programacion2.Gestion_Biblioteca.modelos.Usuario;
import um.programacion2.Gestion_Biblioteca.repositorios.UsuarioRepositorio;

import java.util.List;

@Service
public class UsuarioServicioImpl implements  UsuarioServicio{
    public UsuarioRepositorio usuarioRepositorio;

    public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio){
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Usuario save(Usuario usuario){
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public Usuario update(Long id, Usuario usuario){
        if(!usuarioRepositorio.existsById(id)){
            throw  new UsuarioNoEncontradoExcepcion(id);
        }
        usuario.setId(id);
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public List<Usuario> findAll(){
        return usuarioRepositorio.findAll();
    }

    @Override
    public void delete(Long id){
        if(!usuarioRepositorio.existsById(id)){
            throw  new UsuarioNoEncontradoExcepcion(id);
        }
        usuarioRepositorio.deleteById(id);
    }
}
