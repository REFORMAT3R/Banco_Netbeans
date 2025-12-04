package modelo;

public class UsuarioEmpleado extends Usuario {
    private Empleado empleado;

    public UsuarioEmpleado(String nombreUsuario, String contrasenia, boolean estado, Empleado empleado) {
        super(nombreUsuario, contrasenia, estado);
        this.empleado = empleado;
    }

    public UsuarioEmpleado(String nombreUsuario, String contrasenia, boolean estado) {
        super(nombreUsuario, contrasenia, estado);
        this.empleado = null;
    }

    public Empleado getEmpleado() {return empleado;}
    public void setEmpleado(Empleado empleado) {this.empleado = empleado;}

    // Sobrescritura del método mostrarPermisos según la guía de laboratorio
    @Override
    public void mostrarPermisos() {
        System.out.println("=== Permisos de Usuario Empleado ==="); // Encabezado de permisos
        System.out.println("- Gestionar creación de cuentas"); // Permiso para crear cuentas bancarias
        System.out.println("- Gestionar transacciones de clientes"); // Permiso para procesar transacciones de clientes
    }

    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println("Tipo de usuario: Empleado");
        if (empleado != null) {
            System.out.println("Código de Empleado: " + empleado.getCodigoEmpleado());
        }
    }
}