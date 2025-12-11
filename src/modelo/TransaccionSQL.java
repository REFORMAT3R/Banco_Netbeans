package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import BaseDatos.*;

public class TransaccionSQL {
    private int id;
    private String codigoCuenta;
    private String codigoCuentaDestino;
    private String codigoEmpleado;
    private double monto;
    private String tipo;
    private LocalDateTime fechaHora;
    
    private static final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss a");

    public TransaccionSQL(int id, String codigoCuenta, String codigoCuentaDestino,
                          String codigoEmpleado, double monto, String tipo, LocalDateTime fechaHora) {
        this.id = id;
        this.codigoCuenta = codigoCuenta;
        this.codigoCuentaDestino = codigoCuentaDestino;
        this.codigoEmpleado = codigoEmpleado;
        this.monto = monto;
        this.tipo = tipo;
        this.fechaHora = fechaHora;
    }
    
    public static List<TransaccionSQL> historialCliente(String codigoCliente) {
        List<TransaccionSQL> historial = new ArrayList<>();

        String sql = "SELECT t.* FROM transaccion t " +
                     "JOIN cuenta c ON t.codigoCuenta = c.codigoCuenta " +
                     "WHERE c.codigoCliente = ? " +
                     "   OR t.codigoCuentaDestino IN (SELECT codigoCuenta FROM cuenta WHERE codigoCliente = ?) " +
                     "ORDER BY t.fechaHora DESC";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCliente);
            ps.setString(2, codigoCliente);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("idTransaccion");
                    String cuenta = rs.getString("codigoCuenta");
                    String destino = rs.getString("codigoCuentaDestino"); // puede ser null
                    String empleado = rs.getString("codigoEmpleado"); // puede ser null
                    double monto = rs.getDouble("monto");
                    String tipo = rs.getString("tipo");
                    Timestamp ts = rs.getTimestamp("fechaHora");
                    LocalDateTime fechaHora = ts != null ? ts.toLocalDateTime() : LocalDateTime.now();

                    TransaccionSQL t = new TransaccionSQL(
                        id, cuenta, destino, empleado, monto, tipo, fechaHora
                    );

                    historial.add(t);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener historial del cliente: " + e.getMessage());
        }

        return historial;
    }
    
    // en TransaccionSQL (o TransaccionDAO)
    public static List<TransaccionSQL> historialCuenta(String codigoCuenta) {
        List<TransaccionSQL> historial = new ArrayList<>();

        String sql = "SELECT * FROM transaccion WHERE codigoCuenta = ? OR codigoCuentaDestino = ? ORDER BY fechaHora DESC";

        try (Connection conn = Conexion.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codigoCuenta);
            ps.setString(2, codigoCuenta);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("idTransaccion");
                    String cuenta = rs.getString("codigoCuenta");
                    String destino = rs.getString("codigoCuentaDestino");
                    String empleado = rs.getString("codigoEmpleado");
                    double monto = rs.getDouble("monto");
                    String tipo = rs.getString("tipo");
                    Timestamp ts = rs.getTimestamp("fechaHora");
                    LocalDateTime fechaHora = ts != null ? ts.toLocalDateTime() : LocalDateTime.now();

                    TransaccionSQL t = new TransaccionSQL(
                        id, cuenta, destino, empleado, monto, tipo, fechaHora
                    );

                    historial.add(t);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener historial de la cuenta: " + e.getMessage());
        }

        return historial;
    }

    public double getMonto() { return monto; }
    public String getTipo() { return tipo; }
    public LocalDateTime getFechaHora() {return fechaHora;}
    public String getFechaFormateada() {return fechaHora.format(formatoFecha);}
    public String getHoraFormateada() {return fechaHora.format(formatoHora);}
}

