package um.programacion2.Gestion_Biblioteca.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import um.programacion2.Gestion_Biblioteca.enums.LibroEstado;
import um.programacion2.Gestion_Biblioteca.exceptions.LibroNoEncontradoException;
import um.programacion2.Gestion_Biblioteca.models.Libro;
import um.programacion2.Gestion_Biblioteca.services.LibroServicio;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibroController.class)
public class TestLibroController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LibroServicio libroServicio;


    @Test
    void GETBooks_returnsListAndStatus200() throws Exception{
        List<Libro> libros = List.of(
                new Libro(1L, "978-9876543210", "Programación II", "Juan Pérez", LibroEstado.DISPONIBLE),
                new Libro(2L, "978-9501234567", "Estructuras de Datos", "María Gómez", LibroEstado.PRESTADO));

        when(libroServicio.findAll()).thenReturn(libros);

        mockMvc.perform(get("/api/libros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isbn").value("978-9876543210"))
                .andExpect(jsonPath("$[0].titulo").value("Programación II"))
                .andExpect(jsonPath("$[0].autor").value("Juan Pérez"))
                .andExpect(jsonPath("$[1].isbn").value("978-9501234567"))
                .andExpect(jsonPath("$[1].titulo").value("Estructuras de Datos"))
                .andExpect(jsonPath("$[1].autor").value("María Gómez"));
    }

    @Test
    void GETBooKByIsbn_ReturnsBookAndStatus200() throws  Exception{
        String isbn = "978-9876543210";
        Libro libro = new Libro(1L, isbn, "Programación II", "Juan Pérez", LibroEstado.DISPONIBLE);
        when(libroServicio.findByIsbn(isbn)).thenReturn(libro);

        mockMvc.perform(get("/api/libros/isbn/" + isbn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("978-9876543210"))
                .andExpect(jsonPath("$.titulo").value("Programación II"))
                .andExpect(jsonPath("$.autor").value("Juan Pérez"));
    }


    @Test
    void GETNonExistentBooKByIsbn_ReturnsStatus404() throws  Exception{
        String isbn = "";
        when(libroServicio.findByIsbn(isbn)).thenThrow(new LibroNoEncontradoException(isbn));

        mockMvc.perform(get("/api/libros/isbn/" + isbn))
                .andExpect(status().isNotFound());
    }

    @Test
    void GETBooKById_ReturnsBookAndStatus200() throws  Exception{
        Long id = 1L;
        Libro libro = new Libro(id, "978-9876543210", "Programación II", "Juan Pérez", LibroEstado.DISPONIBLE);
        when(libroServicio.findById(id)).thenReturn(libro);

        mockMvc.perform(get("/api/libros/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("978-9876543210"))
                .andExpect(jsonPath("$.titulo").value("Programación II"))
                .andExpect(jsonPath("$.autor").value("Juan Pérez"));
    }


    @Test
    void GETNonExistentBooKById_ReturnsStatus404() throws  Exception{
        Long id = 1L;
        when(libroServicio.findById(id)).thenThrow(new LibroNoEncontradoException(id));

        mockMvc.perform(get("/api/libros/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void POSTBook_ReturnsBookAndStatus200() throws  Exception{
        Libro libroNuevo = new Libro(null, "978-9876543210", "Programación II", "Juan Pérez", LibroEstado.DISPONIBLE);
        Libro libroGuardado = new Libro(1L, "978-9876543210", "Programación II", "Juan Pérez", LibroEstado.DISPONIBLE);
        when(libroServicio.save(any())).thenReturn(libroGuardado);

        mockMvc.perform(post("/api/libros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(libroNuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn").value("978-9876543210"))
                .andExpect(jsonPath("$.titulo").value("Programación II"))
                .andExpect(jsonPath("$.autor").value("Juan Pérez"));

    }

    @Test
    void PUTBook_ReturnsBookAndStatus200() throws  Exception{
        Libro libro = new Libro(1L, "978-9876543210", "Programación II", "Juan Pérez", LibroEstado.DISPONIBLE);
        when(libroServicio.update(eq(1L),any())).thenReturn(libro);

        mockMvc.perform(put("/api/libros/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(libro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("978-9876543210"))
                .andExpect(jsonPath("$.titulo").value("Programación II"))
                .andExpect(jsonPath("$.autor").value("Juan Pérez"));
    }

    @Test
    void PUTNonExistentBook_ReturnsStatus404() throws  Exception{
        Long id = 1L;
        Libro libro = new Libro(id, "978-9876543210", "Programación II", "Juan Pérez", LibroEstado.DISPONIBLE);
        when(libroServicio.update(id,libro)).thenThrow(new LibroNoEncontradoException(id));

        mockMvc.perform(put("/api/libros/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(libro)))
                .andExpect(status().isNotFound());
    }

    @Test
    void DELETEBook_ReturnsStatus204() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/libros/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void  DELETEBook_NotFound_ReturnsStatus404() throws Exception{
        Long id = 11L;

        doThrow(new LibroNoEncontradoException(id)).when(libroServicio).delete(id);

        mockMvc.perform(delete("/api/libros/"+id))
                .andExpect(status().isNotFound());
    }


}
