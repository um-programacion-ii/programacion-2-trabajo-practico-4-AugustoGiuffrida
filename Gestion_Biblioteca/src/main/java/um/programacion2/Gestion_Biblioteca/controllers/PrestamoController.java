package um.programacion2.Gestion_Biblioteca.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.programacion2.Gestion_Biblioteca.exceptions.PrestamoNoEncontradoExcepcion;
import um.programacion2.Gestion_Biblioteca.models.Prestamo;
import um.programacion2.Gestion_Biblioteca.services.PrestamoServicio;

import java.util.List;

@RestController
@RequestMapping("api/prestamos")
public class PrestamoController {

    private final PrestamoServicio prestamoServicio;

    public PrestamoController(PrestamoServicio prestamoServicio){
        this.prestamoServicio = prestamoServicio;
    }

    @GetMapping
    public ResponseEntity<List<Prestamo>> findAll(){
        return ResponseEntity.ok(prestamoServicio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> findById(@PathVariable Long id){
        try {
            Prestamo prestamo = prestamoServicio.findById(id);
            return ResponseEntity.ok(prestamo);
        } catch (PrestamoNoEncontradoExcepcion e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Prestamo> create(@RequestBody Prestamo prestamo){
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoServicio.save(prestamo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> update(@PathVariable Long id, @RequestBody Prestamo prestamo){
        try {
            return ResponseEntity.ok(prestamoServicio.update(id, prestamo));
        } catch (PrestamoNoEncontradoExcepcion e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            prestamoServicio.delete(id);
            return ResponseEntity.ok("Prestamo con ID " + id + " eliminado correctamente.");
        } catch (PrestamoNoEncontradoExcepcion e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
