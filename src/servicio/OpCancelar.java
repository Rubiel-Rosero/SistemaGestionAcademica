package servicio;
/*
guarda una cancelacion para poder deshacerla
*/
public class OpCancelar extends Operacion
{
    private GestorMaterias gestorMaterias;
    private String id;
    private String codigo;
    public OpCancelar(GestorMaterias gestorMaterias, String id, String codigo)
    {
        this.gestorMaterias = gestorMaterias;
        this.id = id;
        this.codigo = codigo;
    }
    //para deshacer una cancelacion lo vuelvo a inscribir
    @Override
    public String deshacer() throws Exception
    {
        return gestorMaterias.inscribir(id, codigo);
    }
    //para rehacerla cancelo otra vez
    @Override
    public String rehacer() throws Exception
    {
        return gestorMaterias.cancelar(id, codigo);
    }
    @Override
    public String getDescripcion()
    {
        return "cancelar " + id + " en " + codigo;
    }
}
