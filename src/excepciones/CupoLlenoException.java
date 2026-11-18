package excepciones;
/*
Excepcion personalizada que se lanza cuando la materia ya no tiene cupos disponibles.
*/
public class CupoLlenoException extends Exception
{
    public CupoLlenoException(String mensaje)
    {
        super(mensaje);
    }
}
