package modelo;
/*
profesor que hereda de Persona, cada materia tiene uno
*/
public class Profesor extends Persona
{
    private String departamento;
    private double salario;
    public Profesor(String id, String nombre, String email, String departamento, double salario)
    {
        super(id, nombre, email);
        this.departamento = departamento;
        this.salario = salario;
    }
    public String getDepartamento()
    {
        return departamento;
    }
    public double getSalario() {
        return salario;
    }
    @Override
    public String mostrarInformacion()
    {
        return "Profesor: " + getNombre() + "\nDepartamento: " + getDepartamento() + "\nSalario: " + getSalario();
    }
}
