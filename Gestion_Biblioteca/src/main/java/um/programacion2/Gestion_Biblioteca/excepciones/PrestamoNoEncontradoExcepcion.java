package um.programacion2.Gestion_Biblioteca.excepciones;

public class PrestamoNoEncontradoExcepcion extends RuntimeException {
    public PrestamoNoEncontradoExcepcion(Long id) {
        super("No encontro prestamo con el id: "+id);
    }
}
