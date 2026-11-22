package modelo;
/*
el parcial que vale 30%
*/
public class Parcial extends Evaluacion
{
    public Parcial(double nota)
    {
        super(nota);
    }
    @Override 
    public double calcularAporte()
    {
        return nota * 0.30;
    }
    @Override 
    public String getTipo()
    {
        return "Parcial (30%)";
    }
}
