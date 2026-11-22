package servicio;

import java.util.LinkedList;

import modelo.Estudiante;
import modelo.Materia;
/*
guarda el estudiante eliminado y las materias donde estaba, para poder devolverlo
*/
public class OpEliminarEstudiante extends Operacion
{
    private GestorEstudiantes gestorEstudiantes;
    private GestorMaterias gestorMaterias;
    private Estudiante estudiante;
    private LinkedList<String> materias;
    public OpEliminarEstudiante(GestorEstudiantes gestorEstudiantes, GestorMaterias gestorMaterias, Estudiante estudiante, LinkedList<String> materias)
    {
        this.gestorEstudiantes = gestorEstudiantes;
        this.gestorMaterias = gestorMaterias;
        this.estudiante = estudiante;
        this.materias = materias;
    }
    //lo vuelvo a registrar y lo inscribo otra vez en sus materias
    @Override
    public String deshacer() throws Exception
    {
        gestorEstudiantes.registrar(estudiante);
        for(String codigo : materias)
        {
            Materia m = gestorMaterias.buscarMateria(codigo);
            if(m.hayCupo())
            {
                m.inscribir(estudiante.getId());
            }else
            {
                m.encolar(estudiante.getId());
            }
        }
        return estudiante.getNombre() + " volvio al sistema. Materias: " + materias;
    }
    //lo elimino otra vez
    @Override
    public String rehacer() throws Exception
    {
        gestorEstudiantes.eliminar(estudiante.getId());
        materias = gestorMaterias.retirarEstudiante(estudiante.getId());
        return estudiante.getNombre() + " eliminado otra vez";
    }
    @Override
    public String getDescripcion()
    {
        return "eliminar estudiante " + estudiante.getId();
    }
}
