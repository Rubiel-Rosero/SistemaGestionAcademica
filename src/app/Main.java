package app;

import util.LectorConsola;

/**
 * Clase principal del Sistema de Gestion Academica.
 * Muestra el menu con las 22 opciones y, segun lo que elija el usuario,
 * llama al metodo que se encarga de esa opcion.
 * Las opciones que dicen "en construccion" se van completando modulo por modulo.
 */
public class Main
{
    static LectorConsola lector = new LectorConsola();

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
                case 2:
                case 3:
                case 4:
                //gestion de materias
                case 5:
                case 6:
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
}
