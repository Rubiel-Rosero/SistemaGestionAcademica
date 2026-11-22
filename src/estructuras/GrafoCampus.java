package estructuras;
/*
esta seria el grafo del campus tenemos la matriz con los metros entre cada par de edificios y el algoritmo
Dijkstra para encontrar la ruta mas corta
*/
import java.util.LinkedList;
import modelo.Edificio;
public class GrafoCampus 
{
    private int[][] distancias;
    private Edificio[] edificios;
    public GrafoCampus(Edificio[] edificios)
    {
        this.edificios = edificios;
        distancias = new int[edificios.length][edificios.length];
    }
    //conectar los edificios en dos sentidos
    public void agregarConexion(int a, int b, int metros)
    {
        distancias[a][b] = metros;
        distancias[b][a] = metros;
    }
    public int getDistancia(int a, int b)
    {
        return distancias[a][b];
    }
    public Edificio[] getEdificios()
    {
        return edificios;
    }
    //Dijkstra para los indices del camino o null si no hay camino
    public LinkedList<Integer> calcularRutaMasCorta(int origen, int destino)
    {
        int n = edificios.length;
        int[] distancia = new int[n];
        boolean[] visitado = new boolean[n];
        int[] anterior = new int[n];
        //como al inicio no conozco ninguna distancia, las pongo infinitas
        for(int i = 0; i < n; i++)
        {
            distancia[i] = Integer.MAX_VALUE;
            anterior[i] = -1;
        }
        distancia[origen] = 0;
        for(int vuelta = 0; vuelta < n; vuelta++)
        {
            //miro el edificio que no he visitado y que este mas cerca
            int actual = -1;
            for(int i = 0; i < n; i++)
            {
                if(!visitado[i] && distancia[i] != Integer.MAX_VALUE)
                {
                    if(actual == -1 || distancia[i] < distancia[actual])
                    {
                        actual = i;
                    }
                }
            }
            if(actual == -1)
            {
                break;
            }
            visitado[actual] = true;
            //miro los cercanos, si pasan por el que estoy el camino es mas corto, lo actualizo
            for(int v = 0; v < n; v++)
            {
                if(distancias[actual][v] > 0 && !visitado[v])
                {
                    int nueva = distancia[actual] + distancias[actual][v];
                    if(nueva < distancia[v])
                    {
                        distancia[v] = nueva;
                        anterior[v] = actual;
                    }
                }
            }
        }
        if(distancia[destino] == Integer.MAX_VALUE)
        {
            return null;
        }
        //armo el camino de atras hacia adelante con el addFirst
        LinkedList<Integer> camino = new LinkedList<>();
        int paso = destino;
        while(paso != -1)
        {
            camino.addFirst(paso);
            paso = anterior[paso];
        }
        return camino;
    }
}
