package um.programacion2.Gestion_Biblioteca.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import um.programacion2.Gestion_Biblioteca.enums.LibroEstado;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    private Long id;
    private String isbn;
    private String titulo;
    private String autor;
    private LibroEstado estado;
}
