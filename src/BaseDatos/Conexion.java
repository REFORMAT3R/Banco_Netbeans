package BaseDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//DB
public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/banco1?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // fuerza cargar driver
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado: " + e.getMessage());
            return null;
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return null;
        }
    }
}
