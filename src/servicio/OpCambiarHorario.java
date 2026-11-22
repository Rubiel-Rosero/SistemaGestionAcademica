package servicio;
/*
guarda una reserva de horario para poder deshacerla
*/
public class OpCambiarHorario extends Operacion
{
    private GestorHorarios gestorHorarios;
    private String aula;
    private int dia;
    private int hora;
    private int duracion;
    private String codigo;
    public OpCambiarHorario(GestorHorarios gestorHorarios, String aula, int dia, int hora, int duracion, String codigo)
    {
        this.gestorHorarios = gestorHorarios;
        this.aula = aula;
        this.dia = dia;
        this.hora = hora;
        this.duracion = duracion;
        this.codigo = codigo;
    }
    //para deshacer la reserva libero esas horas
    @Override
    public String deshacer() throws Exception
    {
        return gestorHorarios.liberar(aula, dia, hora, duracion);
    }
    //para rehacerla vuelvo a reservar
    @Override
    public String rehacer() throws Exception
    {
        return gestorHorarios.reservar(aula, dia, hora, duracion, codigo);
    }
    @Override
    public String getDescripcion()
    {
        return "reservar " + aula + " para " + codigo;
    }
}
