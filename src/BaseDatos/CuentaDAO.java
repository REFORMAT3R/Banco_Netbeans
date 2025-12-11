package BaseDatos;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CuentaDAO {

    // 1️⃣ Crear / INSERT
    public static boolean insertarCuenta(String codigoCuenta, String codigoCliente) {
        String sql = "INSERT INTO cuenta(codigoCuenta, saldo, codigoCliente) VALUES (?, 0, ?)";
        try (Connection conn = Conexion.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ps.setString(2, codigoCliente);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar cuenta: " + e.getMessage());
            return false;
        }
    }

    // 2️⃣ Leer / SELECT → listar todas las cuentas
    public static List<String> listarCuentas() {
        List<String> cuentas = new ArrayList<>();
        String sql = "SELECT * FROM cuenta";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String cuenta = rs.getString("codigoCuenta") + " - " +
                                "Saldo: " + rs.getDouble("saldo") + " - " +
                                "Titular: " + rs.getString("codigoCliente");
                cuentas.add(cuenta);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar cuentas: " + e.getMessage());
        }

        return cuentas;
    }

    // 2️⃣b Leer / SELECT → buscar una cuenta específica
    public static String obtenerCuenta(String codigoCuenta) {
        String sql = "SELECT * FROM cuenta WHERE codigoCuenta = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("codigoCuenta") + " - " +
                       "Saldo: " + rs.getDouble("saldo") + " - " +
                       "Titular: " + rs.getString("codigoCliente");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener cuenta: " + e.getMessage());
        }
        return null;
    }
    

    // 3️⃣ Actualizar saldo
    public static boolean actualizarSaldo(String codigoCuenta, double nuevoSaldo) {
        String sql = "UPDATE cuenta SET saldo = ? WHERE codigoCuenta = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, nuevoSaldo);
            ps.setString(2, codigoCuenta);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar saldo: " + e.getMessage());
            return false;
        }
    }

    // 4️⃣ Eliminar / DELETE
    public static boolean eliminarCuenta(String codigoCuenta) {
        String sql = "DELETE FROM cuenta WHERE codigoCuenta = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar cuenta: " + e.getMessage());
            return false;
        }
    }
}