package modelo;

/*Excepción personalizada para manejar errores o anomalías durante el sorteo */
public class SorteoInvalidoException extends Exception {

    /* Constructor que recibe el mensaje de error y lo pasa a la superclase */
    public SorteoInvalidoException(String mensaje) {
        super(mensaje);
    }
}