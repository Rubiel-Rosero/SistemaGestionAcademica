package servicio;
/*
una operacion que se puede deshacer y rehacer, cada hija sabe como hacerlo
*/
public abstract class Operacion
{
    public abstract String deshacer() throws Exception;
    public abstract String rehacer() throws Exception;
    public abstract String getDescripcion();
}
