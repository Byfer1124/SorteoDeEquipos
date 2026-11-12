package modelo;

import java.io.Serializable;

//Representa a cada uno de los 16 equipos e implementa Serializable para permitir su guardado

public class Equipo implements Serializable{

	private static final long serialVersionUID = 1L;
    private String nombre;
    
    public Equipo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
