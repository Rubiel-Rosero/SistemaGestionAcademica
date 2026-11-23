package servicio;
/*
guarda una cancelacion para poder deshacerla
*/
public class OpCancelar extends Operacion
{
    private GestorMaterias gestorMaterias;
    private String id;
    private String codigo;
    //quien subio de la cola cuando se cancelo
    private String promovido;
    public OpCancelar(GestorMaterias gestorMaterias, String id, String codigo)
    {
        this.gestorMaterias = gestorMaterias;
        this.id = id;
        this.codigo = codigo;
        promovido = gestorMaterias.getUltimoPromovido();
    }
    //para deshacer una cancelacion lo vuelvo a inscribir
    @Override
    public String deshacer() throws Exception
    {
        return gestorMaterias.restaurarInscripcion(id, codigo, promovido);
    }
    //para rehacerla cancelo otra vez
    @Override
    public String rehacer() throws Exception
    {
        String resultado = gestorMaterias.cancelar(id, codigo);
        promovido = gestorMaterias.getUltimoPromovido();
        return resultado;
    }
    @Override
    public String getDescripcion()
    {
        return "cancelar " + id + " en " + codigo;
    }
}
