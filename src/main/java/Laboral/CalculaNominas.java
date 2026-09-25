package Laboral;

import java.util.List;
import java.util.Scanner;

public class CalculaNominas {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n=== MENÚ DE GESTIÓN DE NÓMINAS ===");
            System.out.println("1. Mostrar todos los empleados");
            System.out.println("2. Mostrar salario por DNI");
            System.out.println("3. Modificar datos de un empleado");
            System.out.println("4. Recalcular y actualizar sueldo de un empleado");
            System.out.println("5. Recalculate y actualizar sueldos de TODOS los empleados");
            System.out.println("6. Realizar copia de seguridad (Backup)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1:
                        BaseDatosUtils.mostrarTodos();
                        break;
                    case 2:
                        System.out.print("Introduzca el DNI del empleado: ");
                        String dniConsulta = sc.nextLine();
                        BaseDatosUtils.mostrarSalario(dniConsulta);
                        break;
                    case 3:
                        modificarEmpleadoMenu(sc);
                        break;
                    case 4:
                        System.out.print("Introduzca el DNI del empleado a recalcular: ");
                        String dniRecalcular = sc.nextLine();
                        Empleado empRecalcular = BaseDatosUtils.obtenerEmpleado(dniRecalcular);
                        if (empRecalcular != null) {
                            BaseDatosUtils.guardarSueldoBD(empRecalcular);
                            System.out.println("Sueldo recalculado y actualizado.");
                        } else {
                            System.out.println("Empleado no encontrado.");
                        }
                        break;
                    case 5:
                        List<Empleado> todos = BaseDatosUtils.leerEmpleadosBD();
                        for (Empleado e : todos) {
                            BaseDatosUtils.guardarSueldoBD(e);
                        }
                        System.out.println("Sueldos de toda la plantilla recalculados y actualizados.");
                        break;
                    case 6:
                        BaseDatosUtils.realizarBackup();
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduzca un número válido.");
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        }
        sc.close();
    }

    /**
     * Submenú interactivo para modificar los datos de un empleado (Apartado 5.3)
     */
    private static void modificarEmpleadoMenu(Scanner sc) {
        System.out.print("Introduzca el DNI del empleado a modificar: ");
        String dni = sc.nextLine();

        Empleado emp = BaseDatosUtils.obtenerEmpleado(dni);

        if (emp != null) {
            try {
                System.out.println("Empleado actual: " + emp.nombre + " | Cat: " + emp.getCategoria() + " | Años: " + emp.anyos);

                System.out.print("Nueva Categoría (1-10) [Enter para mantener]: ");
                String catStr = sc.nextLine();
                if (!catStr.isEmpty()) {
                    emp.setCategoria(Integer.parseInt(catStr));
                }

                System.out.print("Años trabajados [Enter para mantener]: ");
                String anyosStr = sc.nextLine();
                if (!anyosStr.isEmpty()) {
                    emp.anyos = Integer.parseInt(anyosStr);
                }

                // Actualizamos en la BD
                BaseDatosUtils.actualizarEmpleadoBD(emp);
                // Actualización automática del sueldo al modificar variables clave
                BaseDatosUtils.guardarSueldoBD(emp);

                System.out.println("Empleado modificado correctamente.");

            } catch (Exception e) {
                System.out.println("Error en los datos introducidos: " + e.getMessage());
            }
        } else {
            System.out.println("No existe ningún empleado con ese DNI en la base de datos.");
        }
    }
}