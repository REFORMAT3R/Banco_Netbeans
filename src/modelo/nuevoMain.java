package modelo;

public class nuevoMain {
    public static void main(String[] args) {

        Banco banco = new Banco();
        GestorUsuarios gestorUsuarios = new GestorUsuarios();

        // Datos iniciales
        Inicializador.cargarDatosIniciales(banco, gestorUsuarios);

        // Interfaz de usuario
        MenuSistema menu = new MenuSistema(banco, gestorUsuarios);
        menu.iniciar();
    }
}