package um.programacion2.Gestion_Biblioteca.repositorios;

import org.springframework.stereotype.Repository;
import um.programacion2.Gestion_Biblioteca.modelos.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositorioImpl implements UsuarioRepositorio{
    public HashMap<Long, Usuario> usuarios = new HashMap<>();
    public Long nextId = 1L;

    @Override
    public Usuario save(Usuario usuario){
        if(usuario.getId() == null){
            usuario.setId(nextId++);
        }
        usuarios.put(usuario.getId(),usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> findById(Long id){
        return usuarios.values().stream().filter(usuario -> usuario.getId().equals(id)).findFirst();
    }

    @Override
    public List<Usuario> findAll(){
        return new ArrayList<>(usuarios.values());
    }

    @Override
    public void deleteById(Long id){
        usuarios.remove(id);
    }

    @Override
    public boolean existsById(Long id){
        return usuarios.containsKey(id);
    }
}
