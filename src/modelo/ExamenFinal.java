package modelo;
/*
el examen final que vale 50%
*/
public class ExamenFinal extends Evaluacion
{
    public ExamenFinal(double nota)
    {
        super(nota);
    }
    @Override 
    public double calcularAporte()
    {
        return nota * 0.50;
    }
    @Override 
    public String getTipo()
    {
        return "Examen Final (50%)";
    }
}
