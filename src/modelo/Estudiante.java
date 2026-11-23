package modelo;
import java.util.LinkedList;
import estructuras.RegistroNotas;
/*
estudiante que hereda de Persona, tiene su semestre, sus notas y su historial de materias
*/
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
        double promedio = Math.round(notas.promedioAcumulado() * 100) / 100.0;
        String textoPromedio = "" + promedio;
        if(promedio == 0)
        {
            textoPromedio = "0.0 (sin notas aun)";
        }
        return "\nId: " + getId() + " \nEstudiante: " + getNombre() + "\nSemestre: " + getSemestre() + "\nEmail: " + getEmail() + "\nPromedio acumulado: " + textoPromedio + "\nHistorial de Materias: " + historialMaterias;
    }
}
