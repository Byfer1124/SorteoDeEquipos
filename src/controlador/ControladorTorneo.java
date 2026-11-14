package controlador;

import modelo.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControladorTorneo {
    private Torneo torneo;
    private final String ARCHIVO_DATOS = "torneo.ser";
    private List<Partido> partidosRondaActual;

    public ControladorTorneo() {
        this.torneo = new Torneo();
        this.partidosRondaActual = new ArrayList<>();
        inicializarEquiposPorDefecto();
    }

    private void inicializarEquiposPorDefecto() {
        String[] nombresEquipos = {
            "Barcelona SC", "Emelec", "Liga de Quito", "Independiente del Valle",
            "Universidad Católica", "Deportivo Cuenca", "Macará", "Aucas",
            "Técnico Universitario", "Orense", "Delfín", "Mushuc Runa",
            "Libertad FC", "Imbabura SC", "Cumbayá FC", "El Nacional"
        };

        for (String nombre : nombresEquipos) {
            torneo.agregarEquipo(new Equipo(nombre));
        }
    }

    // Simula el sorteo completo hasta la final eligiendo ganadores aleatorios de manera automática para avanzar de fase
    public void simularTorneoCompleto() {
        try {
            System.out.println("\n===== INICIO DEL TORNEO HASTA LA FINAL =====");
            
            // 1. OCTAVOS DE FINAL (16 equipos)
            System.out.println("\n--- OCTAVOS DE FINAL ---");
            List<Partido> octavos = torneo.realizarSorteoEtapa("Octavos de Final", new ArrayList<>(torneo.getEquipos()));
            imprimirYMostrarPartidos(octavos);
            List<Equipo> ganadoresOctavos = simularGanadores(octavos);

            // 2. CUARTOS DE FINAL (8 equipos)
            System.out.println("\n--- CUARTOS DE FINAL ---");
            List<Partido> cuartos = torneo.realizarSorteoEtapa("Cuartos de Final", ganadoresOctavos);
            imprimirYMostrarPartidos(cuartos);
            List<Equipo> ganadoresCuartos = simularGanadores(cuartos);

            // 3. SEMIFINALES (4 equipos)
            System.out.println("\n--- SEMIFINALES ---");
            List<Partido> semis = torneo.realizarSorteoEtapa("Semifinales", ganadoresCuartos);
            imprimirYMostrarPartidos(semis);
            List<Equipo> ganadoresSemis = simularGanadores(semis);

            // 4. GRAN FINAL (2 equipos)
            System.out.println("\n--- GRAN FINAL ---");
            List<Partido> finalTorneo = torneo.realizarSorteoEtapa("Final", ganadoresSemis);
            imprimirYMostrarPartidos(finalTorneo);
            
            Equipo campeon = simularGanadores(finalTorneo).get(0);
            System.out.println("\n ¡EL CAMPEÓN DEL TORNEO ES: " + campeon.getNombre() + "!");

        } catch (Exception e) {
            System.out.println("Error durante el torneo: " + e.getMessage());
        }
    }

    private void imprimirYMostrarPartidos(List<Partido> partidos) {
        this.partidosRondaActual = partidos;
        for (Partido p : partidos) {
            System.out.println(p);
        }
    }

    // Simula de manera aleatoria quién gana cada partido para avanzar a la siguiente etapa
    private List<Equipo> simularGanadores(List<Partido> partidos) {
        List<Equipo> ganadores = new ArrayList<>();
        for (Partido p : partidos) {
            // Elige aleatoriamente entre local y visita como ganador
            Equipo ganador = Math.random() < 0.5 ? p.getEquipoLocal() : p.getEquipoVisita();
            ganadores.add(ganador);
        }
        System.out.println(" > Ganadores que avanzan: " + ganadores);
        return ganadores;
    }

    public void guardarProgreso() {
        try {
            Torneo.guardarTorneo(torneo, ARCHIVO_DATOS);
            System.out.println("¡Torneo guardado exitosamente!");
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    public void cargarProgreso() {
        try {
            torneo = Torneo.cargarTorneo(ARCHIVO_DATOS);
            System.out.println("¡Torneo cargado exitosamente desde " + ARCHIVO_DATOS + "!");
            System.out.println("Equipos recuperados en el torneo: " + torneo.getEquipos().size() + " equipos.");
            
            List<Partido> historial = torneo.getHistorialPartidos();
            if (historial != null && !historial.isEmpty()) {
                System.out.println("Historial de partidos jugados recuperados:");
                String etapaActual = "";
                for (Partido p : historial) {
                    if (!p.getEtapa().equals(etapaActual)) {
                        etapaActual = p.getEtapa();
                        System.out.println("\n   --- " + etapaActual + " ---");
                    }
                    System.out.println("   * " + p.getEquipoLocal() + " vs " + p.getEquipoVisita());
                }
            } else {
                System.out.println("⚠️ El torneo cargado aún no tiene partidos jugados en el historial.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar el archivo guardado: " + e.getMessage());
        }
    }

    public List<Equipo> getEquipos() {
        return torneo.getEquipos();
    }
}
