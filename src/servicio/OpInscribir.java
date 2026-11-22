package servicio;
/*
guarda una inscripcion para poder deshacerla
*/
public class OpInscribir extends Operacion
{
    private GestorMaterias gestorMaterias;
    private String id;
    private String codigo;
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
        return gestorMaterias.cancelar(id, codigo);
    }
    //para rehacerla vuelvo a inscribir
    @Override
    public String rehacer() throws Exception
    {
        return gestorMaterias.inscribir(id, codigo);
    }
    @Override
    public String getDescripcion()
    {
        return "inscribir " + id + " en " + codigo;
    }
}
