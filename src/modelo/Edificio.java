package modelo;
/*
edificio del campus
*/
public class Edificio
{
    private int indice;
    private String nombre;
    public Edificio(int indice, String nombre)
    {
        this.indice = indice;
        this.nombre = nombre;
    }
    public int getIndice()
    {
        return indice;
    }
    public String getNombre()
    {
        return nombre;
    }
}
