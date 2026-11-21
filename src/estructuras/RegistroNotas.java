package estructuras;
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
}
