package modelo;

import estructuras.HorarioAula;

/*
los salones del campus con su capacidad y horario
*/
public class Aula
{
    private String nombre;
    private int capacidad;
    private HorarioAula horario;
    public Aula(String nombre, int capacidad)
    {
        this.nombre = nombre;
        this.capacidad = capacidad;
        horario = new HorarioAula();
    }
    public String getNombre()
    {
        return nombre;
    }
    public int getCapacidad()
    {
        return capacidad;
    }
    public HorarioAula getHorario()
    {
        return horario;
    }
    public String mostrarInformacion()
    {
        return nombre + " capacidad " + capacidad;
    }

}
