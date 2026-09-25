package Laboral;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDatosUtils {
    // Configuración de la conexión a MariaDB local
    private static final String URL = "jdbc:mariadb://localhost:3306/gestion_nominas";
    private static final String USER = "root";
    private static final String PASSWORD = "123456"; //

    private static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Lee la tabla Empleados y devuelve la lista (Apartado 2)
    public static List<Empleado> leerEmpleadosBD() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT nombre, dni, sexo, categoria, anyos FROM Empleados";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String dni = rs.getString("dni");
                char sexo = rs.getString("sexo").charAt(0);
                int categoria = rs.getInt("categoria");
                int anyos = rs.getInt("anyos");

                lista.add(new Empleado(nombre, dni, sexo, categoria, anyos));
            }
        } catch (Exception e) {
            System.err.println("Error leyendo la base de datos: " + e.getMessage());
        }
        return lista;
    }

    // Actualiza categoría y años de un empleado en la BD (Apartado 2.2)
    public static void actualizarEmpleadoBD(Empleado emp) {
        String sql = "UPDATE Empleados SET categoria = ?, anyos = ? WHERE dni = ?";
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, emp.getCategoria());
            pstmt.setInt(2, emp.anyos);
            pstmt.setString(3, emp.dni);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error actualizando empleado: " + e.getMessage());
        }
    }

    // Almacena el sueldo calculado en la tabla Nominas (Apartado 2.3)
    public static void guardarSueldoBD(Empleado emp) {
        String sql = "INSERT INTO Nominas (dni, sueldo) VALUES (?, ?) ON DUPLICATE KEY UPDATE sueldo = ?";
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int sueldo = Nomina.sueldo(emp);
            pstmt.setString(1, emp.dni);
            pstmt.setInt(2, sueldo);
            pstmt.setInt(3, sueldo); // Por si ya existe y solo hay que actualizarlo
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error guardando nómina: " + e.getMessage());
        }
    }

    // Apartado 3: Alta de un empleado individual
    public static void altaEmpleado(Empleado emp) {
        String sql = "INSERT INTO Empleados (dni, nombre, sexo, categoria, anyos) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emp.dni);
            pstmt.setString(2, emp.nombre);
            pstmt.setString(3, String.valueOf(emp.sexo));
            pstmt.setInt(4, emp.getCategoria());
            pstmt.setInt(5, emp.anyos);
            pstmt.executeUpdate();

            System.out.println("Alta completada: " + emp.nombre);

            // Automáticamente calcula y almacena el sueldo en la tabla Nominas
            guardarSueldoBD(emp);

        } catch (SQLException e) {
            System.err.println("Error al dar de alta a " + emp.dni + ": " + e.getMessage());
        }
    }

    // Apartado 3.1: Método sobrecargado para alta por lotes desde fichero
    public static void altaEmpleado(String rutaFichero) {
        System.out.println("--- Procesando alta masiva desde: " + rutaFichero + " ---");
        // Reutilizamos el método de leer ficheros txt que creamos en la Parte 1
        List<Empleado> nuevos = FicherosUtils.leerEmpleados(rutaFichero);

        for (Empleado emp : nuevos) {
            altaEmpleado(emp); // Llamamos al método individual por cada empleado
        }
    }

    /**
     * 5.1 Mostrar todos los empleados de la base de datos
     */
    public static void mostrarTodos() {
        List<Empleado> empleados = leerEmpleadosBD();
        for (Empleado emp : empleados) {
            emp.imprime(); // Muestra nombre y DNI
            System.out.println("Sexo: " + emp.sexo + " | Categoría: " + emp.getCategoria() + " | Años: " + emp.anyos + "\n");
        }
    }

    /**
     * 5.2 Mostrar salario por DNI
     */
    public static void mostrarSalario(String dni) {
        String sql = "SELECT sueldo FROM Nominas WHERE dni = ?";
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("El salario del DNI " + dni + " es: " + rs.getInt("sueldo"));
            } else {
                System.out.println("No se encontró el salario para el DNI: " + dni);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener salario: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar: Obtiene un Empleado concreto por su DNI
     */
    public static Empleado obtenerEmpleado(String dni) {
        String sql = "SELECT nombre, sexo, categoria, anyos FROM Empleados WHERE dni = ?";
        try (Connection conn = conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                char sexo = rs.getString("sexo").charAt(0);
                int categoria = rs.getInt("categoria");
                int anyos = rs.getInt("anyos");
                return new Empleado(nombre, dni, sexo, categoria, anyos);
            }
        } catch (Exception e) {
            System.err.println("Error al buscar empleado: " + e.getMessage());
        }
        return null; // Si no existe
    }

    /**
     * 4 y 5.6 Realizar copia de seguridad (Backup) en ficheros
     */
    public static void realizarBackup() {
        List<Empleado> empleados = leerEmpleadosBD();
        if (!empleados.isEmpty()) {
            FicherosUtils.actualizarFicheroEmpleados(empleados, "empleados.txt");
            FicherosUtils.procesarFicheros("empleados.txt", "sueldos.dat");
            System.out.println("Backup completado con éxito en los ficheros de texto y binario.");
        } else {
            System.out.println("No hay datos en la base de datos para respaldar.");
        }
    }
}
