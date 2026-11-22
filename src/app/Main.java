package app;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import estructuras.HorarioAula;
import excepciones.ColaDeEsperaVaciaException;
import excepciones.ArchivoInvalidoException;
import excepciones.CupoLlenoException;
import excepciones.EstudianteNoEncontradoException;
import excepciones.HorarioConflictivoException;
import excepciones.PilaDeshacerVaciaException;
import excepciones.PreRequisitoNoAprobadoException;
import modelo.Aula;
import modelo.Edificio;
import modelo.Evaluacion;
import modelo.ExamenFinal;
import modelo.Parcial;
import modelo.Quiz;
import modelo.Estudiante;
import modelo.Facultad;
import modelo.Materia;
import modelo.Profesor;
import servicio.GestorEstudiantes;
import servicio.GestorHorarios;
import servicio.GestorMaterias;
import servicio.GestorOperaciones;
import servicio.GestorReportes;
import servicio.GestorRutas;
import servicio.OpCambiarHorario;
import servicio.OpCancelar;
import servicio.OpEliminarEstudiante;
import servicio.OpInscribir;
import servicio.OpRegistrarNota;
import servicio.ProcesadorLotes;
import util.LectorConsola;
import util.Validador;
/*
clase principal: muestra el menu con las 22 opciones y llama al metodo de cada opcion
*/
public class Main
{
    static LectorConsola lector = new LectorConsola();
    static GestorEstudiantes gestorEstudiantes = new GestorEstudiantes();
    static GestorMaterias gestorMaterias = new GestorMaterias(gestorEstudiantes);
    static GestorHorarios gestorHorarios = new GestorHorarios(gestorMaterias);
    static GestorRutas gestorRutas = new GestorRutas();
    static GestorReportes gestorReportes = new GestorReportes(gestorEstudiantes, gestorMaterias);
    static GestorOperaciones gestorOperaciones = new GestorOperaciones();
    static ProcesadorLotes procesadorLotes = new ProcesadorLotes(gestorMaterias);
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
                    mostrarPrerrequisitos();
                    break;
                case 8:
                    inscribirEstudiante();
                    break;
                case 9:
                    cancelarInscripcion();
                    break;
                case 10:
                    mostrarColaEspera();
                    break;
                //gestion de horarios
                case 11:
                    reservarHorario();
                    break;
                case 12:
                    liberarHorario();
                    break;
                case 13:
                    consultarDisponibilidad();
                    break;
                //rutas entre edificios
                case 14:
                    agregarConexion();
                    break;
                case 15:
                    calcularRuta();
                    break;
                //reportes academicos
                case 16:
                    registrarNota();
                    break;
                case 17:
                    verReporte();
                    break;
                case 18:
                    navegarReportes();
                    break;
                //deshacer y rehacer
                case 19:
                    deshacer();
                    break;
                case 20:
                    rehacer();
                    break;
                //procesamiento por lotes
                case 21:
                    procesarCSV();
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
            System.out.println("Error: EstudianteNoEncontradoException - " + ex.getMessage());
        }
    }
    //eliminar estudiante
    private static void eliminarEstudiante()
    {
        String id = lector.leerTexto("Id: ");
        try
        {
            Estudiante e = gestorEstudiantes.eliminar(id);
            //tambien lo saco de las materias y colas donde estaba
            LinkedList<String> materias = gestorMaterias.retirarEstudiante(id);
            gestorOperaciones.registrar(new OpEliminarEstudiante(gestorEstudiantes, gestorMaterias, e, materias));
            System.out.println("Estudiante eliminado exitosamente");
        }catch(EstudianteNoEncontradoException ex){
            System.out.println("Error: EstudianteNoEncontradoException - " + ex.getMessage());
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
    //mostror prerrequisitos
    private static void mostrarPrerrequisitos()
    {
        String codigo = lector.leerTexto("Codigo de la materia: ").toUpperCase();
        Materia m = gestorMaterias.buscarMateria(codigo);
        if(m == null)
        {
            System.out.println("No existe una materia con el codigo: " + codigo + " ");
            return;
        }
        if(m.getPrerrequisitos().isEmpty())
        {
            System.out.println(m.getNombre() + " no tiene prerrequisitos");
            return;
        }
        System.out.println("Prerrequisitos de " + m.getNombre() + ":");
        Iterator<String> it = m.getPrerrequisitos().iterator();// Iterator: Es como un dedo que va señalando la lista de un nodo en uno.
        while(it.hasNext())//entonces pregunta al iterador si queda otro nodo
        {
            String codigoPre = it.next();
            Materia pre = gestorMaterias.buscarMateria(codigoPre);
            System.out.println(" " + codigoPre + " " + pre.getNombre());
        }
    }
    //inscribir un estudiante a una materia
    private static void inscribirEstudiante()
    {
        String id = lector.leerTexto("Id del estudiante: ");
        String codigo = lector.leerTexto("Codigo de la materia: ").toUpperCase();
        try
        {
            String resultado = gestorMaterias.inscribir(id, codigo);
            System.out.println(resultado);
            if(resultado.startsWith("Inscrito"))
            {
                gestorOperaciones.registrar(new OpInscribir(gestorMaterias, id, codigo));
            }
        }catch(EstudianteNoEncontradoException ex)
        {
            System.out.println("Error: EstudianteNoEncontradoException - " + ex.getMessage());
        }catch(PreRequisitoNoAprobadoException ex)
        {
            System.out.println("Error: PreRequisitoNoAprobadoException - " + ex.getMessage());
        }catch(CupoLlenoException ex)
        {
            System.out.println("Error: CupoLlenoException - " + ex.getMessage());
        }
    }
    //cancelar la inscripcion de un estudiante
    private static void cancelarInscripcion()
    {
        String id = lector.leerTexto("Id del estudiante: ");
        String codigo = lector.leerTexto("Codigo de la materia: ").toUpperCase();
        try
        {
            String resultado = gestorMaterias.cancelar(id, codigo);
            System.out.println(resultado);
            if(resultado.startsWith("Inscripcion cancelada"))
            {
                gestorOperaciones.registrar(new OpCancelar(gestorMaterias, id, codigo));
            }
        }catch(EstudianteNoEncontradoException ex)
        {
            System.out.println("Error: EstudianteNoEncontradoException - " + ex.getMessage());
        }
    }
    //mostrar la cola de espera de una materia
    private static void mostrarColaEspera()
    {
        String codigo = lector.leerTexto("Codigo de la materia: ").toUpperCase();
        try
        {
            Queue<String> cola = gestorMaterias.verColaEspera(codigo);
            if(cola == null)
            {
                System.out.println("No existe una materia con el codigo " + codigo + ".");
                return;
            }
            System.out.println("Cola de espera de " + codigo + ":");
            int posicion = 1;
            for(String id : cola)
            {
                System.out.println(posicion + ". " + id);
                posicion++;
            }
        }catch(ColaDeEsperaVaciaException ex)
        {
            System.out.println("Error: ColaDeEsperaVaciaException - " + ex.getMessage());
        }
    }
    /*
    Los horarios
    */
    //mostrar aulas ordenadas y pedirlas
    private static String pedirAula()
    {
        System.out.println("Aulas: ");
        for(Aula a : gestorHorarios.getAulas().values())
        {
            System.out.println("- " + a.mostrarInformacion());
        }
        return lector.leerTexto("Aula: ").toUpperCase();
    }
    //mostrar los dias y pedirlos
    private static int pedirDia()
    {
        for(int i = 0; i < 7; i++)
        {
            System.out.println(i + ". " + HorarioAula.DIAS[i]);
        }
        return lector.leerEntero("Dia (0-6): ", 0, 6);
    }
    //reservar un horario en un aula
    private static void reservarHorario()
    {
        String aula = pedirAula();
        int dia = pedirDia();
        int hora = lector.leerEntero("Hora de inicio (0-23): ", 0, 23);
        int duracion = lector.leerEntero("Duracion en horas: ", 1, 24 - hora);
        String codigo = lector.leerTexto("Codigo de la materia: ").toUpperCase();
        //mostrar como esta cada hora del bloque
        Aula a = gestorHorarios.buscarAula(aula);
        if(a != null)
        {
            for(int h = hora; h < hora + duracion; h++)
            {
                if(a.getHorario().consultarDisponibilidad(dia, h))
                {
                    System.out.println(HorarioAula.DIAS[dia] + " " + h + ":00 -> LIBRE");
                }else
                {
                    System.out.println(HorarioAula.DIAS[dia] + " " + h + ":00 -> OCUPADO");
                }
            }
        }
        try
        {
            String resultado = gestorHorarios.reservar(aula, dia, hora, duracion, codigo);
            System.out.println(resultado);
            if(resultado.startsWith("Reserva exitosa"))
            {
                gestorOperaciones.registrar(new OpCambiarHorario(gestorHorarios, aula, dia, hora, duracion, codigo));
            }
        }catch(HorarioConflictivoException ex)
        {
            System.out.println("Error: HorarioConflictivoException - " + ex.getMessage());
        }
    }
    //liberar un horario
    private static void liberarHorario()
    {
        String aula = pedirAula();
        int dia = pedirDia();
        int hora = lector.leerEntero("Hora de inicio (0-23): ", 0, 23);
        int duracion = lector.leerEntero("Duracion en horas: ", 1, 24 - hora);
        System.out.println(gestorHorarios.liberar(aula, dia, hora, duracion));
    }
    //consultar si una hora esta libre
    private static void consultarDisponibilidad()
    {
        String aula = pedirAula();
        int dia = pedirDia();
        int hora = lector.leerEntero("Hora (0-23): ", 0, 23);
        Aula a = gestorHorarios.buscarAula(aula);
        if(a == null)
        {
            System.out.println("No existe el aula " + aula + ".");
            return;
        }
        if(a.getHorario().consultarDisponibilidad(dia, hora))
        {
            System.out.println(aula + " " + HorarioAula.DIAS[dia] + " " + hora + ":00 -> LIBRE");
        }else
        {
            System.out.println(aula + " " + HorarioAula.DIAS[dia] + " " + hora + ":00 -> OCUPADO por " + a.getHorario().getMateria(dia, hora));
        }
    }
    /*
    Edificios
    */
    //mostrar los edificios y pedir uno
    private static int pedirEdificio(String mensaje)
    {
        Edificio[] edificios = gestorRutas.getEdificios();
        for(int i = 0; i < edificios.length; i++)
        {
            System.out.println(i + ". " + edificios[i].getNombre());
        }
        return lector.leerEntero(mensaje, 0, edificios.length - 1);
    }
    //conectar dos edificios
    private static void agregarConexion()
    {
        int a = pedirEdificio("Edificio de origen: ");
        int b = pedirEdificio("Edificio de destino: ");
        int metros = lector.leerEntero("Distancia en metros (1-5000): ", 1, 5000);
        System.out.println(gestorRutas.agregarConexion(a, b, metros));
    }
    //calcular la ruta mas corta
    private static void calcularRuta()
    {
        int origen = pedirEdificio("Edificio de origen: ");
        int destino = pedirEdificio("Edificio de destino: ");
        System.out.println(gestorRutas.calcularRuta(origen, destino));
    }
    //registrar las tres notas de una materia
    private static void registrarNota()
    {
        String id = lector.leerTexto("Id del estudiante: ");
        int semestre = lector.leerEntero("Semestre (1-10): ", 1, 10);
        String codigo = lector.leerTexto("Codigo de la materia: ").toUpperCase();
        Evaluacion[] evaluaciones = new Evaluacion[3];
        evaluaciones[0] = new Quiz(lector.leerDecimal("Nota del quiz (20%): ", 0.0, 5.0));
        evaluaciones[1] = new Parcial(lector.leerDecimal("Nota del parcial (30%): ", 0.0, 5.0));
        evaluaciones[2] = new ExamenFinal(lector.leerDecimal("Nota del examen final (50%): ", 0.0, 5.0));
        try
        {
            Estudiante e = gestorEstudiantes.buscar(id);
            //guardo la nota que habia antes para poder deshacer
            Double anterior = e.getNotas().getNota(semestre, codigo);
            String resultado = gestorReportes.registrarNota(id, semestre, codigo, evaluaciones);
            System.out.println(resultado);
            if(resultado.startsWith("Nota registrada"))
            {
                Double nueva = e.getNotas().getNota(semestre, codigo);
                gestorOperaciones.registrar(new OpRegistrarNota(e, semestre, codigo, anterior, nueva));
            }
        }catch(EstudianteNoEncontradoException ex)
        {
            System.out.println("Error: EstudianteNoEncontradoException - " + ex.getMessage());
        }
    }
    //ver el reporte academico de un estudiante
    private static void verReporte()
    {
        String id = lector.leerTexto("Id del estudiante: ");
        try
        {
            System.out.println(gestorReportes.generarReporte(id));
        }catch(EstudianteNoEncontradoException ex)
        {
            System.out.println("Error: EstudianteNoEncontradoException - " + ex.getMessage());
        }
    }
    //deshacer la ultima operacion
    private static void deshacer()
    {
        try
        {
            System.out.println(gestorOperaciones.deshacer());
        }catch(PilaDeshacerVaciaException ex)
        {
            System.out.println("Error: PilaDeshacerVaciaException - " + ex.getMessage());
        }catch(Exception ex)
        {
            System.out.println("Error: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
        }
    }
    //rehacer la ultima operacion que se deshizo
    private static void rehacer()
    {
        try
        {
            System.out.println(gestorOperaciones.rehacer());
        }catch(PilaDeshacerVaciaException ex)
        {
            System.out.println("Error: PilaDeshacerVaciaException - " + ex.getMessage());
        }catch(Exception ex)
        {
            System.out.println("Error: " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
        }
    }
    //procesar un archivo CSV con solicitudes de inscripcion
    private static void procesarCSV()
    {
        String ruta = lector.leerTexto("Ruta del archivo (ej: solicitudes.csv): ");
        try
        {
            int cantidad = procesadorLotes.cargar(ruta);
            System.out.println("Se cargaron " + cantidad + " solicitudes en la cola\n");
            System.out.println(procesadorLotes.procesar());
        }catch(ArchivoInvalidoException ex)
        {
            System.out.println("Error: ArchivoInvalidoException - " + ex.getMessage());
        }
    }
    //moverse entre los reportes que ya se vieron
    private static void navegarReportes()
    {
        int opcion;
        do
        {
            System.out.println("\n1. Atras  2. Adelante  3. Volver al menu");
            opcion = lector.leerEntero("Seleccione: ", 1, 3);
            if(opcion == 1)
            {
                System.out.println(gestorReportes.atras());
            }else if(opcion == 2)
            {
                System.out.println(gestorReportes.adelante());
            }
        }while(opcion != 3);
    }
}
