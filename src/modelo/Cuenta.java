package modelo;

import java.util.ArrayList;

public class Cuenta {
    /*Atributos*/
    private String codigoCuenta;
    private double saldo;
    private ArrayList<Transaccion> historial;

    /*Constructor*/
    public Cuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
        this.saldo = 0;
        this.historial = new ArrayList<>();
    }

    /*Agregar transacción al ArrayList*/
    public void agregarTransaccion(Transaccion transaccion) {
        historial.add(transaccion);
    }

    public void mostrarHistorial() {
        System.out.println("Historial de transacciones de la cuenta " + codigoCuenta + ":");
        for (Transaccion t : historial) {
            t.mostrarEstado();
        }
    }

    public void mostrarSaldo() {
        System.out.println("Saldo actual: S/." + saldo);
    }

    /*Getters y setters*/
    public String getCodigoCuenta() {return codigoCuenta;}
    public double getSaldo() {return saldo;}
    public ArrayList<Transaccion> getHistorial() {return historial;}
    
    public void setSaldo(double saldo) {this.saldo = saldo;}

    public void mostrarDatos() {
        System.out.println("Número de cuenta: " + codigoCuenta + " | Saldo: " + saldo);
    }
}