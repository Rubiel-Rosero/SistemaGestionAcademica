package servicio;

import java.util.Stack;

import excepciones.PilaDeshacerVaciaException;
/*
guarda las operaciones en dos pilas para deshacer y rehacer
*/
public class GestorOperaciones
{
    private Stack<Operacion> pilaDeshacer;
    private Stack<Operacion> pilaRehacer;
    public GestorOperaciones()
    {
        pilaDeshacer = new Stack<>();
        pilaRehacer = new Stack<>();
    }
    //guardo una operacion que salio bien
    public void registrar(Operacion op)
    {
        pilaDeshacer.push(op);
        //si hago algo nuevo ya no se puede rehacer lo anterior
        pilaRehacer.clear();
    }
    //deshago la ultima operacion
    public String deshacer() throws Exception
    {
        if(pilaDeshacer.isEmpty())
        {
            throw new PilaDeshacerVaciaException("No hay operaciones para deshacer");
        }
        Operacion op = pilaDeshacer.pop();
        String resultado = op.deshacer();
        pilaRehacer.push(op);
        return "Deshecho: " + op.getDescripcion() + "\n" + resultado;
    }
    //rehago la ultima operacion que se deshizo
    public String rehacer() throws Exception
    {
        if(pilaRehacer.isEmpty())
        {
            throw new PilaDeshacerVaciaException("No hay operaciones para rehacer");
        }
        Operacion op = pilaRehacer.pop();
        String resultado = op.rehacer();
        pilaDeshacer.push(op);
        return "Rehecho: " + op.getDescripcion() + "\n" + resultado;
    }
}
