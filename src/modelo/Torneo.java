package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Torneo implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Equipo> equipos;
    private List<Partido> historialPartidos; // Historial de todas las fases

    /*Constructor para inicializar las listas de equipos y el historial de partidos */
    public Torneo() {
        this.equipos = new ArrayList<>();
        this.historialPartidos = new ArrayList<>();
    }

    /*Agrega un nuevo equipo a la lista general del torneo */
    public void agregarEquipo(Equipo e) {
        this.equipos.add(e);
    }

    /*Retorna la lista de equipos inscritos */
    public List<Equipo> getEquipos() {
        return equipos;
    }

    /*Retorna el historial completo de partidos jugados en todas las fases */
    public List<Partido> getHistorialPartidos() {
        return historialPartidos;
    }

    /*Agrega una lista de partidos al historial general del torneo */
    public void agregarPartidosRonda(List<Partido> partidos) {
        this.historialPartidos.addAll(partidos);
    }

    /*Valida que el número de equipos sea una potencia válida de 2 (16, 8, 4, 2) */
    public void validarNumeroEquipos(int n) throws NumeroEquiposInvalidoException {
        if (n != 2 && n != 4 && n != 8 && n != 16) {
            throw new NumeroEquiposInvalidoException("El número de equipos (" + n + ") no es válido (debe ser potencia de 2: 16, 8, 4, 2).");
        }
    }

    /*Algoritmo recursivo para emparejar de dos en dos a los equipos de forma aleatoria por etapa */
    private void emparejarRecursivo(List<Equipo> listaEquipos, String etapa, List<Partido> resultados) throws SorteoInvalidoException {
        /** Caso base: si la lista está vacía, finaliza la recursividad */
        if (listaEquipos.isEmpty()) {
            return;
        }
        /*Valida que no quede un equipo desparejado */
        if (listaEquipos.size() < 2) {
            throw new SorteoInvalidoException("Error!: Quedó un equipo sin pareja en la etapa " + etapa);
        }

        /*Extrae dos equipos de la lista para conformar un enfrentamiento */
        Equipo local = listaEquipos.remove(0);
        Equipo visita = listaEquipos.remove(0);

        /*Añade el partido creado a los resultados y realiza la llamada recursiva */
        resultados.add(new Partido(local, visita, etapa));
        emparejarRecursivo(listaEquipos, etapa, resultados);
    }

    /*Valida, baraja y ejecuta el sorteo recursivo de los enfrentamientos para una etapa específica */
    public List<Partido> realizarSorteoEtapa(String etapa, List<Equipo> equiposParticipantes) throws SorteoInvalidoException, NumeroEquiposInvalidoException {
        validarNumeroEquipos(equiposParticipantes.size());

        List<Partido> partidosEtapa = new ArrayList<>();
        List<Equipo> copia = new ArrayList<>(equiposParticipantes);
        Collections.shuffle(copia);

        emparejarRecursivo(copia, etapa, partidosEtapa);
        
        /** Guardar automáticamente en el historial general del torneo */
        agregarPartidosRonda(partidosEtapa);
        
        return partidosEtapa;
    }

    /*Serializa y guarda el objeto Torneo completo en un archivo binario */
    public static void guardarTorneo(Torneo torneo, String rutaArchivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(torneo);
        }
    }

    /*Deserializa y carga el objeto Torneo desde un archivo binario */
    public static Torneo cargarTorneo(String rutaArchivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (Torneo) ois.readObject();
        }
    }
}