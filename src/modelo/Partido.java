package modelo;

import java.io.Serializable;

//Representa el enfrentamiento entre dos equipos

public class Partido implements Serializable {

	private static final long serialVersionUID = 1L;
    private Equipo equipoLocal;
    private Equipo equipoVisita;
    private String etapa;
    
    public Partido(Equipo equipoLocal, Equipo equipoVisita, String etapa) {
        this.equipoLocal = equipoLocal;
        this.equipoVisita = equipoVisita;
        this.etapa = etapa;
    }
    
    public Equipo getEquipoLocal() { 
    	return equipoLocal; 
    }
    
    public Equipo getEquipoVisita() { 
    	return equipoVisita; 
    }
    
    public String getEtapa() { 
    	return etapa; 
    }

    @Override
    public String toString() {
        return "[" + etapa + "] " + equipoLocal.getNombre() + " vs " + equipoVisita.getNombre();
    }
}
