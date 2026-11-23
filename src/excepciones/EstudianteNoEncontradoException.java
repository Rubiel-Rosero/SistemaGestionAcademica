package excepciones;
/*
3excepcion personalizada que se lanza cuando no existe un estudiante con el ID buscado.
*/
public class EstudianteNoEncontradoException extends Exception
{
    public EstudianteNoEncontradoException(String mensaje)
    {
        super(mensaje);
    }
}
