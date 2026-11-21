package app;
import excepciones.EstudianteNoEncontradoException;
import modelo.Estudiante;
import modelo.Facultad;
import modelo.Materia;
import modelo.Profesor;
import servicio.GestorEstudiantes;
import servicio.GestorMaterias;
import util.LectorConsola;
import util.Validador;
/**
 * Clase principal del Sistema de Gestion Academica.
 * Muestra el menu con las 22 opciones y, segun lo que elija el usuario,
 * llama al metodo que se encarga de esa opcion.
 * Las opciones que dicen "en construccion" se van completando modulo por modulo.
 */
public class Main
{
    static LectorConsola lector = new LectorConsola();
    static GestorEstudiantes gestorEstudiantes = new GestorEstudiantes();
    static GestorMaterias gestorMaterias = new GestorMaterias(gestorEstudiantes);
    public static void main(String[] args)
    {
        int opcion;
        //menu principal
        do
        {
            mostrarMenu();
            opcion = lector.leerEntero("Seleccione una opcion: ", 1, 22);
            System.out.println();
            switch(opcion)
            {
                //gestion de estudiantes
                case 1:
                    registrarEstudiante();
                    break;
                case 2:
                    buscarEstudiante();
                    break;
                case 3:
                    listarEstudiantes();
                    break;
                case 4:
                    listarEstudiantes();
                    eliminarEstudiante();
                    break;
                //gestion de materias
                case 5:
                    crearMateria();
                    break;
                case 6:
                    agregarPrerrequisito();
                    break;
                case 7:
                case 8:
                case 9:
                case 10:
                //gestion de horarios
                case 11:
                case 12:
                case 13:
                //rutas entre edificios
                case 14:
                case 15:
                //reportes academicos
                case 16:
                case 17:
                case 18:
                //deshacer y rehacer
                case 19:
                case 20:
                //procesamiento por lotes
                case 21:
                    System.out.println("Opcion en construccion.");
                    break;
                case 22:
                    System.out.println("Programa finalizado.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        if(opcion != 22)
        {
            lector.pausar();
        }
        }while(opcion != 22);
        lector.cerrar();
    }
    //mostrar todas las opciones del menu
    private static void mostrarMenu()
    {
        System.out.println("\n============================================================");
        System.out.println("        PLANIFICACION ACADEMICA - SISTEMA UNIVERSITARIO");
        System.out.println("============================================================");
        System.out.println("\n=== GESTION DE ESTUDIANTES ===");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Buscar estudiante por ID");
        System.out.println("3. Listar todos los estudiantes");
        System.out.println("4. Eliminar estudiante");
        System.out.println("\n=== GESTION DE MATERIAS ===");
        System.out.println("5. Crear materia");
        System.out.println("6. Agregar pre-requisito");
        System.out.println("7. Mostrar pre-requisitos");
        System.out.println("8. Inscribir estudiante");
        System.out.println("9. Cancelar inscripcion");
        System.out.println("10. Mostrar cola de espera");
        System.out.println("\n=== GESTION DE HORARIOS ===");
        System.out.println("11. Reservar horario en aula");
        System.out.println("12. Liberar horario");
        System.out.println("13. Consultar disponibilidad");
        System.out.println("\n=== RUTAS ENTRE EDIFICIOS ===");
        System.out.println("14. Agregar conexion entre edificios");
        System.out.println("15. Calcular ruta mas corta");
        System.out.println("\n=== REPORTES ACADEMICOS ===");
        System.out.println("16. Registrar nota");
        System.out.println("17. Ver reporte academico");
        System.out.println("18. Navegador de reportes (atras/adelante)");
        System.out.println("\n=== SISTEMA DESHACER/REHACER ===");
        System.out.println("19. Deshacer ultima operacion");
        System.out.println("20. Rehacer ultima operacion");
        System.out.println("\n=== PROCESAMIENTO POR LOTES ===");
        System.out.println("21. Procesar archivo CSV");
        System.out.println("\n=== SALIR ===");
        System.out.println("22. Salir\n");
    }
    //metodo para registrar el estudiante
    private static void registrarEstudiante()
    {
        System.out.println("Ingrese el id");
        String id = lector.leerTexto("Id: ");
        String nombre = lector.leerTexto("Nombre: ");
        String email = lector.leerTexto("Email: ");
        while(!Validador.esEmailValido(email))
        {
            System.out.println("Email no valido, ejemplo: ana@uni.edu");
            email = lector.leerTexto("Email: ");
        }
        int semestre = lector.leerEntero("Semestre (1-10): ", 1, 10);
        Estudiante e = new Estudiante(id, nombre, email, semestre);
        if(gestorEstudiantes.registrar(e))
        {
            System.out.println("Estudiante registrado exitosamente");
        }else
        {
            System.out.println("Ya existe un estudiante con el Id " + id + ".");
        }
    }
    //listar a los estudiantes
    private static void listarEstudiantes()
    {
        Estudiante[] lista = gestorEstudiantes.listar();
        if(lista.length == 0)
        {
            System.out.println("No hay estudiantes registrados");
        }else
        {
            for(int i = 0; i < lista.length; i++)
            {
                System.out.println(lista[i].mostrarInformacion());
            }
        }
    }
    //buscar estudiante por id
    private static void  buscarEstudiante()
    {
        String id = lector.leerTexto("Id: ");
        try
        {
            Estudiante e = gestorEstudiantes.buscar(id);
            System.out.println(e.mostrarInformacion());
        }catch(EstudianteNoEncontradoException ex){
            System.out.println("Error: EstudianteNoEncontradoException " + ex.getMessage());
        }
    }
    //eliminar estudiante
    private static void eliminarEstudiante()
    {
        String id = lector.leerTexto("Id: ");
        try
        {
            Estudiante e = gestorEstudiantes.eliminar(id);
            System.out.println("Estudiante eliminado exitosamente");
        }catch(EstudianteNoEncontradoException ex){
            System.out.println("Error: EstudianteNoEncontradoException " + ex.getMessage());
        }
    }
    /*
    Las materias
    */
   //crear una materia nueva
    private static void crearMateria()
    {
        String codigo = lector.leerTexto("Codigo: ").toUpperCase();
        String nombre = lector.leerTexto("Nombre: ");
        int cupos = lector.leerEntero("Cupos maximos (1-50): ", 1, 50);
        int creditos = lector.leerEntero("Creditos (1-5): ", 1, 5);
        //elegimos la facultad
        Facultad[] facultades = gestorMaterias.getFacultades();
        for(int i = 0; i < facultades.length; i++)
        {
            System.out.println((i + 1) + " " + facultades[i].getNombre());
        }
        int f = lector.leerEntero("Facultad: ", 1, 5);
        //los datos del profesor
        String idProfesor = lector.leerTexto("Id del profesor: ");
        String nombreProfesor = lector.leerTexto("Nombre del profesor: ");
        String emailProfesor = lector.leerTexto("Email del profesor: ");
        while(!Validador.esEmailValido(emailProfesor))
        {
            System.out.println("Email no valido, ejemplo: juanelmecanico@uni.edu");
            emailProfesor = lector.leerTexto("Email del profesor: ");
        }
        Profesor p = new Profesor(idProfesor, nombreProfesor, emailProfesor, facultades[f - 1].getNombre(), 0);// (f - 1) se resta 1 porque el usuario ve las facultades del 1 al 5
        Materia m = new Materia(codigo, nombre, cupos, creditos, facultades[f - 1], p);
        if(gestorMaterias.crearMateria(m))
        {
            System.out.println("Materia creada exitosamente");
        }else
        {
            System.out.println("Ya existe una materia con el codigo " + codigo);
        }
    }
    //agregar un prerrequisito
    private static void agregarPrerrequisito()
    {
        String codigo = lector.leerTexto("Codigo de la materia: ").toUpperCase();
        String codigoPre = lector.leerTexto("Codigo del prerrequisito: ").toUpperCase();
        System.out.println(gestorMaterias.agregarPrerrequisito(codigo, codigoPre));
    }
}
