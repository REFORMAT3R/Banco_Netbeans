package modelo;

public class Deposito extends Transaccion {
    /*Constructor con empleado*/
    public Deposito(Empleado empleado, double monto, String id) {
        super(empleado, monto, id);
    }

    /*Constructor sin empleado*/
    public Deposito(double monto, String id) {
        super(monto, id);
    }

    @Override
    public void procesar(Cuenta cuenta) {
        if (validarMonto()) {
            double nuevoSaldo = cuenta.getSaldo() + monto;
            cuenta.setSaldo(nuevoSaldo);
        } else {
            System.out.println("Monto inválido para depósito.");
        }
    }

    @Override
    public void mostrarEstado() {
        System.out.println("--- Depósito ---");
        super.mostrarEstado();
    }
}