package vista;

import controlador.ControladorTorneo;
import modelo.Partido;
import modelo.Equipo;

import java.util.Scanner;

public class Main{
    /*Método principal que ejecuta la interfaz por consola y gestiona el menú interactivo del torneo */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ControladorTorneo controlador = new ControladorTorneo();
        int opcion = 0;

        /*Bucle do-while para mantener el menú activo hasta que el usuario decida salir */
        do {
            System.out.println("\n===== SORTEO DE EQUIPOS - LIGA PROFESIONAL =====");
            System.out.println("1. Ver equipos inscritos (16 equipos)");
            System.out.println("2. Realizar sorteo (Octavos de Final)");
            System.out.println("3. Guardar estado del torneo (Serialización)");
            System.out.println("4. Cargar estado del torneo (Deserialización)");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            /*Valida que la entrada ingresada por consola sea un número entero */
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                /*Estructura de control para dirigir la acción según la opción seleccionada */
                switch (opcion) {
                    case 1:
                        System.out.println("\n--- Equipos Participantes ---");
                        for (Equipo e : controlador.getEquipos()) {
                            System.out.println("- " + e.getNombre());
                        }
                        break;
                    case 2:
                        System.out.println("\n--- Sorteo de Octavos de Final (Recursivo) ---");
                        controlador.simularTorneoCompleto();
                        break;
                    case 3:
                        controlador.guardarProgreso();
                        break;
                    case 4:
                        controlador.cargarProgreso();
                        System.out.println("Estado actual cargado. Verifique los equipos o partidos.");
                        break;
                    case 5:
                        System.out.println("¡Saliendo del sistema de sorteo!");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                }
            } else {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.next(); // Limpiar entrada incorrecta
            }
        } while (opcion != 5);

        scanner.close();
    }
}