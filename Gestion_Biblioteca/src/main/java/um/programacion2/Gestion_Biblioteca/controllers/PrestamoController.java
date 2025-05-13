package um.programacion2.Gestion_Biblioteca.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.programacion2.Gestion_Biblioteca.exceptions.PrestamoNoEncontradoExcepcion;
import um.programacion2.Gestion_Biblioteca.models.Prestamo;
import um.programacion2.Gestion_Biblioteca.services.PrestamoServicio;

import java.util.List;

/**
 * Controlador REST para la gestión de préstamos de libros.
 * Provee endpoints para operaciones CRUD sobre la entidad Prestamo.
 */
@RestController
@RequestMapping("api/prestamos")
public class PrestamoController {

    private final PrestamoServicio prestamoServicio;

    public PrestamoController(PrestamoServicio prestamoServicio){
        this.prestamoServicio = prestamoServicio;
    }

    /**
     * Obtiene la lista de todos los préstamos registrados.
     *
     * @return Lista de préstamos con estado 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Prestamo>> findAll(){
        return ResponseEntity.ok(prestamoServicio.findAll());
    }

    /**
     * Obtiene un préstamo específico por su ID.
     *
     * @param id Identificador único del préstamo.
     * @return Préstamo encontrado o 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> findById(@PathVariable Long id){
        try {
            Prestamo prestamo = prestamoServicio.findById(id);
            return ResponseEntity.ok(prestamo);
        } catch (PrestamoNoEncontradoExcepcion e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Registra un nuevo préstamo.
     *
     * @param prestamo Objeto préstamo a crear.
     * @return Préstamo creado con estado 201 CREATED.
     */
    @PostMapping
    public ResponseEntity<Prestamo> create(@RequestBody Prestamo prestamo){
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoServicio.save(prestamo));
    }

    /**
     * Actualiza un préstamo existente.
     *
     * @param id ID del préstamo a actualizar.
     * @param prestamo Objeto préstamo con datos actualizados.
     * @return Préstamo actualizado o 404 si no se encuentra.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> update(@PathVariable Long id, @RequestBody Prestamo prestamo){
        try {
            return ResponseEntity.ok(prestamoServicio.update(id, prestamo));
        } catch (PrestamoNoEncontradoExcepcion e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Elimina un préstamo por su ID.
     *
     * @param id ID del préstamo a eliminar.
     * @return 204 No Content si fue eliminado, o 404 si no se encuentra.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            prestamoServicio.delete(id);
            return ResponseEntity.noContent().build();
        } catch (PrestamoNoEncontradoExcepcion e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
