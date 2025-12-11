package BaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    // 1️⃣ Crear / INSERT
    public static boolean insertarEmpleado(String nombre, String apellido, String telefono,
                                           String correo, int edad, String dni,
                                           String direccion, String codigoEmpleado) {
        String sql = "INSERT INTO empleado(nombre, apellido, telefono, correo, edad, dni, direccion, codigoEmpleado) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, telefono);
            ps.setString(4, correo);
            ps.setInt(5, edad);
            ps.setString(6, dni);
            ps.setString(7, direccion);
            ps.setString(8, codigoEmpleado);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar empleado: " + e.getMessage());
            return false;
        }
    }

    // 2️⃣ Leer / SELECT → listar todos los empleados
    public static List<String> listarEmpleados() {
        List<String> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleado";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String empleado = rs.getString("codigoEmpleado") + " - " +
                                  rs.getString("nombre") + " " +
                                  rs.getString("apellido") + " - " +
                                  rs.getString("correo");
                empleados.add(empleado);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar empleados: " + e.getMessage());
        }

        return empleados;
    }

    // 2️⃣b Leer / SELECT → buscar un empleado específico por código
    public static String obtenerEmpleado(String codigoEmpleado) {
        String sql = "SELECT * FROM empleado WHERE codigoEmpleado = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoEmpleado);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("codigoEmpleado") + " - " +
                       rs.getString("nombre") + " " +
                       rs.getString("apellido") + " - " +
                       rs.getString("correo") + " - " +
                       rs.getString("telefono") + " - " +
                       rs.getInt("edad") + " - " +
                       rs.getString("dni") + " - " +
                       rs.getString("direccion");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener empleado: " + e.getMessage());
        }
        return null; // si no existe
    }

    // Actualizar teléfono
    public static boolean actualizarTelefono(String codigoEmpleado, String nuevoTelefono) {
        String sql = "UPDATE empleado SET telefono = ? WHERE codigoEmpleado = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoTelefono);
            ps.setString(2, codigoEmpleado);
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar teléfono: " + e.getMessage());
            return false;
        }
    }
    
// Actualizar solo el correo de un empleado
    public static boolean actualizarCorreo(String codigoEmpleado, String nuevoCorreo) {
        String sql = "UPDATE empleado SET correo = ? WHERE codigoEmpleado = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoCorreo);
            ps.setString(2, codigoEmpleado);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar correo del empleado: " + e.getMessage());
            return false;
        }
    }

    // Actualizar solo la dirección de un empleado
    public static boolean actualizarDireccion(String codigoEmpleado, String nuevaDireccion) {
        String sql = "UPDATE empleado SET direccion = ? WHERE codigoEmpleado = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevaDireccion);
            ps.setString(2, codigoEmpleado);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar dirección del empleado: " + e.getMessage());
            return false;
        }
    }
    
    // 4️⃣ Eliminar / DELETE
    public static boolean eliminarEmpleado(String codigoEmpleado) {
        String sql = "DELETE FROM empleado WHERE codigoEmpleado = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoEmpleado);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }
}