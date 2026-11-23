package util;
/*
metodos para revisar los datos que escribe el usuario
*/
public class Validador
{
    //revisar que el email este en el formato
    public static boolean esEmailValido(String email)
    {
        int arroba = email.indexOf("@");
        int punto = email.lastIndexOf(".");
        return arroba > 0 && punto > arroba + 1 && punto < email.length() - 1;
    }
}
