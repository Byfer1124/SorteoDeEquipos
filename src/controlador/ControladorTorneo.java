package controlador;

import modelo.*;
import java.io.IOException;
import java.util.List;

public class ControladorTorneo {
    private Torneo torneo;
    private final String ARCHIVO_DATOS = "torneo.ser";

    public ControladorTorneo() {
        this.torneo = new Torneo();
        inicializarEquiposPorDefecto();
    }

    // Inicializa los 16 equipos obligatorios para la liga profesional
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

    public void ejecutarSorteo(String etapa) {
        try {
            torneo.realizarSorteo(etapa);
        } catch (NumeroEquiposInvalidoException | SorteoInvalidoException e) {
            System.out.println("❌ Error en el sorteo: " + e.getMessage());
        }
    }

    public List<Partido> getPartidos() {
        return torneo.getPartidosRondaActual();
    }

    public void guardarProgreso() {
        try {
            Torneo.guardarTorneo(torneo, ARCHIVO_DATOS);
            System.out.println("💾 ¡Torneo guardado exitosamente en " + ARCHIVO_DATOS + "!");
        } catch (IOException e) {
            System.out.println("❌ Error al guardar el archivo: " + e.getMessage());
        }
    }

    public void cargarProgreso() {
        try {
            torneo = Torneo.cargarTorneo(ARCHIVO_DATOS);
            System.out.println("📂 ¡Torneo cargado exitosamente desde " + ARCHIVO_DATOS + "!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("❌ Error al cargar el archivo guardado: " + e.getMessage());
        }
    }

    public List<Equipo> getEquipos() {
        return torneo.getEquipos();
    }
}
