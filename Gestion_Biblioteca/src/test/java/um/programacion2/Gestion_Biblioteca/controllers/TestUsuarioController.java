package um.programacion2.Gestion_Biblioteca.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import um.programacion2.Gestion_Biblioteca.enums.UsuarioEstado;
import um.programacion2.Gestion_Biblioteca.exceptions.UsuarioNoEncontradoExcepcion;
import um.programacion2.Gestion_Biblioteca.models.Usuario;
import um.programacion2.Gestion_Biblioteca.services.UsuarioServicio;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class TestUsuarioController {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UsuarioServicio usuarioServicio;

    Usuario usuario;

    @BeforeEach
    void setUp(){
        usuario = new Usuario(1L, "Ana Martínez", "ana.martinez@example.com", UsuarioEstado.ACTIVO);
    }

    @Test
    void GETUser_returnsListAndStatus200() throws Exception {
        when(usuarioServicio.findAll()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Ana Martínez"))
                .andExpect(jsonPath("$[0].email").value("ana.martinez@example.com"));
    }

    @Test
    void GETBooKById_ReturnsBookAndStatus200() throws  Exception {
        Long id = usuario.getId();
        when(usuarioServicio.findById(id)).thenReturn(usuario);

        mockMvc.perform(get("/api/usuarios/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Ana Martínez"))
                .andExpect(jsonPath("$.email").value("ana.martinez@example.com"));
    }

    @Test
    void GETNonExistentUserById_ReturnsStatus404() throws  Exception {
        Long id = usuario.getId();
        when(usuarioServicio.findById(id)).thenThrow(new UsuarioNoEncontradoExcepcion(id));

        mockMvc.perform(get("/api/usuarios"+id))
                .andExpect(status().isNotFound());
    }

    @Test
    void POSTUser_CreatesNewUserAndReturns201() throws Exception {
        Usuario usuarioSinId = new Usuario(null, usuario.getNombre(), usuario.getEmail(), usuario.getEstado());
        Usuario usuarioConId = usuario;

        when(usuarioServicio.save(any(Usuario.class))).thenReturn(usuarioConId);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioSinId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(usuarioConId.getId()))
                .andExpect(jsonPath("$.nombre").value(usuarioConId.getNombre()))
                .andExpect(jsonPath("$.email").value(usuarioConId.getEmail()));
    }

    @Test
    void PUTUser_ReturnsUserAndStatus200() throws  Exception {
        Long id = usuario.getId();
        when(usuarioServicio.update(eq(id),any())).thenReturn(usuario);

        mockMvc.perform(put("/api/usuarios/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Ana Martínez"))
                .andExpect(jsonPath("$.email").value("ana.martinez@example.com"));
    }

    @Test
    void PUTNonExistentUser_ReturnsStatus404() throws  Exception {
        Long id = usuario.getId();
        when(usuarioServicio.update(id,usuario)).thenThrow(new UsuarioNoEncontradoExcepcion(id));

        mockMvc.perform(put("/api/usuarios/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isNotFound());
    }

    @Test
    void DELETEUser_ReturnsStatus204() throws Exception {
        Long id = usuario.getId();

        mockMvc.perform(delete("/api/usuarios/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void  DELETEUser_NotFound_ReturnsStatus404() throws Exception{
        Long id = 11L;

        doThrow(new UsuarioNoEncontradoExcepcion(id)).when(usuarioServicio).delete(id);

        mockMvc.perform(delete("/api/usuarios/"+id))
                .andExpect(status().isNotFound());
    }



}
