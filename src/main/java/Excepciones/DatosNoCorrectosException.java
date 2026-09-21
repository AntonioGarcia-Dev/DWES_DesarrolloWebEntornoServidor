package Excepciones;

/**
 * Se crea la clase para el control de Excepciones en paquete independiente.
 */

public class DatosNoCorrectosException extends Exception{
    public DatosNoCorrectosException(String mensaje) {
        super(mensaje);
    }
}
