package servicio;
/*
guardo una inscripcion para poder deshacerla
*/
public class OpInscribir extends Operacion
{
    private GestorMaterias gestorMaterias;
    private String id;
    private String codigo;
    //quien subio de la cola cuando se deshizo la inscripcion
    private String promovido;
    public OpInscribir(GestorMaterias gestorMaterias, String id, String codigo)
    {
        this.gestorMaterias = gestorMaterias;
        this.id = id;
        this.codigo = codigo;
    }
    //para deshacer una inscripcion la cancelo
    @Override
    public String deshacer() throws Exception
    {
        String resultado = gestorMaterias.cancelar(id, codigo);
        promovido = gestorMaterias.getUltimoPromovido();
        return resultado;
    }
    //para rehacerla vuelvo a inscribir
    @Override
    public String rehacer() throws Exception
    {
        return gestorMaterias.restaurarInscripcion(id, codigo, promovido);
    }
    @Override
    public String getDescripcion()
    {
        return "inscribir " + id + " en " + codigo;
    }
}
