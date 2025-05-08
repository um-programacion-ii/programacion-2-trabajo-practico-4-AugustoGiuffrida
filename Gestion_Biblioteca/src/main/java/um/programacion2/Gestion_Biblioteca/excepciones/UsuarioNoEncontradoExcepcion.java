package um.programacion2.Gestion_Biblioteca.excepciones;

public class UsuarioNoEncontradoExcepcion extends RuntimeException{
    public UsuarioNoEncontradoExcepcion(Long id){
        super("No encontro usuario con el id: " +id);
    }
}
