package modelo;

public class Administrador extends Empleado {

    /* Constructor */
    public Administrador(String nombre, String apellido, String telefono, String correo,
                        int edad, String dni, String direccion, String codigoEmpleado) {
        
        super(nombre, apellido, telefono, correo, edad, dni, direccion, codigoEmpleado);
    }

    /* Podemos sobrescribir mostrarDatos si deseas que muestre el tipo */
    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println("Tipo de usuario: Administrador");
    }
}
