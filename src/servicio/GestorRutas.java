package servicio;
/*
manejo de los edificios del campus y el calculo de las rutas
*/
import java.util.LinkedList;
import estructuras.GrafoCampus;
import modelo.Edificio;
public class GestorRutas
{
    private GrafoCampus grafo;
    public GestorRutas()
    {
        Edificio[] edificios = new Edificio[5];
        edificios[0] = new Edificio(0, "Ingenieria");
        edificios[1] = new Edificio(1, "Biblioteca");
        edificios[2] = new Edificio(2, "Cafeteria");
        edificios[3] = new Edificio(3, "Rectoria");
        edificios[4] = new Edificio(4, "Laboratorios");
        grafo = new GrafoCampus(edificios);
        //conexiones en metros
        grafo.agregarConexion(0, 1, 100);
        grafo.agregarConexion(0, 2, 150);
        grafo.agregarConexion(1, 3, 300);
        grafo.agregarConexion(2, 3, 180);
        grafo.agregarConexion(3, 4, 120);
    }
    public Edificio[] getEdificios()
    {
        return grafo.getEdificios();
    }
    //conectar dos edificios
    public String agregarConexion(int a, int b, int metros)
    {
        Edificio[] e = grafo.getEdificios();
        if(a == b)
        {
            return "Un edificio no se puede conectar con el mismo ";
        }
        grafo.agregarConexion(a, b, metros);
        return "Conexion agregada: " + e[a].getNombre() + " <-> " + e[b].getNombre() + " (" + metros + "m)";
    }
    //calcular la ruta mas corta con el mensaje para el tramo
    public String calcularRuta(int origen, int destino)
    {
        Edificio[] e = grafo.getEdificios();
        if(origen == destino)
        {
            return "Ya esta en " + e[origen].getNombre();
        }
        LinkedList<Integer> camino = grafo.calcularRutaMasCorta(origen, destino);
        if(camino == null)
        {
            return  "No hay camino entre " + e[origen].getNombre() + " y " + e[destino].getNombre();
        }
        String ruta = e[camino.get(0)].getNombre();
        int total = 0;
        for(int i = 1; i < camino.size(); i++)
        {
            int metros = grafo.getDistancia(camino.get(i - 1), camino.get(i));
            total = total + metros;
            ruta = ruta + " -> " + e[camino.get(i)].getNombre() + " (" + metros + "m)";
        }
        return ruta + "\nDistancia TOTAL: " + total + " metros";
    }
}
