package modelo;

import java.util.Scanner;

public class GestorEntradas {
    
    private static Scanner sc = new Scanner(System.in);
    
    /* Constructor privado */
    private GestorEntradas() {}
    
    /* === SOLICITAR DATOS CON VALIDACIÓN Y REINTENTO === */
    
    public static String solicitarNombre(String campo) {
        String nombre = "";
        boolean valido = false;
        
        while (!valido) {
            System.out.print("Ingrese " + campo + ": ");
            nombre = sc.nextLine();
            
            if (Validaciones.validarNombre(nombre)) {
                valido = true;
            } else {
                System.out.println("Error: " + Validaciones.obtenerMensajeError("nombre"));
            }
        }
        return nombre;
    }
    
    public static String solicitarDNI() {
        String dni = "";
        boolean valido = false;
        
        while (!valido) {
            System.out.print("Ingrese DNI: ");
            dni = sc.nextLine();
            
            if (Validaciones.validarDNI(dni)) {
                valido = true;
            } else {
                System.out.println("Error: " + Validaciones.obtenerMensajeError("dni"));
            }
        }
        return dni;
    }
    
    public static String solicitarCelular() {
        String celular = "";
        boolean valido = false;
        
        while (!valido) {
            System.out.print("Ingrese telefono/celular: ");
            celular = sc.nextLine();
            
            if (Validaciones.validarCelular(celular)) {
                valido = true;
            } else {
                System.out.println("Error: " + Validaciones.obtenerMensajeError("celular"));
            }
        }
        return celular;
    }
    
    public static String solicitarCorreo() {
        String correo = "";
        boolean valido = false;
        
        while (!valido) {
            System.out.print("Ingrese correo electronico: ");
            correo = sc.nextLine();
            
            if (Validaciones.validarCorreo(correo)) {
                valido = true;
            } else {
                System.out.println("Error: " + Validaciones.obtenerMensajeError("correo"));
            }
        }
        return correo;
    }
    
    public static int solicitarEdad() {
        int edad = 0;
        boolean valido = false;
        
        while (!valido) {
            System.out.print("Ingrese edad: ");
            try {
                edad = Integer.parseInt(sc.nextLine());
                
                if (Validaciones.validarEdad(edad)) {
                    valido = true;
                } else {
                    System.out.println("Error: " + Validaciones.obtenerMensajeError("edad"));
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero valido.");
            }
        }
        return edad;
    }
    
    public static String solicitarDireccion() {
        String direccion = "";
        boolean valido = false;
        
        while (!valido) {
            System.out.print("Ingrese direccion: ");
            direccion = sc.nextLine();
            
            if (Validaciones.validarDireccion(direccion)) {
                valido = true;
            } else {
                System.out.println("Error: " + Validaciones.obtenerMensajeError("direccion"));
            }
        }
        return direccion;
    }
    
    public static String solicitarCodigoCliente() {
        String codigo = "";
        boolean valido = false;
        
        while (!valido) {
            System.out.print("Ingrese codigo de cliente (Formato: CLI001): ");
            codigo = sc.nextLine();
            
            if (Validaciones.validarCodigoCliente(codigo)) {
                valido = true;
            } else {
                System.out.println("Error: " + Validaciones.obtenerMensajeError("codigo_cliente"));
            }
        }
        return codigo;
    }
    
    public static String solicitarCodigoEmpleado() {
        String codigo = "";
        boolean valido = false;
        
        while (!valido) {
            System.out.print("Ingrese codigo de empleado (Formato: EMP001): ");
            codigo = sc.nextLine();
            
            if (Validaciones.validarCodigoEmpleado(codigo)) {
                valido = true;
            } else {
                System.out.println("Error: " + Validaciones.obtenerMensajeError("codigo_empleado"));
            }
        }
        return codigo;
    }
    
    public static String solicitarCodigoCuenta() {
        String codigo = "";
        boolean valido = false;
        
        while (!valido) {
            System.out.print("Ingrese codigo de cuenta (Formato: CTA00012345): ");
            codigo = sc.nextLine();
            
            if (Validaciones.validarCodigoCuenta(codigo)) {
                valido = true;
            } else {
                System.out.println("Error: " + Validaciones.obtenerMensajeError("codigo_cuenta"));
            }
        }
        return codigo;
    }
    
    public static String solicitarNombreUsuario() {
        String nombreUsuario = "";
        boolean valido = false;
        
        while (!valido) {
            System.out.print("Ingrese nombre de usuario: ");
            nombreUsuario = sc.nextLine();
            
            if (Validaciones.validarNombreUsuario(nombreUsuario)) {
                valido = true;
            } else {
                System.out.println("Error: " + Validaciones.obtenerMensajeError("nombreusuario"));
            }
        }
        return nombreUsuario;
    }
    
    public static String solicitarContrasena() {
        String contrasena = "";
        boolean valido = false;
        
        while (!valido) {
            System.out.print("Ingrese contrasena: ");
            contrasena = sc.nextLine();
            
            if (Validaciones.validarContrasena(contrasena)) {
                valido = true;
            } else {
                System.out.println("Error: " + Validaciones.obtenerMensajeError("contrasena"));
            }
        }
        return contrasena;
    }
    
    public static String solicitarIdTransaccion() {
        String id = "";
        boolean valido = false;
        
        while (!valido) {
            System.out.print("Ingrese ID de transaccion (Formato: TXN12345): ");
            id = sc.nextLine();
            
            if (Validaciones.validarIdTransaccion(id)) {
                valido = true;
            } else {
                System.out.println("Error: " + Validaciones.obtenerMensajeError("id_transaccion"));
            }
        }
        return id;
    }
    
    public static double solicitarMonto(String tipoTransaccion) {
        double monto = 0;
        boolean valido = false;
        
        while (!valido) {
            System.out.print("Ingrese monto a " + tipoTransaccion + ": ");
            try {
                monto = Double.parseDouble(sc.nextLine());
                
                if (monto > 0) {
                    valido = true;
                } else {
                    System.out.println("Error: El monto debe ser mayor a 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero valido.");
            }
        }
        return monto;
    }
    
    /* === MÉTODOS COMPUESTOS PARA REGISTRAR === */
    
    public static Cliente solicitarDatosCliente(Banco banco) {
        System.out.println("\n=== REGISTRO DE CLIENTE ===");
        
        String nombre = solicitarNombre("nombre");
        String apellido = solicitarNombre("apellido");
        String telefono = solicitarCelular();
        String correo = solicitarCorreo();
        int edad = solicitarEdad();
        String dni = solicitarDNI();
        String direccion = solicitarDireccion();
        
        String codigoCliente = "";
        boolean codigoValido = false;
        
        while (!codigoValido) {
            codigoCliente = solicitarCodigoCliente();
            
            if (banco.buscarCliente(codigoCliente) != null) {
                System.out.println("Error: Ya existe un cliente con ese codigo. Intente con otro.");
            } else {
                codigoValido = true;
            }
        }
        
        Cliente cliente = new Cliente(nombre, apellido, telefono, correo, edad, dni, direccion, codigoCliente);
        System.out.println("Datos del cliente capturados correctamente.");
        return cliente;
    }
    
    public static Empleado solicitarDatosEmpleado(Banco banco) {
        System.out.println("\n=== REGISTRO DE EMPLEADO ===");
        
        String nombre = solicitarNombre("nombre");
        String apellido = solicitarNombre("apellido");
        String telefono = solicitarCelular();
        String correo = solicitarCorreo();
        int edad = solicitarEdad();
        String dni = solicitarDNI();
        String direccion = solicitarDireccion();
        
        String codigoEmpleado = "";
        boolean codigoValido = false;
        
        while (!codigoValido) {
            codigoEmpleado = solicitarCodigoEmpleado();
            
            if (banco.buscarEmpleado(codigoEmpleado) != null) {
                System.out.println("Error: Ya existe un empleado con ese codigo. Intente con otro.");
            } else {
                codigoValido = true;
            }
        }
        
        Empleado empleado = new Empleado(nombre, apellido, telefono, correo, edad, dni, direccion, codigoEmpleado);
        System.out.println("Datos del empleado capturados correctamente.");
        return empleado;
    }
    
    public static Administrador solicitarDatosAdministrador(Banco banco) {
        System.out.println("\n=== REGISTRO DE ADMINISTRADOR ===");
        
        String nombre = solicitarNombre("nombre");
        String apellido = solicitarNombre("apellido");
        String telefono = solicitarCelular();
        String correo = solicitarCorreo();
        int edad = solicitarEdad();
        String dni = solicitarDNI();
        String direccion = solicitarDireccion();
        
        String codigoEmpleado = "";
        boolean codigoValido = false;
        
        while (!codigoValido) {
            codigoEmpleado = solicitarCodigoEmpleado();
            
            if (banco.buscarEmpleado(codigoEmpleado) != null) {
                System.out.println("Error: Ya existe un empleado con ese codigo. Intente con otro.");
            } else {
                codigoValido = true;
            }
        }
        
        Administrador admin = new Administrador(nombre, apellido, telefono, correo, edad, dni, direccion, codigoEmpleado);
        System.out.println("Datos del administrador capturados correctamente.");
        return admin;
    }
}