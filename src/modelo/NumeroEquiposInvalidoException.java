package modelo;

/*Excepción personalizada para validar que el número de equipos sea correcto */
public class NumeroEquiposInvalidoException extends Exception{

    /*Constructor que recibe el mensaje de error y lo pasa a la superclase */
    public NumeroEquiposInvalidoException(String mensaje) {
        super(mensaje);
    }
}