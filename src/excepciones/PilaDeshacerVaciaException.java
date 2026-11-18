package excepciones;
/*
Excepcion personalizada que se lanza cuando no hay operaciones para deshacer o rehacer.
*/
public class PilaDeshacerVaciaException extends Exception
{
    public PilaDeshacerVaciaException(String mensaje)
    {
        super(mensaje);
    }
}
