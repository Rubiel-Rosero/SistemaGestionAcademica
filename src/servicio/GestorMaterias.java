package servicio;
import java.util.HashMap;
import modelo.Facultad;
import modelo.Materia;

public class GestorMaterias
{
    private HashMap<String, Materia> materias;
    private Facultad[] facultades;
    private GestorEstudiantes gestorEstudiantes;

    public GestorMaterias(GestorEstudiantes gestorEstudiantes)
    {
        this.gestorEstudiantes  = gestorEstudiantes;
        materias = new HashMap<>();
        facultades = new Facultad[5];
        facultades[0] = new Facultad(1, "Ingenieria");
        facultades[1] = new Facultad(2, "Ciencias Economicas");
        facultades[2] = new Facultad(3, "Salud");
        facultades[3] = new Facultad(4, "Educacion");
        facultades[4] = new Facultad(5, "Ciencias Basicas");
    }
    //crear una materia nueva
    public boolean crearMateria(Materia m)
    {
        if(materias.containsKey(m.getCodigo()))
        {
            return false;
        }
        materias.put(m.getCodigo(), m);
        return true;
    }
    //las facultades
    public Facultad[] getFacultades()
    {
        return facultades;
    }
    //buscar una materia por su codigo
    public Materia buscarMateria(String codigo)
    {
        return materias.get(codigo);
    }
    //los prerrequisitos
    public String agregarPrerrequisito(String codigo, String codigoPre)
    {
        Materia m = materias.get(codigo);
        if(m == null || materias.get(codigoPre) == null)
        {
            return "Alguna de las dos materias no existe. ";
        }
        if(codigo.equals(codigoPre))
        {
            return "Una materia no puede ser prerrequisito de ella misma";
        }
        if(!m.agregarPrerrequisito(codigoPre))
        {
            return codigoPre + " ya es prerrequisito de " + codigo;
        }
        return "Prerrequisitos agregado: " + codigoPre + " con el codigo " + codigo;
    }
}
