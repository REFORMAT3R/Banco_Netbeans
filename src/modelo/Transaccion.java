package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Transaccion {
    /*Atributos*/
    protected Empleado empleado;
    protected double monto;
    protected LocalDateTime fechaHora;
    protected String id;
    
    // Formateadores estáticos para toda la clase
    protected static final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    protected static final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss a");

    /*Constructor con empleado*/
    public Transaccion(Empleado empleado, double monto, String id) {
        this.empleado = empleado;
        this.monto = monto;
        this.fechaHora = LocalDateTime.now();
        this.id = id;
    }

    /*Constructor sin empleado*/
    public Transaccion(double monto, String id) {
        this.empleado = null;
        this.monto = monto;
        this.fechaHora = LocalDateTime.now();
        this.id = id;
    }

    /*Polimorfismo*/
    public abstract void procesar(Cuenta cuenta);

    public boolean validarMonto() {
        return monto > 0;
    }

    /*Polimorfismo*/
    public void mostrarEstado() {
        System.out.println("--- Estado de Transacción ---");
        System.out.println("ID: " + id);
        System.out.println("Monto: " + monto);
        System.out.println("Fecha: " + fechaHora.format(formatoFecha));
        System.out.println("Hora: " + fechaHora.format(formatoHora));
        if(empleado != null) {
            System.out.println("Código de empleado: " + empleado.getCodigoEmpleado());
        }
    }
    public double getMonto() { return monto; }
    
    /*Getters*/
    public LocalDateTime getFechaHora() {return fechaHora;}
    public String getFechaFormateada() {return fechaHora.format(formatoFecha);}
    public String getHoraFormateada() {return fechaHora.format(formatoHora);}
}