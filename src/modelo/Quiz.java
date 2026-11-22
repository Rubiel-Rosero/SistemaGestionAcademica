package modelo;
/*
el quiz que vale 20%
*/
public class Quiz extends Evaluacion
{
    public Quiz(double nota)
    {
        super(nota);
    }
    @Override 
    public double calcularAporte()
    {
        return nota * 0.20;
    }
    @Override 
    public String getTipo()
    {
        return "Quiz (20%)";
    }
}
