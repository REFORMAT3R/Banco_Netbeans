package BaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.*;

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
    public static List<Empleado> listarEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT codigoEmpleado, nombre, apellido, telefono, correo, edad, dni, direccion FROM empleado";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                empleados.add(new Empleado(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getInt("edad"),
                    rs.getString("dni"),
                    rs.getString("direccion"),
                    rs.getString("codigoEmpleado")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar empleados: " + e.getMessage());
        }

        return empleados;
    }




    // 2️⃣b Leer / SELECT → buscar un empleado específico por código
    // En BaseDatos.EmpleadoDAO
    public static Empleado obtenerEmpleado(String codigoEmpleado) {
        String sql = "SELECT nombre, apellido, telefono, correo, edad, dni, direccion, codigoEmpleado FROM empleado WHERE codigoEmpleado = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoEmpleado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Empleado e = new Empleado(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getInt("edad"),
                        rs.getString("dni"),
                        rs.getString("direccion"),
                        rs.getString("codigoEmpleado")
                    );
                    return e;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error obtenerEmpleadoObjeto: " + ex.getMessage());
        }
        return null;
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