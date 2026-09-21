package Laboral;
import Excepciones.DatosNoCorrectosException;

public class Empleado extends Persona{

    private int categoria;
    public int anyos;

    /**
     * @param nombre
     * @param dni
     * @param sexo
     * @param categoria
     * @param anyos
     * @throws DatosNoCorrectosException
     *
     * Se crean los constructores correspondientes, setter, getters, el incrementador de Años y el Imprime.
     */

    public Empleado(String nombre, String dni, char sexo, int categoria, int anyos) throws DatosNoCorrectosException{
        super(nombre, dni, sexo);
        if (categoria < 0 || categoria > 10 || anyos < 0) {
            throw new DatosNoCorrectosException("Datos no correctos: Categoría (1-10) o años trabajados (<0) inválidos.\"");
        }
        this.categoria = categoria;
        this.anyos = anyos;
    }

    public Empleado(String nombre, String dni, char sexo) throws DatosNoCorrectosException{
        super(nombre, dni, sexo);
        this.categoria = 1;
        this.anyos = 0;
    }

    public void setCategoria(int categoria) throws DatosNoCorrectosException{
        if (categoria < 1 || categoria > 10) {
            throw new DatosNoCorrectosException("Datos no correctos.");
        }
        this.categoria = categoria;
    }

    public int getCategoria() {
        return categoria;
    }

    public void incrAnyo() {
        this.anyos++;
    }

    public void imprime() {
        System.out.println("Nombre: " + this.nombre +
                "\nDni: " + this.dni +
                "\nSexo: " + this.sexo +
                "\nCategoria: " + this.categoria +
                "\nAños trabajados: " + this.anyos);
    }
}
