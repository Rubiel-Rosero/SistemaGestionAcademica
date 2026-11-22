package modelo;
import java.util.LinkedList;
import estructuras.RegistroNotas;
public class Estudiante extends Persona
{
    private int semestre;
    private RegistroNotas notas;
    private LinkedList<String> historialMaterias;
    public Estudiante(String id, String nombre, String email, int semestre)
    {
        super(id, nombre, email);
        this.semestre = semestre;
        this.historialMaterias = new LinkedList<>();
        this.notas = new RegistroNotas();
    }
    public int getSemestre()
    {
        return semestre;
    }
    public LinkedList<String> getHistorialMaterias()
    {
        return historialMaterias;
    }
    public RegistroNotas getNotas()
    {
        return notas;
    }
    public void agregarAlHistorial(String codigo)
    {
        historialMaterias.addFirst(codigo);
    }
    public String getUltimaMateria()
    {
        if(historialMaterias.isEmpty())
        {
            return null;
        }else
        {
            return historialMaterias.getFirst();
        }
    }
    @Override
    public String mostrarInformacion()
    {
        return "\nId: " + getId() + " \nEstudiante: " + getNombre() + "\nSemestre: " + getSemestre() + "\nEmail: " + getEmail() + "\nHistorial de Materias: " + historialMaterias;
    }
}
