package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Torneo implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Equipo> equipos;
    private List<Partido> historialPartidos; // Historial de todas las fases

    public Torneo() {
        this.equipos = new ArrayList<>();
        this.historialPartidos = new ArrayList<>();
    }

    public void agregarEquipo(Equipo e) {
        this.equipos.add(e);
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public List<Partido> getHistorialPartidos() {
        return historialPartidos;
    }

    public void agregarPartidosRonda(List<Partido> partidos) {
        this.historialPartidos.addAll(partidos);
    }

    public void validarNumeroEquipos(int n) throws NumeroEquiposInvalidoException {
        if (n != 2 && n != 4 && n != 8 && n != 16) {
            throw new NumeroEquiposInvalidoException("El número de equipos (" + n + ") no es válido (debe ser potencia de 2: 16, 8, 4, 2).");
        }
    }

    private void emparejarRecursivo(List<Equipo> listaEquipos, String etapa, List<Partido> resultados) throws SorteoInvalidoException {
        if (listaEquipos.isEmpty()) {
            return;
        }
        if (listaEquipos.size() < 2) {
            throw new SorteoInvalidoException("Error!: Quedó un equipo sin pareja en la etapa " + etapa);
        }

        Equipo local = listaEquipos.remove(0);
        Equipo visita = listaEquipos.remove(0);

        resultados.add(new Partido(local, visita, etapa));
        emparejarRecursivo(listaEquipos, etapa, resultados);
    }

    public List<Partido> realizarSorteoEtapa(String etapa, List<Equipo> equiposParticipantes) throws SorteoInvalidoException, NumeroEquiposInvalidoException {
        validarNumeroEquipos(equiposParticipantes.size());

        List<Partido> partidosEtapa = new ArrayList<>();
        List<Equipo> copia = new ArrayList<>(equiposParticipantes);
        Collections.shuffle(copia);

        emparejarRecursivo(copia, etapa, partidosEtapa);
        
        // Guardar automáticamente en el historial general del torneo
        agregarPartidosRonda(partidosEtapa);
        
        return partidosEtapa;
    }

    public static void guardarTorneo(Torneo torneo, String rutaArchivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(torneo);
        }
    }

    public static Torneo cargarTorneo(String rutaArchivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (Torneo) ois.readObject();
        }
    }
}
