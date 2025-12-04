package modelo;

public class Empleado extends Persona {
    /*Atributo*/
    private String codigoEmpleado;

    /*Constructor*/
    public Empleado(String nombre, String apellido, String telefono, String correo,
                    int edad, String dni, String direccion, String codigoEmpleado) {
        super(nombre, apellido, telefono, correo, edad, dni, direccion);
        this.codigoEmpleado = codigoEmpleado;
    }

    /*Getter y Setter*/
    
    public String getApellido() {
        return apellido;
    }
    
    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }
    
    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    @Override
    public void mostrarDatos() {
        super.mostrarDatos(); 
        System.out.println("Código de Empleado: " + codigoEmpleado);
    }
}