package servicio;
import java.util.HashMap;
import excepciones.EstudianteNoEncontradoException;
import modelo.Estudiante;
//HashMap para lo estudiantes
public class GestorEstudiantes
{
    private Estudiante [] estudiantes;
    private int cantidad  = 0;
    private HashMap<String, Estudiante> indice;
    public GestorEstudiantes()
    {
        estudiantes = new Estudiante[500];
        cantidad = 0;
        indice = new HashMap<>();
    }
    //registrar un estudiante
    public boolean registrar(Estudiante e)
    {   
        if(indice.containsKey(e.getId()) || cantidad == estudiantes.length)
        {
            return false;
        }else
        {
            estudiantes[cantidad++] = e;
            indice.put(e.getId(), e);
            return true;
        }
    }
    //buscar un estudiante
    public Estudiante buscar(String id) throws EstudianteNoEncontradoException
    {
        Estudiante e = indice.get(id);
        if(e == null)
        {
            throw new EstudianteNoEncontradoException("No existe el estudiante con el Id: " + id);
        }
        return e;
    }
    //listar los estudiantes
    public Estudiante[] listar()
    {
        Estudiante[] lista = new Estudiante[cantidad];
        for(int i = 0; i < cantidad; i++)
        {
            lista[i] = estudiantes[i];
        }
        return lista;
    }
    //eliminar estudiante
    public Estudiante eliminar(String id) throws EstudianteNoEncontradoException 
    {   
        Estudiante e = buscar(id);//si no existe sale la excepcion
        int pos = 0;
        while(estudiantes[pos] != e)//buscamos
        {
            pos++;
        }
        for(int j = pos; j < cantidad - 1; j++)//los corremos a la izquierda
        {
            estudiantes[j] = estudiantes[j + 1];
        }
        estudiantes[cantidad - 1] = null; //vaciamos el ultimo
        cantidad--;
        indice.remove(id);
        return e;
    }
}
