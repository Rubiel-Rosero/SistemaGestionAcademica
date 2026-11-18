package excepciones;
/*
Excepcion personalizada que se lanza cuando el estudiante no ha aprobado un prerrequisito de la materia.
*/
public class PreRequisitoNoAprobadoException extends Exception
{
    public PreRequisitoNoAprobadoException(String mensaje)
    {
        super(mensaje);
    }
}
