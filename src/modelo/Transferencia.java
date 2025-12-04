package modelo;

public class Transferencia extends Transaccion {
    /*Atributos*/
    private Cuenta cuentaDestino;

    /*Constructor con empleado*/
    public Transferencia(Empleado empleado, Cuenta cuentaDestino, double monto, String id) {
        super(empleado, monto, id);
        this.cuentaDestino = cuentaDestino;
    }

    /*Constructor sin empleado*/
    public Transferencia(Cuenta cuentaDestino, double monto, String id) {
        super(monto, id);
        this.cuentaDestino = cuentaDestino;
    }

    @Override
    public void procesar(Cuenta cuenta) {
        if (validarMonto() && saldoSuficiente(cuenta)) {
            Cuenta origen = cuenta;
            origen.setSaldo(origen.getSaldo() - monto);
            cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);
        } else {
            System.out.println("Transferencia no válida.");
        }
    }

    @Override
    public boolean validarMonto() {
        return super.validarMonto() && monto >= 10;
    }

    public boolean saldoSuficiente(Cuenta cuenta) {
        return cuenta.getSaldo() >= monto;
    }

    @Override
    public void mostrarEstado() {
        System.out.println("--- Transferencia ---");
        super.mostrarEstado();
        System.out.println("Cuenta destino: " +
                (cuentaDestino != null ? cuentaDestino.getCodigoCuenta() : "N/A"));
    }
}