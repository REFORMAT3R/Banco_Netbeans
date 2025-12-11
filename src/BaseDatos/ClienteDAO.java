package BaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // 1️⃣ Crear / INSERT
    public static boolean insertarCliente(String nombre, String apellido, String telefono,
                                          String correo, int edad, String dni,
                                          String direccion, String codigoCliente) {
        String sql = "INSERT INTO cliente(nombre, apellido, telefono, correo, edad, dni, direccion, codigoCliente) " +
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
            ps.setString(8, codigoCliente);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    // 2️⃣ Leer / SELECT → listar todos los clientes
    public static List<String> listarClientes() {
        List<String> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String cliente = rs.getString("codigoCliente") + " - " +
                                 rs.getString("nombre") + " " +
                                 rs.getString("apellido") + " - " +
                                 rs.getString("correo");
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    // Actualizar teléfono
    public static boolean actualizarTelefono(String codigoCliente, String nuevoTelefono) {
        String sql = "UPDATE cliente SET telefono = ? WHERE codigoCliente = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoTelefono);
            ps.setString(2, codigoCliente);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar teléfono: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean actualizarCorreo(String codigoCliente, String nuevoCorreo) {
        String sql = "UPDATE cliente SET correo = ? WHERE codigoCliente = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevoCorreo);
            ps.setString(2, codigoCliente);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar correo: " + e.getMessage());
            return false;
        }
    }

    // Actualizar solo la dirección
    public static boolean actualizarDireccion(String codigoCliente, String nuevaDireccion) {
        String sql = "UPDATE cliente SET direccion = ? WHERE codigoCliente = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nuevaDireccion);
            ps.setString(2, codigoCliente);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar dirección: " + e.getMessage());
            return false;
        }
    }

    // 4️⃣ Eliminar / DELETE
    public static boolean eliminarCliente(String codigoCliente) {
        String sql = "DELETE FROM cliente WHERE codigoCliente = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCliente);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}
