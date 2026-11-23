package servicio;
import modelo.Estudiante;
/*
guardo una nota registrada, con la nota que habia antes, para poder deshacerla
*/
public class OpRegistrarNota extends Operacion
{
    private Estudiante estudiante;
    private int semestre;
    private String codigo;
    private Double notaAnterior;
    private double notaNueva;
    public OpRegistrarNota(Estudiante estudiante, int semestre, String codigo, Double notaAnterior, double notaNueva)
    {
        this.estudiante = estudiante;
        this.semestre = semestre;
        this.codigo = codigo;
        this.notaAnterior = notaAnterior;
        this.notaNueva = notaNueva;
    }
    //si no habia nota la borro, si habia vuelvo a poner la anterior
    @Override
    public String deshacer() throws Exception
    {
        if(notaAnterior == null)
        {
            estudiante.getNotas().eliminar(semestre, codigo);
            return "Se quito la nota de " + codigo;
        }
        estudiante.getNotas().registrar(semestre, codigo, notaAnterior);
        return codigo + " volvio a " + notaAnterior;
    }
    //pongo otra vez la nota nueva
    @Override
    public String rehacer() throws Exception
    {
        estudiante.getNotas().registrar(semestre, codigo, notaNueva);
        return codigo + " quedo en " + notaNueva;
    }
    @Override
    public String getDescripcion()
    {
        return "nota de " + estudiante.getId() + " en " + codigo;
    }
}
