package modelo;

import java.util.LinkedList;
import java.util.Queue;

public class Materia
{
    private String codigo;
    private String nombre;
    private int cuposMaximos;
    private int creditos;
    private Facultad facultad;
    private Profesor profesor;
    private LinkedList<String> prerrequisitos;
    private LinkedList<String> inscritos;
    private Queue<String> colaEspera;
    public Materia(String codigo, String nombre, int cuposMaximos, int creditos, Facultad facultad, Profesor profesor)
    {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cuposMaximos = cuposMaximos;
        this.creditos = creditos;
        this.facultad = facultad;
        this.profesor = profesor;
        prerrequisitos = new LinkedList<>();
        inscritos = new LinkedList<>();
        colaEspera = new LinkedList<>();
    }
    //agregar algun prerrequisito
    public boolean agregarPrerrequisito(String codigo)
    {
        if(prerrequisitos.contains(codigo))
        {
            return false;
        }
        prerrequisitos.addLast(codigo);
        return true;
    }
    //saber si hay cupo
    public boolean hayCupo()
    {
        return inscritos.size() < cuposMaximos;
    }
    //saber quienes estan inscritos
    public boolean estaInscrito(String id)
    {
        return inscritos.contains(id);
    }
    //saber quienes estar en cola
    public boolean estaEnCola(String id)
    {
        return colaEspera.contains(id);
    }
    //inscribir estudiante
    public void inscribir(String id)
    {
        inscritos.addLast(id);
    }
    //cancelar inscripcion
    public void cancelar(String id)
    {
        inscritos.remove(id);
    }
    //agregar a la cola de espera
    public void encolar(String id)
    {
        colaEspera.offer(id);
    }
    //sacar al primero de la cola
    public String desencolar()
    {
        return colaEspera.poll();
    }
    //saber que cupos quedan
    public int getCuposDisponibles()
    {
        return cuposMaximos - inscritos.size();
    }
    //getters
    public String getCodigo()
    {
        return codigo;
    }
    public String getNombre()
    {
        return nombre;
    }
    public int getCuposMaximos()
    {
        return cuposMaximos;
    }
    public int getCreditos()
    {
        return creditos;
    }
    public Facultad getFacultad()
    {
        return facultad;
    }
    public Profesor getProfesor()
    {
        return profesor;
    }
    public LinkedList<String> getPrerrequisitos()
    {
        return prerrequisitos;
    }
    public LinkedList<String> getInscritos()
    {
        return inscritos;
    }
    public Queue<String> getColaEspera()
    {
        return colaEspera;
    }
    //mostrar los datos de la materia
    public String mostrarInformacion()
    {
        return "Codigo: " + codigo + "\nNombre: " + nombre + "\nCreditos: " + creditos + "\nFacultad: " + facultad.getNombre() + "\nCupos: " + inscritos.size();
    }

}
