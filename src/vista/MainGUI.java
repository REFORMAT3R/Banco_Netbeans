package vista;

// Importa tus clases de lógica (ajusta el nombre del paquete si es necesario)
import modelo.*; 

public class MainGUI {
    public static void main(String[] args) {
        // 1. Inicializamos la lógica del negocio
        Banco banco = new Banco();
        GestorUsuarios gestorUsuarios = new GestorUsuarios();

        // 2. Cargamos los datos de prueba (Admin, Clientes, Cuentas)
        // Usamos tu clase Inicializador existente [cite: 15]
        Inicializador.cargarDatosIniciales(banco, gestorUsuarios);

        // 3. Hacemos visible la primera ventana (Login)
        /* Nota: Crearemos LoginFrame en el siguiente paso, por ahora saldrá error */
        java.awt.EventQueue.invokeLater(() -> {
            new LoginFrame(banco, gestorUsuarios).setVisible(true);
        });
    }
}