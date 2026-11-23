package excepciones;
/*
excepcion personalizada que se lanza cuando la cola de espera de una materia esta vacia.
*/
public class ColaDeEsperaVaciaException extends Exception
{
    public ColaDeEsperaVaciaException(String mensaje)
    {
        super(mensaje);
    }
}
