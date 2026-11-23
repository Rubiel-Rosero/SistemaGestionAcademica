package servicio;
import java.util.TreeMap;
import estructuras.HorarioAula;
import excepciones.HorarioConflictivoException;
import modelo.Aula;
/*
guarda las aulas del campus en un TreeMap
*/
public class GestorHorarios
{
    private TreeMap<String, Aula> aulas;
    private GestorMaterias gestorMaterias;
    public GestorHorarios(GestorMaterias gestorMaterias)
    {
        this.gestorMaterias = gestorMaterias;
        aulas = new TreeMap<>();
        //aulas llenas en desorden
        aulas.put("B201", new Aula("B201", 40));
        aulas.put("A101", new Aula("A101", 30));
        aulas.put("LAB1", new Aula("LAB1", 25));
        aulas.put("AUDITORIO", new Aula("AUDITORIO", 100));
    }
    //aulas ordenadas por el nombre ya de por si el TreeMap las ordena
    public TreeMap<String, Aula> getAulas()
    {
        return aulas;
    }
    //buscar aula por su nombre
    public Aula buscarAula(String nombre)
    {
        return aulas.get(nombre);
    }
    //reservar las horas para una materia
    public String reservar(String nombreAula, int dia, int hora, int duracion, String codigo) throws HorarioConflictivoException
    {
        Aula a = aulas.get(nombreAula);
        if(a == null)
        {
            return "No existe el aula " + nombreAula;
        }
        if(gestorMaterias.buscarMateria(codigo) == null)
        {
            return "No existe una materia con el codigo " + codigo;
        }
        a.getHorario().reservar(dia, hora, duracion, codigo);
        return "Reserva exitosa: " + codigo + " en " + nombreAula + ", " + HorarioAula.dias[dia] + " de " + hora + ":00 a " + (hora + duracion) + ":00";
    }
    //liberar horas
    public String liberar(String nombreAula, int dia, int hora, int duracion)
    {
        Aula a = aulas.get(nombreAula);
        if(a == null)
        {
            return "No existe el aula " + nombreAula;
        }
        a.getHorario().liberar(dia, hora, duracion);
        return "Horario liberado: " + nombreAula + " " + HorarioAula.dias[dia] + " de " + hora + ":00 a " + (hora + duracion) + ":00";
    }
}
