package um.programacion2.Gestion_Biblioteca.exceptions;

public class LibroNoEncontradoException extends RuntimeException{
    public LibroNoEncontradoException(Long id){
        super("No se encontro libro con el id: " +id);
    }

    public LibroNoEncontradoException(String isbn){
        super("No se encontro libro con el isbn: "+isbn);
    }
}
