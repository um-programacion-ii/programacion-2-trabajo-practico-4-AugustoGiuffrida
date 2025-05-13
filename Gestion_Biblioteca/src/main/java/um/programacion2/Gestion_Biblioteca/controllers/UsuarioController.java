package um.programacion2.Gestion_Biblioteca.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.programacion2.Gestion_Biblioteca.exceptions.UsuarioNoEncontradoExcepcion;
import um.programacion2.Gestion_Biblioteca.models.Usuario;
import um.programacion2.Gestion_Biblioteca.repositories.UsuarioRepositorio;
import um.programacion2.Gestion_Biblioteca.services.UsuarioServicio;

import java.util.List;

/**
 * Controlador REST para la gestión de usuarios del sistema.
 * Provee endpoints CRUD para la entidad Usuario.
 */
@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {
    private final UsuarioServicio usuarioServicio;

    public UsuarioController(UsuarioServicio usuarioServicio){
        this.usuarioServicio = usuarioServicio;
    }

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return Lista de usuarios con estado 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> findAll(){
        return ResponseEntity.ok(usuarioServicio.findAll());
    }

    /**
     * Obtiene un usuario específico por su ID.
     *
     * @param id Identificador único del usuario.
     * @return Usuario encontrado o 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id){
        try {
            Usuario usuario = usuarioServicio.findById(id);
            return ResponseEntity.ok(usuario);
        } catch (UsuarioNoEncontradoExcepcion e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param usuario Objeto Usuario a crear.
     * @return Usuario creado con estado 201 CREATED.
     */
    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioServicio.save(usuario));
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param id ID del usuario a actualizar.
     * @param usuario Objeto con los nuevos datos del usuario.
     * @return Usuario actualizado o 404 si no se encuentra.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario){
        try {
            return ResponseEntity.ok(usuarioServicio.update(id,usuario));
        } catch (UsuarioNoEncontradoExcepcion e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Elimina un usuario del sistema.
     *
     * @param id ID del usuario a eliminar.
     * @return 204 No Content si fue eliminado, o 404 si no se encuentra.
     */
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
