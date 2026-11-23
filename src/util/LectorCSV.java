package util;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import excepciones.ArchivoInvalidoException;
/*
lee un archivo CSV y devuelve cada linea separada por comas
*/
public class LectorCSV
{
    //veo el archivo linea por linea y la primera seria el encabezado
    public static LinkedList<String[]> leer(String ruta)throws ArchivoInvalidoException
    {
        LinkedList<String[]> filas = new LinkedList<>();
        Scanner archivo;
        try
        {
            archivo = new Scanner(new File(ruta));
        }catch(FileNotFoundException ex)
        {
            throw new ArchivoInvalidoException("No se encontro el archivo " + ruta);
        }
        int numeroLinea = 0;
        while(archivo.hasNextLine())
        {
            String linea = archivo.nextLine();
            numeroLinea++;
            if(numeroLinea == 1 || linea.isEmpty())
            {
                continue;
            }
            String[] partes = linea.split(",");
            if(partes.length != 2)
            {
                archivo.close();
                throw new ArchivoInvalidoException("La linea " + numeroLinea + " no tiene el formato id estudiante ni codigo materia");
            }
            filas.addLast(partes);
        }
        archivo.close();
        return filas;
    }
}
