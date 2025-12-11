package BaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.*;

public class CuentaDAO {

    // 1️⃣ Crear / INSERT
    public static boolean insertarCuenta(String codigoCuenta, String codigoCliente) {
        String sql = "INSERT INTO cuenta(codigoCuenta, saldo, codigoCliente) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ps.setDouble(2, 0.0);
            ps.setString(3, codigoCliente);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar cuenta: " + e.getMessage());
            return false;
        }
    }

    // 2️⃣ Leer / SELECT → listar todas las cuentas
    public static List<Cuenta> listarCuentas() {
        List<Cuenta> cuentas = new ArrayList<>();
        String sql = "SELECT codigoCuenta FROM cuenta";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                cuentas.add(new Cuenta(rs.getString("codigoCuenta")));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar cuentas: " + e.getMessage());
        }

        return cuentas;
    }

    // 2️⃣b Leer / SELECT → buscar una cuenta específica
    public static Cuenta obtenerCuenta(String codigoCuenta) {
        String sql = "SELECT codigoCuenta FROM cuenta WHERE codigoCuenta = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cuenta(rs.getString("codigoCuenta"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error obtenerCuentaObjeto: " + e.getMessage());
        }
        return null;
    }
    
        public static List<Cuenta> buscarCuentasCliente(String codigoCliente) {
            List<Cuenta> cuentas = new ArrayList<>();

            String sql = "SELECT codigoCuenta FROM cuenta WHERE codigoCliente = ?";

            try (Connection conn = Conexion.conectar();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, codigoCliente);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    // Creas la cuenta usando solo el código
                    Cuenta c = new Cuenta(rs.getString("codigoCuenta"));
                    cuentas.add(c);
                }

            } catch (SQLException e) {
                System.out.println("Error al obtener cuentas del cliente: " + e.getMessage());
            }

            return cuentas;
        }

        public static String obtenerCodigoClientePorCuenta(String codigoCuenta) {
        String sql = "SELECT codigoCliente FROM cuenta WHERE codigoCuenta = ?";
        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("codigoCliente");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener cliente de la cuenta: " + e.getMessage());
        }
        return null;
    }

    public static Double obtenerSaldo(String codigoCuenta) {
        String sql = "SELECT saldo FROM cuenta WHERE codigoCuenta = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("saldo"); // devuelve el saldo
            } else {
                return null; // cuenta no encontrada
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener saldo: " + e.getMessage());
            return null;
        }
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