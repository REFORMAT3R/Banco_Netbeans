package modelo;

public class UsuarioCliente extends Usuario {
    private Cliente cliente;

    public UsuarioCliente(String nombreUsuario, String contrasenia, boolean estado, Cliente cliente) {
        super(nombreUsuario, contrasenia, estado);
        this.cliente = cliente;
    }

    public UsuarioCliente(String nombreUsuario, String contrasenia, boolean estado) {
        super(nombreUsuario, contrasenia, estado);
        this.cliente = null;
    }

    public Cliente getCliente() {return cliente;}
    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    // Sobrescritura del método mostrarPermisos según la guía de laboratorio
    @Override
    public void mostrarPermisos() {
        System.out.println("=== Permisos de Usuario Cliente ==="); // Encabezado de permisos
        System.out.println("- Depositar"); // Permiso para realizar depósitos
        System.out.println("- Retirar"); // Permiso para realizar retiros
        System.out.println("- Ver movimientos"); // Permiso para consultar movimientos
    }

    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println("Tipo de usuario: Cliente");
        if (cliente != null) {
            System.out.println("Código de Cliente: " + cliente.getCodigoCliente());
        }
    }

    
}