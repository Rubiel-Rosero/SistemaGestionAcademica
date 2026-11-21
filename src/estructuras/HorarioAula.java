package estructuras;

import excepciones.HorarioConflictivoException;

/*
matriz de 7 dias por 24 horas que guarda si cada hora de un aula esta ocupada y que materia la tiene reservada
*/
public class HorarioAula
{
    public static final String[] DIAS = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes" , "Sabado"};
    private boolean[][] ocupado;
    private String[][] materiaAsignada;
    public HorarioAula()
    {
        ocupado = new boolean[7][24];
        materiaAsignada = new String[7][24];
    }
    //reservar unas horas
    public void reservar(int dia, int hora, int duracion, String codigo)throws HorarioConflictivoException
    {
        //primero reviso las horas, si una hora esta ocupada no reservo ninguna
        for(int h = hora; h < hora + duracion; h++)
        {
            if(ocupado[dia][h])
            {
                throw new HorarioConflictivoException(DIAS[dia] + " " + h + ":00 ya esta reservada por " + materiaAsignada[dia][h]);
            }
        }
        //si todas estan libres, se reserva
        for(int h = hora; h < hora + duracion; h++)
        {
            ocupado[dia][h] = true;
            materiaAsignada[dia][h] = codigo;
        }
    }
    //liberar las horas
    public void liberar(int dia, int hora, int duracion)
    {
        for(int h = hora; h < hora + duracion; h++)
        {
            ocupado[dia][h] = false;
            materiaAsignada[dia][h] = null;
        }
    }
    //saber si una hora esta libre
    public boolean consultarDisponibilidad(int dia, int hora)
    {
        return !ocupado[dia][hora];
    }
    //saber que materia ya tiene hora asignada
    public String getMateria(int dia, int hora)
    {
        return materiaAsignada[dia][hora];
    }
}
