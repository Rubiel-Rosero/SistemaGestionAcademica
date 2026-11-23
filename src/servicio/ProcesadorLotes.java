package servicio;
import java.util.LinkedList;
import java.util.Queue;
import excepciones.ArchivoInvalidoException;
import excepciones.CupoLlenoException;
import excepciones.EstudianteNoEncontradoException;
import excepciones.PreRequisitoNoAprobadoException;
import modelo.SolicitudInscripcion;
import util.LectorCSV;
/*
carga las solicitudes del CSV en una cola y las procesa en orden de llegada
*/
public class ProcesadorLotes
{
    private Queue<SolicitudInscripcion> cola;
    private GestorMaterias gestorMaterias;
    public ProcesadorLotes(GestorMaterias gestorMaterias)
    {
        this.gestorMaterias = gestorMaterias;
        cola = new LinkedList<>();
    }
    //leo el archivo y meto cada solicitud en la cola
    public int cargar(String ruta) throws ArchivoInvalidoException
    {
        LinkedList<String[]> filas = LectorCSV.leer(ruta);
        for(String[] fila : filas)
        {
            cola.offer(new SolicitudInscripcion(fila[0].trim(), fila[1].trim().toUpperCase()));
        }
        return filas.size();
    }
    //saco las solicitudes una por una y las inscribo
    public String procesar()
    {
        String resultado = "Procesando cola...\n";
        int total = cola.size();
        int numero = 0;
        int exitosas = 0;
        int fallidas = 0;
        while(!cola.isEmpty())
        {
            SolicitudInscripcion s = cola.poll();
            numero++;
            String linea = "[" + numero + "/" + total + "] " + s.getIdEstudiante() + " -> " + s.getCodigoMateria() + " -> ";
            try
            {
                String mensaje = gestorMaterias.inscribir(s.getIdEstudiante(), s.getCodigoMateria());
                if(mensaje.startsWith("Inscrito"))
                {
                    exitosas++;
                    resultado = resultado + linea + "Exitosa\n";
                }else
                {
                    fallidas++;
                    resultado = resultado + linea + "Fallida (" + mensaje + ")\n";
                }
            }catch(EstudianteNoEncontradoException ex)
            {
                fallidas++;
                resultado = resultado + linea + "Fallida (EstudianteNoEncontradoException - " + ex.getMessage() + ")\n";
            }catch(PreRequisitoNoAprobadoException ex)
            {
                fallidas++;
                resultado = resultado + linea + "Fallida (PreRequisitoNoAprobadoException - " + ex.getMessage() + ")\n";
            }catch(CupoLlenoException ex)
            {
                fallidas++;
                resultado = resultado + linea + "Fallida (CupoLlenoException - " + ex.getMessage() + ")\n";
            }
        }
        resultado = resultado + "\n=== RESUMEN ===\nExitosas: " + exitosas + "\nFallidas: " + fallidas;
        return resultado;
    }
}
