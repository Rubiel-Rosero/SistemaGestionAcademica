package util;

import java.util.Scanner;

/**
Clase de apoyo para leer datos del teclado sin que el programa se caiga.
*/
public class LectorConsola
{
    private Scanner sc;
    public LectorConsola()
    {
        sc = new Scanner(System.in);
    }
    //leer un texto que no puede quedar vacio
    public String leerTexto(String mensaje)
    {
        String texto = "";
        while(texto.isEmpty())
        {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();
            if(texto.isEmpty())
            {
                System.out.println("El valor no puede estar vacio.");
            }
        }
        return texto;
    }
    //leer un numero entero entre min y max
    public int leerEntero(String mensaje, int min, int max)
    {
        while(true)
        {
            String texto = leerTexto(mensaje);
            try
            {
                int numero = Integer.parseInt(texto);
                if(numero >= min && numero <= max)
                {
                    return numero;
                }
                System.out.println("Debe ser un numero entre " + min + " y " + max + ".");
            }
            catch(NumberFormatException e)
            {
                System.out.println("Escriba un numero entero.");
            }
        }
    }
    //leer un numero decimal entre min y max (acepta punto o coma)
    public double leerDecimal(String mensaje, double min, double max)
    {
        while(true)
        {
            String texto = leerTexto(mensaje).replace(',', '.');
            try
            {
                double numero = Double.parseDouble(texto);
                if(numero >= min && numero <= max)
                {
                    return numero;
                }
                System.out.println("Debe ser un numero entre " + min + " y " + max + ".");
            }
            catch(NumberFormatException e)
            {
                System.out.println("Escriba un numero, por ejemplo 4.5");
            }
        }
    }
    //cerrar el scanner al salir del programa
    public void cerrar()
    {
        sc.close();
    }
}
