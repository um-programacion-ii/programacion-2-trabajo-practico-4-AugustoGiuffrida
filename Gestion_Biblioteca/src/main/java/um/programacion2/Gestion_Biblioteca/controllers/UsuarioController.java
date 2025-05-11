package um.programacion2.Gestion_Biblioteca.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.programacion2.Gestion_Biblioteca.exceptions.UsuarioNoEncontradoExcepcion;
import um.programacion2.Gestion_Biblioteca.models.Usuario;
import um.programacion2.Gestion_Biblioteca.repositories.UsuarioRepositorio;
import um.programacion2.Gestion_Biblioteca.services.UsuarioServicio;

import java.util.List;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {
    private final UsuarioServicio usuarioServicio;

    public UsuarioController(UsuarioServicio usuarioServicio){
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll(){
        return ResponseEntity.ok(usuarioServicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id){
        try {
            Usuario usuario = usuarioServicio.findById(id);
            return ResponseEntity.ok(usuario);
        } catch (UsuarioNoEncontradoExcepcion e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioServicio.save(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario){
        try {
            return ResponseEntity.ok(usuarioServicio.update(id,usuario));
        } catch (UsuarioNoEncontradoExcepcion e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            usuarioServicio.delete(id);
            return ResponseEntity.noContent().build();
        } catch (UsuarioNoEncontradoExcepcion e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
