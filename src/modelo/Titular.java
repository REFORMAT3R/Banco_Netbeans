package modelo;

public class Titular {
    /*Atributos*/
    private Cliente cliente;
    private Cuenta cuenta;

    /*Constructor*/
    public Titular(Cliente cliente, Cuenta cuenta) {
        this.cliente = cliente;
        this.cuenta = cuenta;
    }
    
    public void mostrarDatos() {
        System.out.println("=== Datos del Titular ===");
        cliente.mostrarDatos();
        cuenta.mostrarSaldo();
    }

    /*Getters*/
    public Cliente getCliente() {
        return cliente;
    }
    
    public Cuenta getCuenta() {
        return cuenta;
    }
}