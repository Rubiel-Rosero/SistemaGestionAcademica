package excepciones;
/*
excepcion personalizada que se lanza cuando el archivo CSV no existe, no se puede leer o tiene un formato invalido.
*/
public class ArchivoInvalidoException extends Exception
{
    public ArchivoInvalidoException(String mensaje)
    {
        super(mensaje);
    }
}
