package modelo;

public class UsuarioAdministrador extends Usuario {
    /* Atributo adicional */
    private Administrador administrador; // Referencia al objeto Administrador asociado

    /* Constructor */
    public UsuarioAdministrador(String nombreUsuario, String contrasenia, boolean estado,
                                Administrador administrador) {
        super(nombreUsuario, contrasenia, estado);
        this.administrador = administrador;
    }

    /* Constructor alternativo sin Administrador (se puede asignar después) */
    public UsuarioAdministrador(String nombreUsuario, String contrasenia, boolean estado) {
        super(nombreUsuario, contrasenia, estado);
        this.administrador = null;
    }

    public Administrador getAdministrador() {return administrador;}
    public void setAdministrador(Administrador administrador) {this.administrador = administrador;}

    /* Implementación de mostrarPermisos según la guía de laboratorio */
    @Override
    public void mostrarPermisos() {
        System.out.println("=== Permisos de Usuario Administrador ==="); // Encabezado de permisos
        System.out.println("- Crear usuarios"); // Permiso para crear nuevos usuarios en el sistema
        System.out.println("- Modificar usuarios"); // Permiso para modificar usuarios existentes
        System.out.println("- Eliminar usuarios"); // Permiso para eliminar usuarios del sistema
        System.out.println("- Permisos totales"); // Indica que tiene acceso completo al sistema
    }

    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println("Tipo de usuario: Administrador");
        if (administrador != null) {
            System.out.println("Código de Empleado: " + administrador.getCodigoEmpleado());
        }
    }
}