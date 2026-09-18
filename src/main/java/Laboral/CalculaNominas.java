package Laboral;

import Excepciones.DatosNoCorrectosException;

public class CalculaNominas {
    public static void main(String[] args) {
        try {
            Empleado e1 = new Empleado("James Cosling", "32000032G", 'M', 4, 7);
            Empleado e2 = new Empleado("Ada Lovelace", "32000031R", 'F');

            escribe(e1, e2);

            e2.incrAnyo();
            e1.setCategoria(9);

            escribe(e1, e2);

        } catch (DatosNoCorrectosException e) {
            System.out.println("Datos no correctos");
            System.exit(1);
        }
    }

    private static void escribe (Empleado e1, Empleado e2) {
        e1.imprime();
        System.out.println("Sueldo: " + Nomina.sueldo(e1) + "\n");



        e2.imprime();
        System.out.println("Sueldo: " + Nomina.sueldo(e2) + "\n");
    }
}
