package um.programacion2.Gestion_Biblioteca.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.programacion2.Gestion_Biblioteca.exceptions.LibroNoEncontradoException;
import um.programacion2.Gestion_Biblioteca.models.Libro;
import um.programacion2.Gestion_Biblioteca.services.LibroServicio;

import java.util.List;


/**
 * Controlador REST para la gestión de libros en la biblioteca.
 * Provee endpoints para operaciones CRUD sobre la entidad Libro.
 */
@RestController
@RequestMapping("api/libros")
public class LibroController {
    public LibroServicio libroServicio;

    public LibroController(LibroServicio libroServicio){
        this.libroServicio = libroServicio;
    }

    /**
     * Obtiene la lista de todos los libros disponibles.
     *
     * @return Lista de libros con estado 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Libro>> getAll(){
        return ResponseEntity.ok(libroServicio.findAll());
    }

    /**
     * Obtiene un libro específico por su ID.
     *
     * @param id Identificador único del libro.
     * @return Libro encontrado o 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getById(@PathVariable Long id){
        try {
            Libro libro = libroServicio.findById(id);
            return ResponseEntity.ok(libro);
        }catch (LibroNoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    /**
     * Obtiene un libro específico por su código ISBN.
     *
     * @param isbn Código ISBN del libro.
     * @return Libro encontrado o 404 si no existe.
     */
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Libro> getByIsbn(@PathVariable String isbn){
        try {
            Libro libro = libroServicio.findByIsbn(isbn);
            return ResponseEntity.ok(libro);
        }catch (LibroNoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Crea un nuevo libro.
     *
     * @param libro Objeto libro a crear.
     * @return Libro creado con estado 201 CREATED.
     */
    @PostMapping
    public ResponseEntity<Libro> create(@RequestBody Libro libro){
        return ResponseEntity.status(HttpStatus.CREATED).body(libroServicio.save(libro));
    }

    /**
     * Actualiza un libro existente.
     *
     * @param id ID del libro a actualizar.
     * @param libro Objeto libro con datos actualizados.
     * @return Libro actualizado o 404 si no se encuentra.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Libro> update(@PathVariable Long id, @RequestBody Libro libro){
        try {
            return ResponseEntity.ok(libroServicio.update(id,libro));
        } catch (LibroNoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Elimina un libro por su ID.
     *
     * @param id ID del libro a eliminar.
     * @return 204 No Content si fue eliminado, o 404 si no se encuentra.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            libroServicio.delete(id);
            return ResponseEntity.noContent().build();
        } catch (LibroNoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
