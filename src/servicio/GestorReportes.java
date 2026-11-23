package servicio;
import java.util.Stack;
import estructuras.RegistroNotas;
import excepciones.EstudianteNoEncontradoException;
import modelo.Estudiante;
import modelo.Evaluacion;
/*
registro las notas y armo los reportes, con dos pilas para ir atras y adelante
*/
public class GestorReportes
{
    private GestorEstudiantes gestorEstudiantes;
    private GestorMaterias gestorMaterias;
    private Stack<String> pilaAtras;
    private Stack<String> pilaAdelante;
    private String reporteActual;
    public GestorReportes(GestorEstudiantes gestorEstudiantes, GestorMaterias gestorMaterias)
    {
        this.gestorEstudiantes = gestorEstudiantes;
        this.gestorMaterias = gestorMaterias;
        pilaAtras = new Stack<>();
        pilaAdelante = new Stack<>();
        reporteActual = null;
    }
    //calculo la nota final con la evaluaciones
    public String registrarNota(String id, int semestre, String codigo, Evaluacion[] evaluaciones)throws EstudianteNoEncontradoException
    {
        Estudiante e = gestorEstudiantes.buscar(id);
        if(gestorMaterias.buscarMateria(codigo) == null)
        {
            return "La materia " + codigo + " no existe";
        }
        if(e.getNotas().semestreLleno(semestre))
        {
            return "El semestre " + semestre + " ya tiene 20 materias";
        }
        double notaFinal = 0;
        for(Evaluacion ev : evaluaciones)
        {
            notaFinal = notaFinal + ev.calcularAporte();
        }
        //redondeo a dos decimales
        notaFinal = Math.round(notaFinal * 100) / 100.0;
        //Double y no double porque puede llegar null si la materia es nueva
        Double anterior = e.getNotas().registrar(semestre, codigo, notaFinal);
        if(!e.getHistorialMaterias().contains(codigo))
        {
            e.agregarAlHistorial(codigo);
        }
        String mensaje = "Nota registrada: " + codigo + " = " + notaFinal;
        if(anterior != null)
        {
            mensaje = mensaje + " (antes " + anterior + ")";
        }
        return mensaje;
    }
    //armo el reporte del estudiante semestre por semestre
    public String generarReporte(String id) throws EstudianteNoEncontradoException
    {
        Estudiante e = gestorEstudiantes.buscar(id);
        RegistroNotas notas = e.getNotas();
        String reporte = "=== REPORTE ACADEMICO ===\nEstudiante: " + e.getNombre() + " (ID: " + e.getId() + ")";
        int aprobadas = 0;
        int reprobadas = 0;
        for(int s = 1; s <= 10; s++)
        {
            String lineas = "";
            for(int j = 0; j < 20; j++)
            {
                if(notas.getCodigo(s, j) != null)
                {
                    lineas = lineas + "\n  " + notas.getCodigo(s, j) + ": " + notas.getNota(s, j);
                    if(notas.getNota(s, j) >= 3.0)
                    {
                        aprobadas++;
                    }else
                    {
                        reprobadas++;
                    }
                }
            }
            //solo muestro los semestres que tienen materias
            if(!lineas.equals(""))
            {
                double promedio = Math.round(notas.promedioSemestre(s) * 100) / 100.0;
                reporte = reporte + "\nSemestre " + s + lineas + "\n  Promedio: " + promedio;
            }
        }
        double acumulado = Math.round(notas.promedioAcumulado() * 100) / 100.0;
        reporte = reporte + "\n=== RESUMEN ===\nPromedio acumulado: " + acumulado;
        reporte = reporte + "\nMaterias aprobadas: " + aprobadas + "\nMaterias reprobadas: " + reprobadas;
        reporte = reporte + "\nCodigos reprobados: " + notas.materiasReprobadas();
        //el reporte que estaba se va a la pila de atras, maximo 10
        if(reporteActual != null)
        {
            pilaAtras.push(reporteActual);
            if(pilaAtras.size() > 10)
            {
                pilaAtras.remove(0);
            }
        }
        pilaAdelante.clear();
        reporteActual = reporte;
        return reporte;
    }
    //vuelvo al reporte anterior
    public String atras()
    {
        if(pilaAtras.isEmpty())
        {
            return "No hay reportes anteriores";
        }
        pilaAdelante.push(reporteActual);
        reporteActual = pilaAtras.pop();
        return reporteActual;
    }
    //voy al reporte siguiente
    public String adelante()
    {
        if(pilaAdelante.isEmpty())
        {
            return "No hay reportes siguientes";
        }
        pilaAtras.push(reporteActual);
        reporteActual = pilaAdelante.pop();
        return reporteActual;
    }
}
