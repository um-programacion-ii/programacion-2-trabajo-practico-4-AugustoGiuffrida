package um.programacion2.Gestion_Biblioteca.exceptions;

public class PrestamoNoEncontradoExcepcion extends RuntimeException {
    public PrestamoNoEncontradoExcepcion(Long id) {
        super("No encontro prestamo con el id: "+id);
    }
}
