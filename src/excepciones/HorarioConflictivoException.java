package excepciones;
/*
excepcion personalizada que se lanza cuando una hora del bloque pedido ya esta reservada en el aula.
*/
public class HorarioConflictivoException extends Exception
{
    public HorarioConflictivoException(String mensaje)
    {
        super(mensaje);
    }
}
