package modelo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Contiene la lista de equipos, la lógica del sorteo recursivo y los métodos de Serialización/Deserialización.

public class Torneo implements Serializable{

	private static final long serialVersionUID = 1L;
    private List<Equipo> equipos;
    private List<Partido> partidosRondaActual;
    
    public Torneo() {
        this.equipos = new ArrayList<>();
        this.partidosRondaActual = new ArrayList<>();
    }
    
    public void agregarEquipo(Equipo e) {
        this.equipos.add(e);
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public List<Partido> getPartidosRondaActual() {
        return partidosRondaActual;
    }
    
    // Validación de potencia de 2 (16, 8, 4, 2)
    public void validarNumeroEquipos(int n) throws NumeroEquiposInvalidoException {
        if (n != 2 && n != 4 && n != 8 && n != 16) {
            throw new NumeroEquiposInvalidoException("El número de equipos (" + n + ") no es válido para eliminación directa (debe ser potencia de 2: 16, 8, 4, 2).");
        }
    }
    
    // Algoritmo de sorteo recursivo
    public void sortearEtapaRecursivo(List<Equipo> listaEquipos, String etapa, List<Partido> resultados) throws SorteoInvalidoException, NumeroEquiposInvalidoException {
        validarNumeroEquipos(listaEquipos.size());

        if (listaEquipos.size() % 2 != 0) {
            throw new SorteoInvalidoException("Error crítico: La cantidad de equipos es impar, no se pueden emparejar.");
        }

        // Caso base de la recursividad: cuando quedan 2 equipos para la final o último emparejamiento
        if (listaEquipos.size() == 2) {
            resultados.add(new Partido(listaEquipos.get(0), listaEquipos.get(1), etapa));
            return;
        }

        // Lógica recursiva: barajar aleatoriamente para garantizar aleatoriedad
        List<Equipo> copia = new ArrayList<>(listaEquipos);
        Collections.shuffle(copia);

        Equipo local = copia.get(0);
        Equipo visita = copia.get(1);

        resultados.add(new Partido(local, visita, etapa));

        // Remover los equipos ya emparejados y llamar recursivamente con el resto
        copia.remove(0);
        copia.remove(0);

        sortearEtapaRecursivo(copia, etapa, resultados);
    }

    public void realizarSorteo(String etapa) throws SorteoInvalidoException, NumeroEquiposInvalidoException {
        partidosRondaActual.clear();
        sortearEtapaRecursivo(new ArrayList<>(equipos), etapa, partidosRondaActual);
    }

    // Serialización (Guardar estado)
    public static void guardarTorneo(Torneo torneo, String rutaArchivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(torneo);
        }
    }

    // Deserialización (Cargar estado)
    public static Torneo cargarTorneo(String rutaArchivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (Torneo) ois.readObject();
        }
    }
}
