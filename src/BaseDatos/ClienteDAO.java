package BaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.*;

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
    public static List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cl = new Cliente(
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getInt("edad"),
                    rs.getString("dni"),
                    rs.getString("direccion"),
                    rs.getString("codigoCliente")
                );
                lista.add(cl);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return lista;
    }
    
// En BaseDatos.ClienteDAO
    public static Cliente obtenerCliente(String codigoCliente) {
        String sql = "SELECT nombre, apellido, telefono, correo, edad, dni, direccion, codigoCliente FROM cliente WHERE codigoCliente = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente cl = new Cliente(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getInt("edad"),
                        rs.getString("dni"),
                        rs.getString("direccion"),
                        rs.getString("codigoCliente")
                    );
                    return cl;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error obtenerClienteObjeto: " + e.getMessage());
        }
        return null;
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
        Connection conn = null;

        try {
            conn = Conexion.conectar();
            conn.setAutoCommit(false); // ⚠️ IMPORTANTE: Inicio de Transacción

            // --- PASO 1: Usar CuentaDAO para borrar las cuentas ---
            // Le pasamos nuestra conexión activa 'conn'
            CuentaDAO.eliminarCuentasPorCliente(conn, codigoCliente);

            // --- PASO 2: Borrar al cliente (Responsabilidad propia) ---
            String sqlCliente = "DELETE FROM cliente WHERE codigoCliente = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlCliente)) {
                ps.setString(1, codigoCliente);
                int filasAfectadas = ps.executeUpdate();

                if (filasAfectadas > 0) {
                    conn.commit(); // ✅ Todo salió bien: CONFIRMAR CAMBIOS
                    return true;
                } else {
                    conn.rollback(); // ❌ No se borró el cliente: DESHACER TODO
                    return false;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error transacción eliminar cliente: " + e.getMessage());
            // En caso de CUALQUIER error, deshacemos lo de las cuentas y lo del cliente
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            // Cerramos la conexión al final de todo
            try {
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                System.out.println("Error cerrando conexión: " + ex.getMessage());
            }
        }
    }
    
    // Validar DNI
    public static boolean existeDNI(String dni) {
        String sql = "SELECT codigoCliente FROM cliente WHERE dni = ?";
        try (java.sql.Connection conn = Conexion.conectar();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (java.sql.ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (java.sql.SQLException e) { return false; }
    }

    // Validar Correo
    public static boolean existeCorreo(String correo) {
        String sql = "SELECT codigoCliente FROM cliente WHERE correo = ?";
        try (java.sql.Connection conn = Conexion.conectar();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (java.sql.ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (java.sql.SQLException e) { return false; }
    }

    // Validar Teléfono
    public static boolean existeTelefono(String telefono) {
        String sql = "SELECT codigoCliente FROM cliente WHERE telefono = ?";
        try (java.sql.Connection conn = Conexion.conectar();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, telefono);
            try (java.sql.ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (java.sql.SQLException e) { return false; }
    }
}
