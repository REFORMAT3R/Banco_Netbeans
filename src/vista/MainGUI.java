package vista;

// Importa tus clases de lógica (ajusta el nombre del paquete si es necesario)
import javax.swing.*;
import modelo.*; 

public class MainGUI {
    public static void main(String[] args) {
        try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
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