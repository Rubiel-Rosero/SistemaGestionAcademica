package util;
import java.util.Locale;
import java.util.Scanner;
/*
clase de apoyo para leer datos del teclado
*/
public class LectorConsola
{
    private Scanner sc;
    public LectorConsola()
    {
        sc = new Scanner(System.in);
        //para que los decimales se escriban con punto, ejemplo 4.5
        sc.useLocale(Locale.US);//c
    }
    //leer un texto que no puede quedar vacio
    public String leerTexto(String mensaje)
    {
        String texto = "";
        while(texto.isEmpty())
        {
            System.out.print(mensaje);
            texto = sc.nextLine();
            if(texto.isEmpty())
            {
                System.out.println("El valor no puede estar vacio.");
            }
        }
        return texto;
    }
    public void pausar()
    {
        System.out.print("\n Presione Enter para continuar");
        sc.nextLine();
    }
    //leer un numero entero entre min y max
    public int leerEntero(String mensaje, int min, int max)
    {
        int numero = min - 1;
        do
        {
            System.out.print(mensaje);
            //si escribe letras no las tomo
            if(sc.hasNextInt())
            {
                numero = sc.nextInt();
            }
            sc.nextLine();
            if(numero < min || numero > max)
            {
                System.out.println("Debe ser un numero entre " + min + " y " + max + ".");
            }
        }while(numero < min || numero > max);
        return numero;
    }
    //leer un numero decimal entre min y max
    public double leerDecimal(String mensaje, double min, double max)
    {
        double numero = min - 1;
        do
        {
            System.out.print(mensaje);
            if(sc.hasNextDouble())
            {
                numero = sc.nextDouble();
            }
            sc.nextLine();
            if(numero < min || numero > max)
            {
                System.out.println("Debe ser un numero entre " + min + " y " + max + ", por ejemplo 4.5");
            }
        }while(numero < min || numero > max);
        return numero;
    }
    //cerrar el scanner al salir del programa
    public void cerrar()
    {
        sc.close();
    }
}
