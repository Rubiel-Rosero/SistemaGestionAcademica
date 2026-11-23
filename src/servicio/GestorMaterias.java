package servicio;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import excepciones.ColaDeEsperaVaciaException;
import excepciones.CupoLlenoException;
import excepciones.EstudianteNoEncontradoException;
import excepciones.PreRequisitoNoAprobadoException;
import modelo.Estudiante;
import modelo.Facultad;
import modelo.Materia;
/*
guarda las materias en un HashMap y maneja inscripciones, cancelaciones y la cola de espera
*/
public class GestorMaterias
{
    private HashMap<String, Materia> materias;
    private Facultad[] facultades;
    private GestorEstudiantes gestorEstudiantes;
    //el ultimo estudiante que salio de la cola al cancelar, sirve para deshacer
    private String ultimoPromovido;
    //quien subio de la cola en cada materia al eliminar un estudiante, sirve para deshacer
    private HashMap<String, String> promovidosAlRetirar = new HashMap<>();

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
        //si el prerrequisito ya depende de esta materia se formaria un ciclo
        if(dependeDe(codigoPre, codigo))
        {
            return "No se puede agregar: " + codigoPre + " ya depende de " + codigo + " y se formaria un ciclo";
        }
        if(!m.agregarPrerrequisito(codigoPre))
        {
            return codigoPre + " ya es prerrequisito de " + codigo;
        }
        return "Prerrequisito agregado: " + codigoPre + " -> " + codigo;
    }
    //reviso de forma recursiva si una materia necesita a otra, directa o indirectamente
    private boolean dependeDe(String codigo, String buscado)
    {
        for(String pre : materias.get(codigo).getPrerrequisitos())
        {
            if(pre.equals(buscado) || dependeDe(pre, buscado))
            {
                return true;
            }
        }
        return false;
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
            if(!e.getHistorialMaterias().contains(codigo))
            {
                e.agregarAlHistorial(codigo);
            }
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
        ultimoPromovido = siguiente;
        if(siguiente != null)
        {
            m.inscribir(siguiente);
            Estudiante nuevo = gestorEstudiantes.buscar(siguiente);
            if(!nuevo.getHistorialMaterias().contains(codigo))
            {
                nuevo.agregarAlHistorial(codigo);
            }
            mensaje += "\n" + nuevo.getNombre() + " salio de la cola de espera y quedo inscrito";
        }
        return mensaje;
    }
    public String getUltimoPromovido()
    {
        return ultimoPromovido;
    }
    //devuelve la inscripcion como estaba: quien habia subido de la cola regresa al primer puesto
    public String restaurarInscripcion(String id, String codigo, String promovido) throws EstudianteNoEncontradoException
    {
        Estudiante e = gestorEstudiantes.buscar(id);
        Materia m = materias.get(codigo);
        if(m.estaInscrito(id))
        {
            return e.getNombre() + " ya esta inscrito en " + m.getNombre();
        }
        String mensaje = "Inscrito de nuevo: " + e.getNombre() + " en " + m.getNombre();
        if(promovido != null && m.estaInscrito(promovido))
        {
            m.cancelar(promovido);
            m.devolverAlFrenteDeCola(promovido);
            mensaje += "\n" + gestorEstudiantes.buscar(promovido).getNombre() + " volvio a la cola de espera, posicion 1";
        }
        if(!m.hayCupo())
        {
            //alguien mas tomo el cupo (por ejemplo el CSV), entonces queda de primero en la cola
            if(!m.estaEnCola(id))
            {
                m.devolverAlFrenteDeCola(id);
            }
            return e.getNombre() + " no recupero el cupo porque se ocupo. Quedo en la cola de espera";
        }
        m.getColaEspera().remove(id);
        m.inscribir(id);
        return mensaje;
    }
    //cuando se elimina un estudiante lo saco de todas las materias y colas
    //devuelvo las materias donde estaba inscrito para poder deshacer
    public LinkedList<String> retirarEstudiante(String id) throws EstudianteNoEncontradoException
    {
        LinkedList<String> retiradas = new LinkedList<>();
        promovidosAlRetirar = new HashMap<>();
        for(Materia m : materias.values())
        {
            if(m.estaInscrito(id))
            {
                m.cancelar(id);
                retiradas.addLast(m.getCodigo());
                //el cupo que queda libre es para el primero de la cola
                String siguiente = m.desencolar();
                if(siguiente != null)
                {
                    m.inscribir(siguiente);
                    Estudiante nuevo = gestorEstudiantes.buscar(siguiente);
                    if(!nuevo.getHistorialMaterias().contains(m.getCodigo()))
                    {
                        nuevo.agregarAlHistorial(m.getCodigo());
                    }
                    promovidosAlRetirar.put(m.getCodigo(), siguiente);
                }
            }
            if(m.estaEnCola(id))
            {
                m.getColaEspera().remove(id);
            }
        }
        return retiradas;
    }
    public HashMap<String, String> getPromovidosAlRetirar()
    {
        return promovidosAlRetirar;
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
