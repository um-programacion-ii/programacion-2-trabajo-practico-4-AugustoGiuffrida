package um.programacion2.Gestion_Biblioteca.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.programacion2.Gestion_Biblioteca.exceptions.LibroNoEncontradoException;
import um.programacion2.Gestion_Biblioteca.models.Libro;
import um.programacion2.Gestion_Biblioteca.services.LibroServicio;

import java.util.List;

@RestController
@RequestMapping("api/libros")
public class LibroController {
    public LibroServicio libroServicio;

    public LibroController(LibroServicio libroServicio){
        this.libroServicio = libroServicio;
    }

    @GetMapping
    public ResponseEntity<List<Libro>> getAll(){
        return ResponseEntity.ok(libroServicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> getById(@PathVariable Long id){
        try {
            Libro libro = libroServicio.findById(id);
            return ResponseEntity.ok(libro);
        }catch (LibroNoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Libro> getByIsbn(@PathVariable String isbn){
        try {
            Libro libro = libroServicio.findByIsbn(isbn);
            return ResponseEntity.ok(libro);
        }catch (LibroNoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Libro> create(@RequestBody Libro libro){
        return ResponseEntity.status(HttpStatus.CREATED).body(libroServicio.save(libro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> update(@PathVariable Long id, @RequestBody Libro libro){
        try {
            return ResponseEntity.ok(libroServicio.update(id,libro));
        } catch (LibroNoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            libroServicio.delete(id);
            return ResponseEntity.noContent().build(); // 204
        } catch (LibroNoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
