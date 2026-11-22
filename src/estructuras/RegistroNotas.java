package estructuras;
/*
matriz notas filas son semestre, columnas son materias
*/
import java.util.LinkedList;
public class RegistroNotas
{
    private Double[][] notas;  
    private String[][] codigos;
    public RegistroNotas()
    {
        notas = new Double[10][20];
        codigos = new String[10][20];
    }
    //buscar la nota de una materia por su codigo
    public Double getNota(String codigo)
    {
        for(int i = 0; i < codigos.length; i++)
        {
            for(int j = 0; j < codigos[i].length; j++)
            {
                if(codigos[i][j] != null && codigos[i][j].equals(codigo))
                {
                    return notas[i][j];
                }
            }
        }
        return null;
    }
    //guardo la nota de una materia en un semestre, y devuelvo la nota que habia antes o null
    public Double registrar(int semestre, String codigo, double nota)
    {
        int fila = semestre - 1;
        //si la materia ya tiene nota en esa parte del semestre, la reemplazo
        for(int j = 0; j < codigos[fila].length; j++)
        {
            if(codigos[fila][j] != null && codigos[fila][j].equals(codigo))
            {
                double anterior = notas[fila][j];
                notas[fila][j] = nota;
                return anterior;
            }
        }
        //si no esta, la pongo en la primera casilla libre
        for(int j = 0; j < codigos[fila].length; j++)
        {
            if(codigos[fila][j] == null)
            {
                codigos[fila][j] = codigo;
                notas[fila][j] = nota;
                return null;
            }
        }
        return null;
    }
    //saber si ya no caben mas materias en ese semestre
    public boolean semestreLleno(int semestre)
    {
        return codigos[semestre - 1][codigos[semestre - 1].length - 1] != null;
    }
    //sacar el promedio de un semestre y 0 si no tiene notas
    public double promedioSemestre(int semestre)
    {
        double suma = 0;
        int cuenta = 0;
        for(int j = 0; j < notas[semestre - 1].length; j++)
        {
            if(notas[semestre - 1][j] != null)
            {
                suma = suma + notas[semestre - 1][j];
                cuenta++;
            }
        }
        if(cuenta == 0)
        {
            return 0;
        } 
        return suma / cuenta;
    }
    //promedio de todas las notas
    public double promedioAcumulado()
    {
        double suma = 0;
        int cuenta = 0;
        for(int i = 0; i < notas.length; i++)
        {
            for(int j = 0; j < notas[i].length; j++)
            {
                if(notas[i][j] != null)
                {
                    suma = suma + notas[i][j];
                    cuenta++;
                }
            }
        }
        if(cuenta == 0)
        {
            return 0;
        }
        return suma / cuenta;
    }
    //si la materia esta con nota menor a 3.0
    public LinkedList<String> materiasReprobadas()
    {
        LinkedList<String> reprobadas = new LinkedList<>();
        for(int i = 0; i < notas.length; i++)
        {
            for(int j = 0; j < notas[i].length; j++)
            {
                if(notas[i][j] != null && notas[i][j] < 3.0)
                {
                    reprobadas.addLast(codigos[i][j]);
                }
            }
        }
        return reprobadas;
    }
    //recorro la matriz desde el reporte
    public String getCodigo(int semestre, int j)
    {
        return codigos[semestre - 1][j];
    }
    public Double getNota(int semestre, int j)
    {
        return notas[semestre - 1][j];
    }
}
