package BaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.*;

public class TransaccionDAO {

    // 1️⃣ Crear / INSERT
    public static boolean insertarTransaccion(Connection conn, String codigoCuenta, String codigoCuentaDestino,
                                              String codigoEmpleado, double monto, String tipo) {
        String sql = "INSERT INTO transaccion(codigoCuenta, codigoCuentaDestino, codigoEmpleado, monto, tipo) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, codigoCuenta);
            ps.setString(2, codigoCuentaDestino); // puede ser null si es solo cliente
            ps.setString(3, codigoEmpleado);     // puede ser null si es solo cliente
            ps.setDouble(4, monto);
            ps.setString(5, tipo);               // "retiro", "deposito", "transferencia"

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar transacción: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                // no cerramos la conexión aquí, porque la maneja quien llama
            } catch (SQLException e) {
                System.out.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }


    // 2️⃣ Leer / SELECT → listar todas las transacciones
    public static List<TransaccionSQL> listarTransacciones() {
        List<TransaccionSQL> lista = new ArrayList<>();
        String sql = "SELECT * FROM transaccion";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TransaccionSQL t = new TransaccionSQL(
                    rs.getInt("idTransaccion"),
                    rs.getString("codigoCuenta"),
                    rs.getString("codigoCuentaDestino"),
                    rs.getString("codigoEmpleado"),
                    rs.getDouble("monto"),
                    rs.getString("tipo"),
                    rs.getTimestamp("fechaHora").toLocalDateTime()
                );

                lista.add(t);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar transacciones: " + e.getMessage());
        }

        return lista;
    }

    public static List<TransaccionSQL> historialCliente(String codigoCliente) {
        List<TransaccionSQL> historial = new ArrayList<>();
        String sql = "SELECT t.* FROM transaccion t " +
                     "JOIN cuenta c ON t.codigoCuenta = c.codigoCuenta " +
                     "WHERE c.codigoCliente = ? OR t.codigoCuentaDestino IN (SELECT codigoCuenta FROM cuenta WHERE codigoCliente = ?)";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCliente);
            ps.setString(2, codigoCliente);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                TransaccionSQL t = new TransaccionSQL(
                    rs.getInt("idTransaccion"),
                    rs.getString("codigoCuenta"),
                    rs.getString("codigoCuentaDestino"),
                    rs.getString("codigoEmpleado"),
                    rs.getDouble("monto"),
                    rs.getString("tipo"),
                    rs.getTimestamp("fechaHora").toLocalDateTime()
                );

                historial.add(t);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener historial del cliente: " + e.getMessage());
        }

        return historial;
    }

    // 2️⃣b Leer / SELECT → buscar transacción por ID
    public static TransaccionSQL obtenerTransaccion(int idTransaccion) {
        String sql = "SELECT * FROM transaccion WHERE idTransaccion = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTransaccion);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new TransaccionSQL(
                    rs.getInt("idTransaccion"),
                    rs.getString("codigoCuenta"),
                    rs.getString("codigoCuentaDestino"),
                    rs.getString("codigoEmpleado"),
                    rs.getDouble("monto"),
                    rs.getString("tipo"),
                    rs.getTimestamp("fechaHora").toLocalDateTime()
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener transacción: " + e.getMessage());
        }

        return null;
    }

    // 4️⃣ Eliminar / DELETE
    public static boolean eliminarTransaccion(int idTransaccion) {
        String sql = "DELETE FROM transaccion WHERE idTransaccion = ?";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTransaccion);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar transacción: " + e.getMessage());
            return false;
        }
    }
}