package modelo;

public class Cliente extends Persona {
    /*Atributos*/
    private String codigoCliente;

    /*Constructor*/
    public Cliente(String nombre, String apellido, String telefono, String correo,
                int edad, String dni, String direccion, String codigoCliente) {
        super(nombre, apellido, telefono, correo, edad, dni, direccion);
        this.codigoCliente = codigoCliente;
    }

    /*Getter y Setter*/
    public String getCodigoCliente() {
        return codigoCliente;
    }
    
    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    @Override
    public void mostrarDatos() {
        super.mostrarDatos(); 
        System.out.println("Código de Cliente: " + codigoCliente);
    }
}