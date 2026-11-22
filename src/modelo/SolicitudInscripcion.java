package modelo;
/*
una solicitud de inscripcion que viene del archivo CSV
*/
public class SolicitudInscripcion
{
    private String idEstudiante;
    private String codigoMateria;
    public SolicitudInscripcion(String idEstudiante, String codigoMateria)
    {
        this.idEstudiante = idEstudiante;
        this.codigoMateria = codigoMateria;
    }
    public String getIdEstudiante()
    {
        return idEstudiante;
    }
    public String getCodigoMateria()
    {
        return codigoMateria;
    }
}
