package servicio;
import java.util.HashMap;
import java.util.Queue;
import excepciones.ColaDeEsperaVaciaException;
import excepciones.CupoLlenoException;
import excepciones.EstudianteNoEncontradoException;
import excepciones.PreRequisitoNoAprobadoException;
import modelo.Estudiante;
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
        return "Prerrequisito agregado: " + codigoPre + " -> " + codigo;
    }
    //inscribir un estudiante a una materia
    public String inscribir(String id, String codigo) throws EstudianteNoEncontradoException, PreRequisitoNoAprobadoException, CupoLlenoException
    {
        Estudiante e = gestorEstudiantes.buscar(id);
        Materia m = materias.get(codigo);
        if(m == null)
        {
            return "No existe una materia con el codigo " + codigo + " ";
        }
        if(m.estaInscrito(id))
        {
            return e.getNombre() + " ya esta inscrito en " + m.getNombre();
        }
        if(m.estaEnCola(id))
        {
            return e.getNombre() + " ya esta en la cola de espera de " + m.getNombre();
        }
        //revisar si aprobo
        for(String pre : m.getPrerrequisitos())
        {
            Double nota = e.getNotas().getNota(pre);
            if(nota == null || nota < 3.0)
            {
                throw new PreRequisitoNoAprobadoException("Debe aprobar " + pre + " antes de inscribir " + codigo);
            }
        }
        if(m.hayCupo())
        {
            m.inscribir(id);
            e.agregarAlHistorial(codigo);
            return "Inscrito: " + e.getNombre() + " en " + m.getNombre() + ". Cupos disponibles: " + m.getCuposDisponibles();
        }
        //si no hay cupo va a la cola de espera
        m.encolar(id);
        throw new CupoLlenoException(m.getNombre() + " esta llena. " + e.getNombre() + " quedo en la cola de espera, posicion " + m.getColaEspera().size());
    }
    //cancelar la inscripcion y realizar el proceso de pasar al primero de la cola
    public String cancelar(String id, String codigo) throws EstudianteNoEncontradoException 
    {
        Estudiante e = gestorEstudiantes.buscar(id);
        Materia m = materias.get(codigo);
        if(m == null)
        {
            return "No existe una materia con el codigo " + codigo;
        }
        if(!m.estaInscrito(id))
        {
            return e.getNombre() + " no esta inscrito en " + m.getNombre();
        }
        m.cancelar(id);
        String mensaje = "Inscripcion cancelada: " + e.getNombre() + " en " + m.getNombre();
        //si alguien estaba esperando entonces saldria de la cola y quedaria inscrito
        String siguiente = m.desencolar();
        if(siguiente != null)
        {
            m.inscribir(siguiente);
            Estudiante nuevo = gestorEstudiantes.buscar(siguiente);
            nuevo.agregarAlHistorial(codigo);
            mensaje += "\n" + nuevo.getNombre() + " salio de la cola de espera y quedo inscrito";
        }
        return mensaje;
    }
    //ver la cola de espera de una materia
    public Queue<String> verColaEspera(String codigo) throws ColaDeEsperaVaciaException
    {
        Materia m = materias.get(codigo);
        if(m == null)
        {
            return null;
        }
        if(m.getColaEspera().isEmpty())
        {
            throw new ColaDeEsperaVaciaException("La cola de espera de " + m.getNombre() + " esta vacia");
        }
        return m.getColaEspera();
    }
}
