package um.programacion2.Gestion_Biblioteca.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import um.programacion2.Gestion_Biblioteca.enums.LibroEstado;
import um.programacion2.Gestion_Biblioteca.enums.UsuarioEstado;
import um.programacion2.Gestion_Biblioteca.exceptions.PrestamoNoEncontradoExcepcion;
import um.programacion2.Gestion_Biblioteca.models.Libro;
import um.programacion2.Gestion_Biblioteca.models.Prestamo;
import um.programacion2.Gestion_Biblioteca.models.Usuario;
import um.programacion2.Gestion_Biblioteca.services.PrestamoServicio;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDate;
import java.util.List;


@WebMvcTest(PrestamoController.class)
public class TestPrestamoController {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PrestamoServicio prestamoServicio;

    Prestamo prestamo;

    @BeforeEach
    void setUp(){
        Libro libro = new Libro(1L, "978-9876543210", "Programación II", "Juan Pérez", LibroEstado.DISPONIBLE);
        Usuario usuario = new Usuario(1L, "Ana Martínez", "ana.martinez@example.com", UsuarioEstado.ACTIVO);
        prestamo = new Prestamo(1L, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(7));
    }

    @Test
    void GETLoans_returnsListAndStatus200() throws Exception{
        when(prestamoServicio.findAll()).thenReturn(List.of(prestamo));

        mockMvc.perform(get("/api/prestamos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(prestamo.getId()))
                .andExpect(jsonPath("$[0].libro").value(prestamo.getLibro()))
                .andExpect(jsonPath("$[0].usuario").value(prestamo.getUsuario()));
    }

    @Test
    void GETLoanById_ReturnsLoanAndStatus200() throws  Exception {
        Long id= prestamo.getId();
        when(prestamoServicio.findById(id)).thenReturn(prestamo);

        mockMvc.perform(get("/api/prestamos/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(prestamo.getId()))
                .andExpect(jsonPath("$.libro").value(prestamo.getLibro()))
                .andExpect(jsonPath("$.usuario").value(prestamo.getUsuario()));
    }

    @Test
    void GETNonExistentLoanById_ReturnsStatus404() throws  Exception {
        Long id= prestamo.getId();
        when(prestamoServicio.findById(id)).thenThrow(new PrestamoNoEncontradoExcepcion(id));

        mockMvc.perform(get("/api/prestamos/"+id))
                .andExpect(status().isNotFound());

    }

    @Test
    void POSTLoan_CreatesNewLoanAndReturns201() throws Exception {
        // El ID es asignado automaticamente por el sistema
        Prestamo prestamoSinId = new Prestamo(null, prestamo.getLibro(), prestamo.getUsuario(), prestamo.getFechaPrestamo(), prestamo.getFechaDevolucion());
        Prestamo prestamoConId = prestamo;

        when(prestamoServicio.save(any(Prestamo.class))).thenReturn(prestamoConId);

        mockMvc.perform(post("/api/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prestamoSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(prestamoConId.getId()))
                .andExpect(jsonPath("$.libro.id").value(prestamoConId.getLibro().getId()))
                .andExpect(jsonPath("$.usuario.id").value(prestamoConId.getUsuario().getId()));
    }

    @Test
    void PUTLoan_ReturnsLoanAndStatus200() throws  Exception{
        Long id= prestamo.getId();
        when(prestamoServicio.update(eq(id),any())).thenReturn(prestamo);

        mockMvc.perform(put("/api/prestamos/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prestamo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(prestamo.getId()))
                .andExpect(jsonPath("$.libro").value(prestamo.getLibro()))
                .andExpect(jsonPath("$.usuario").value(prestamo.getUsuario()));
    }

    @Test
    void PUTNonExistentLoan_ReturnsStatus404() throws  Exception {
        Long id= prestamo.getId();
        when(prestamoServicio.update(id,prestamo)).thenThrow(new PrestamoNoEncontradoExcepcion(id));

        mockMvc.perform(put("/api/prestamos/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prestamo)))
                .andExpect(status().isNotFound());
    }

    @Test
    void DELETELoan_ReturnsStatus204() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/prestamos/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void  DELETELoan_NotFound_ReturnsStatus404() throws Exception{
        Long id = 11L;

        doThrow(new PrestamoNoEncontradoExcepcion(id)).when(prestamoServicio).delete(id);

        mockMvc.perform(delete("/api/prestamos/"+id))
                .andExpect(status().isNotFound());
    }


}
