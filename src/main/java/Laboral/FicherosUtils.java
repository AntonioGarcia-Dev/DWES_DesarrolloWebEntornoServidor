package Laboral;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Excepciones.DatosNoCorrectosException;

public class FicherosUtils {
    // Método para leer empleados.txt y generar salarios.dat
    public static void procesarFicheros(String txtEntrada, String datSalida) {
        List<Empleado> empleados = leerEmpleados(txtEntrada);
        if (!empleados.isEmpty()) {
            escribirSalarios(empleados, datSalida);
            System.out.println("Archivo binario generado correctamente.");
        } else {
            System.out.println("No se han leído empleados del archivo.");
        }
    }

    // Lee el fichero txt y devuelve una lista de Empleados
    public static List<Empleado> leerEmpleados(String rutaTxt) {
        List<Empleado> lista = new ArrayList<>();
        // try-with-resources asegura que el BufferedReader se cierre automáticamente
        try (BufferedReader br = new BufferedReader(new FileReader(rutaTxt))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Dividimos la línea por el punto y coma
                String[] datos = linea.split(";");
                if (datos.length >= 3) {
                    try {
                        String dni = datos[0];
                        String nombre = datos[1];
                        char sexo = datos[2].charAt(0);

                        Empleado emp;
                        // Si tiene los 5 datos (dni, nombre, sexo, categoria, años)
                        if (datos.length == 5) {
                            int categoria = Integer.parseInt(datos[3]);
                            int anyos = Integer.parseInt(datos[4]);
                            emp = new Empleado(nombre, dni, sexo, categoria, anyos);
                        } else {
                            // Usa el constructor reducido (categoría 1, 0 años)
                            emp = new Empleado(nombre, dni, sexo);
                        }
                        lista.add(emp);
                    } catch (DatosNoCorrectosException | NumberFormatException e) {
                        System.err.println("Error procesando línea: " + linea + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo " + rutaTxt + ": " + e.getMessage());
        }
        return lista;
    }

    // Escribe el DNI y el sueldo en el archivo binario (sueldos.dat)
    private static void escribirSalarios(List<Empleado> empleados, String rutaDat) {
        // DataOutputStream es ideal para escribir tipos de datos primitivos en binario
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(rutaDat))) {
            for (Empleado emp : empleados) {
                dos.writeUTF(emp.dni); // Guarda el DNI (String)
                dos.writeInt(Nomina.sueldo(emp)); // Calcula y guarda el sueldo (int)
            }
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo binario: " + e.getMessage());
        }
    }

    // Escribe la lista de empleados de vuelta al archivo de texto
    public static void actualizarFicheroEmpleados(List<Empleado> empleados, String rutaTxt) {
        // BufferedWriter y FileWriter se encargan de escribir texto. Al no usar 'true' en FileWriter, sobrescribe el archivo.
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaTxt))) {
            for (Empleado emp : empleados) {
                // Reconstruimos la línea con el formato exacto separado por punto y coma
                String linea = emp.dni + ";" + emp.nombre + ";" + emp.sexo + ";" + emp.getCategoria() + ";" + emp.anyos;
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo de texto: " + e.getMessage());
        }
    }
}
