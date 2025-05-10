package um.programacion2.Gestion_Biblioteca.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import um.programacion2.Gestion_Biblioteca.enums.UsuarioEstado;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Long id;
    private String nombre;
    private String email;
    private UsuarioEstado estado;
}
