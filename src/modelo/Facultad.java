package modelo;
/*
una facultad de la universidad, hay 5 fijas en un arreglo
*/
public class Facultad
{
    private int id;
    private String nombre;
    public Facultad(int id, String nombre)
    {
        this.id = id;
        this.nombre = nombre;
    }
    public int getId()
    {
        return id;
    }
    public String getNombre()
    {
        return nombre;
    }
}
