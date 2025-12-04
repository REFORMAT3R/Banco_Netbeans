package modelo;

import java.util.Scanner;

public class MenuSistema {
    private Banco banco;
    private GestorUsuarios gestorUsuarios;
    private Scanner sc;

    public MenuSistema(Banco banco, GestorUsuarios gestorUsuarios) {
        this.banco = banco;
        this.gestorUsuarios = gestorUsuarios;
        this.sc = new Scanner(System.in);
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== SISTEMA BANCARIO ===");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Salir");
            System.out.print("Opción: ");
            
            String opcion = sc.nextLine();
            if (opcion.equals("1")) {
                procesarLogin();
            } else if (opcion.equals("2")) {
                salir = true;
                System.out.println("Saliendo del sistema...");
            } else {
                System.out.println("Opción no válida.");
            }
        }
    }

    private void procesarLogin() {
        System.out.println("\n--- LOGIN ---");
        String user = GestorEntradas.solicitarNombreUsuario();
        String pass = GestorEntradas.solicitarContrasena();
        
        Usuario u = gestorUsuarios.login(user, pass);
        
        if (u != null) {
            if (u instanceof UsuarioAdministrador) {
                mostrarMenuAdmin((UsuarioAdministrador) u);
            } else if (u instanceof UsuarioEmpleado) {
                mostrarMenuEmpleado((UsuarioEmpleado) u);
            } else if (u instanceof UsuarioCliente) {
                mostrarMenuCliente((UsuarioCliente) u);
            }
        }
    }

    // ==========================================
    //           MENÚ DE ADMINISTRADOR
    // ==========================================
    private void mostrarMenuAdmin(UsuarioAdministrador admin) {
        boolean atras = false;
        while (!atras) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║   PANEL ADMINISTRADOR: " + admin.getNombreUsuario());
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.println("┌─ GESTIÓN DE USUARIOS ─────────────────────┐");
            System.out.println("│ 1.  Registrar Empleado y Usuario          │");
            System.out.println("│ 2.  Ver Usuarios Registrados               │");
            System.out.println("│ 3.  Ver Usuarios por Tipo                  │");
            System.out.println("│ 4.  Ver Estadísticas de Usuarios           │");
            System.out.println("│ 5.  Modificar Estado de Usuario            │");
            System.out.println("│ 6.  Eliminar Usuario                       │");
            System.out.println("├─ GESTIÓN DE CLIENTES ─────────────────────┤");
            System.out.println("│ 7.  Registrar Cliente y Usuario            │");
            System.out.println("│ 8.  Ver Lista de Clientes                  │");
            System.out.println("│ 9.  Ver Lista de Empleados                 │");
            System.out.println("├─ GESTIÓN DE CUENTAS ──────────────────────┤");
            System.out.println("│ 10. Crear Cuenta Bancaria                  │");
            System.out.println("│ 11. Ver Todas las Cuentas                  │");
            System.out.println("│ 12. Ver Cuentas de un Cliente              │");
            System.out.println("│ 13. Ver Lista de Titulares                 │");
            System.out.println("├─ TRANSACCIONES ───────────────────────────┤");
            System.out.println("│ 14. Realizar Depósito                      │");
            System.out.println("│ 15. Realizar Retiro                        │");
            System.out.println("│ 16. Realizar Transferencia                 │");
            System.out.println("│ 17. Ver Historial de Cuenta                │");
            System.out.println("├───────────────────────────────────────────┤");
            System.out.println("│ 18. Cambiar mi Contraseña                  │");
            System.out.println("│ 0.  Cerrar Sesión                          │");
            System.out.println("└───────────────────────────────────────────┘");
            System.out.print("Seleccione una opción: ");
            
            String op = sc.nextLine();
            switch(op) {
                case "1": registrarEmpleadoYUsuario(); break;
                case "2": gestorUsuarios.mostrarUsuarios(); break;
                case "3":
                    System.out.print("Tipo (cliente/empleado/administrador): ");
                    gestorUsuarios.mostrarUsuariosPorTipo(sc.nextLine());
                    break;
                case "4": gestorUsuarios.mostrarEstadisticas(); break;
                case "5": modificarUsuario(admin); break;
                case "6": eliminarUsuario(admin); break;
                case "7": registrarClienteYUsuario(); break;
                case "8": banco.mostrarClientes(); break;
                case "9": banco.mostrarEmpleados(); break;
                case "10": crearCuentaBancaria(); break;
                case "11": banco.mostrarCuentas(); break;
                case "12": mostrarCuentasCliente(); break;
                case "13": banco.mostrarTitulares(); break;
                case "14": procesarTransaccion("deposito", admin.getAdministrador()); break;
                case "15": procesarTransaccion("retiro", admin.getAdministrador()); break;
                case "16": procesarTransaccion("transferencia", admin.getAdministrador()); break;
                case "17": verHistorialCuenta(); break;
                case "18": cambiarMiContrasena(admin); break;
                case "0": 
                    atras = true;
                    System.out.println("Cerrando sesión...");
                    break;
                default: System.out.println("Opción incorrecta.");
            }
        }
    }

    // ==========================================
    //           MENÚ DE EMPLEADO
    // ==========================================
    private void mostrarMenuEmpleado(UsuarioEmpleado emp) {
        boolean atras = false;
        while (!atras) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║   PANEL EMPLEADO: " + emp.getEmpleado().getNombre());
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.println("┌─ GESTIÓN DE CLIENTES ─────────────────────┐");
            System.out.println("│ 1.  Registrar Cliente y Usuario            │");
            System.out.println("│ 2.  Ver Lista de Clientes                  │");
            System.out.println("│ 3.  Ver Usuarios Registrados               │");
            System.out.println("├─ GESTIÓN DE CUENTAS ──────────────────────┤");
            System.out.println("│ 4.  Crear Cuenta Bancaria                  │");
            System.out.println("│ 5.  Ver Todas las Cuentas                  │");
            System.out.println("│ 6.  Ver Cuentas de un Cliente              │");
            System.out.println("│ 7.  Ver Lista de Titulares                 │");
            System.out.println("├─ TRANSACCIONES ───────────────────────────┤");
            System.out.println("│ 8.  Realizar Depósito                      │");
            System.out.println("│ 9.  Realizar Retiro                        │");
            System.out.println("│ 10. Realizar Transferencia                 │");
            System.out.println("│ 11. Ver Historial de Cuenta                │");
            System.out.println("├───────────────────────────────────────────┤");
            System.out.println("│ 12. Cambiar mi Contraseña                  │");
            System.out.println("│ 0.  Cerrar Sesión                          │");
            System.out.println("└───────────────────────────────────────────┘");
            System.out.print("Seleccione una opción: ");
            
            String op = sc.nextLine();
            switch(op) {
                case "1": registrarClienteYUsuario(); break;
                case "2": banco.mostrarClientes(); break;
                case "3": gestorUsuarios.mostrarUsuarios(); break;
                case "4": crearCuentaBancaria(); break;
                case "5": banco.mostrarCuentas(); break;
                case "6": mostrarCuentasCliente(); break;
                case "7": banco.mostrarTitulares(); break;
                case "8": procesarTransaccion("deposito", emp.getEmpleado()); break;
                case "9": procesarTransaccion("retiro", emp.getEmpleado()); break;
                case "10": procesarTransaccion("transferencia", emp.getEmpleado()); break;
                case "11": verHistorialCuenta(); break;
                case "12": cambiarMiContrasena(emp); break;
                case "0": 
                    atras = true;
                    System.out.println("Cerrando sesión...");
                    break;
                default: System.out.println("Opción incorrecta.");
            }
        }
    }

    // ==========================================
    //           MENÚ DE CLIENTE
    // ==========================================
    private void mostrarMenuCliente(UsuarioCliente cli) {
        boolean atras = false;
        while (!atras) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║   PANEL CLIENTE: " + cli.getCliente().getNombre());
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.println("┌─ MIS CUENTAS ─────────────────────────────┐");
            System.out.println("│ 1. Ver mis Cuentas y Saldos                │");
            System.out.println("│ 2. Ver Historial de una Cuenta             │");
            System.out.println("├─ TRANSACCIONES ───────────────────────────┤");
            System.out.println("│ 3. Realizar Depósito                       │");
            System.out.println("│ 4. Realizar Retiro                         │");
            System.out.println("│ 5. Realizar Transferencia                  │");
            System.out.println("├─ MI PERFIL ───────────────────────────────┤");
            System.out.println("│ 6. Ver mis Datos Personales                │");
            System.out.println("│ 7. Cambiar mi Contraseña                   │");
            System.out.println("├───────────────────────────────────────────┤");
            System.out.println("│ 0. Cerrar Sesión                           │");
            System.out.println("└───────────────────────────────────────────┘");
            System.out.print("Seleccione una opción: ");
            
            String op = sc.nextLine();
            switch(op) {
                case "1": banco.mostrarCuentasDeCliente(cli.getCliente().getCodigoCliente()); break;
                case "2": verHistorialCuentaCliente(cli); break;
                case "3": procesarTransaccionCliente("deposito", cli); break;
                case "4": procesarTransaccionCliente("retiro", cli); break;
                case "5": procesarTransaccionCliente("transferencia", cli); break;
                case "6": cli.getCliente().mostrarDatos(); break;
                case "7": cambiarMiContrasena(cli); break;
                case "0": 
                    atras = true;
                    System.out.println("Cerrando sesión...");
                    break;
                default: System.out.println("Opción incorrecta.");
            }
        }
    }

    // ==========================================
    //      MÉTODOS AUXILIARES SIMPLIFICADOS
    // ==========================================

    private void registrarClienteYUsuario() {
        Cliente cli = GestorEntradas.solicitarDatosCliente(banco);
        banco.registrarCliente(cli);
        System.out.println("¿Crear usuario web para el cliente? (S/N): ");
        if(sc.nextLine().equalsIgnoreCase("S")) {
            gestorUsuarios.crearUsuarioCliente(
                GestorEntradas.solicitarNombreUsuario(),
                GestorEntradas.solicitarContrasena(), 
                cli
            );
        }
    }

    private void registrarEmpleadoYUsuario() {
        Empleado nuevoEmp = GestorEntradas.solicitarDatosEmpleado(banco);
        banco.registrarEmpleado(nuevoEmp);
        System.out.println(">> Crear credenciales de acceso:");
        gestorUsuarios.crearUsuarioEmpleado(
            GestorEntradas.solicitarNombreUsuario(), 
            GestorEntradas.solicitarContrasena(), 
            nuevoEmp
        );
    }

    private void crearCuentaBancaria() {
        String codCli = GestorEntradas.solicitarCodigoCliente();
        Cliente dueño = banco.buscarCliente(codCli);
        if(dueño != null) {
            banco.crearCuenta(GestorEntradas.solicitarCodigoCuenta(), dueño);
        } else {
            System.out.println("Error: Cliente no existe.");
        }
    }

    private void mostrarCuentasCliente() {
        banco.mostrarCuentasDeCliente(GestorEntradas.solicitarCodigoCliente());
    }

    // Método unificado para transacciones con empleado
    private void procesarTransaccion(String tipo, Empleado empleado) {
        String idTxn = generarIdTransaccion();
        
        switch(tipo.toLowerCase()) {
            case "deposito":
                banco.depositar(
                    GestorEntradas.solicitarCodigoCliente(), 
                    GestorEntradas.solicitarCodigoCuenta(), 
                    GestorEntradas.solicitarMonto("depositar"), 
                    empleado, idTxn
                );
                break;
            case "retiro":
                banco.retirar(
                    GestorEntradas.solicitarCodigoCliente(), 
                    GestorEntradas.solicitarCodigoCuenta(), 
                    GestorEntradas.solicitarMonto("retirar"), 
                    empleado, idTxn
                );
                break;
            case "transferencia":
                System.out.println("--- CUENTA ORIGEN ---");
                String codCliOrigen = GestorEntradas.solicitarCodigoCliente();
                String codCtaOrigen = GestorEntradas.solicitarCodigoCuenta();
                System.out.println("--- CUENTA DESTINO ---");
                String codCtaDestino = GestorEntradas.solicitarCodigoCuenta();
                
                banco.transferir(codCliOrigen, codCtaOrigen, codCtaDestino,
                    GestorEntradas.solicitarMonto("transferir"), empleado, idTxn);
                break;
        }
    }

    // Método unificado para transacciones de cliente
    private void procesarTransaccionCliente(String tipo, UsuarioCliente cli) {
        String idTxn = generarIdTransaccion();
        String codCta = GestorEntradas.solicitarCodigoCuenta();
        
        if(tipo.equals("transferencia")) {
            System.out.println("--- CUENTA ORIGEN (tuya) ---");
            codCta = GestorEntradas.solicitarCodigoCuenta();
            System.out.println("--- CUENTA DESTINO ---");
            String codCtaDestino = GestorEntradas.solicitarCodigoCuenta();
            double monto = GestorEntradas.solicitarMonto("transferir");
            
            if (!Validaciones.validarCuentasDiferentes(codCta, codCtaDestino)) {
                System.out.println("Error: " + Validaciones.obtenerMensajeError("cuentas_iguales"));
                return;
            }
            
            Titular titular = banco.existeTitular(cli.getCliente().getCodigoCliente(), codCta);
            Cuenta destino = banco.buscarCuenta(codCtaDestino);
            
            if(titular != null && destino != null) {
                Transferencia trans = new Transferencia(destino, monto, idTxn);
                trans.procesar(titular.getCuenta());
                titular.getCuenta().agregarTransaccion(trans);
                destino.agregarTransaccion(trans);
                trans.mostrarEstado();
            } else {
                mostrarErrorTransaccion(titular, destino);
            }
            return;
        }
        
        double monto = GestorEntradas.solicitarMonto(tipo.equals("deposito") ? "depositar" : "retirar");
        Titular titular = banco.existeTitular(cli.getCliente().getCodigoCliente(), codCta);
        
        if(titular != null) {
            Transaccion transaccion = tipo.equals("deposito") 
                ? new Deposito(monto, idTxn) 
                : new Retiro(monto, idTxn);
            
            transaccion.procesar(titular.getCuenta());
            titular.getCuenta().agregarTransaccion(transaccion);
            transaccion.mostrarEstado();
        } else {
            System.out.println("Error: No eres titular de esta cuenta.");
        }
    }

    private void verHistorialCuenta() {
        String codCta = GestorEntradas.solicitarCodigoCuenta();
        Cuenta cuenta = banco.buscarCuenta(codCta);
        if(cuenta != null) {
            cuenta.mostrarHistorial();
        } else {
            System.out.println("Error: Cuenta no encontrada.");
        }
    }

    private void verHistorialCuentaCliente(UsuarioCliente cli) {
        String codCta = GestorEntradas.solicitarCodigoCuenta();
        Titular titular = banco.existeTitular(cli.getCliente().getCodigoCliente(), codCta);
        
        if(titular != null) {
            titular.getCuenta().mostrarHistorial();
        } else {
            System.out.println("Error: No eres titular de esta cuenta.");
        }
    }

    private void modificarUsuario(UsuarioAdministrador admin) {
        System.out.println("\n--- MODIFICAR ESTADO DE USUARIO ---");
        String nombreUser = GestorEntradas.solicitarNombreUsuario();
        Usuario usuarioObjetivo = gestorUsuarios.buscarUsuario(nombreUser);

        if (usuarioObjetivo == null) {
            System.out.println("Error: Usuario no encontrado.");
            return;
        }

        System.out.println("Usuario encontrado: " + usuarioObjetivo.getNombreUsuario() + 
                        " | Estado actual: " + (usuarioObjetivo.getEstado() ? "Activo" : "Inactivo"));
        System.out.print("¿Desea cambiar el estado? (S/N): ");
        
        if (sc.nextLine().equalsIgnoreCase("S")) {
            gestorUsuarios.cambiarEstadoUsuario(nombreUser, !usuarioObjetivo.getEstado());
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void eliminarUsuario(UsuarioAdministrador admin) {
        System.out.println("\n--- ELIMINAR USUARIO ---");
        String userEliminar = GestorEntradas.solicitarNombreUsuario();
        
        if (userEliminar.equalsIgnoreCase(admin.getNombreUsuario())) {
            System.out.println("Error: No puedes eliminar tu propia cuenta de administrador.");
            return;
        }

        System.out.print("¿Está seguro de eliminar el acceso de '" + userEliminar + "'? (S/N): ");
        if (sc.nextLine().equalsIgnoreCase("S")) {
            gestorUsuarios.eliminarUsuario(userEliminar);
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void cambiarMiContrasena(Usuario usuario) {
        System.out.println("\n--- CAMBIAR MI CONTRASEÑA ---");
        System.out.println("Ingrese su contraseña actual:");
        String actual = GestorEntradas.solicitarContrasena();
        System.out.println("Ingrese su nueva contraseña:");
        String nueva = GestorEntradas.solicitarContrasena();
        
        gestorUsuarios.cambiarContrasenia(usuario.getNombreUsuario(), actual, nueva);
    }

    // Métodos auxiliares
    private String generarIdTransaccion() {
        return "TXN" + (int)(Math.random() * 100000);
    }

    private void mostrarErrorTransaccion(Titular titular, Cuenta destino) {
        if (titular == null) {
            System.out.println("Error: No eres titular de la cuenta origen.");
        }
        if (destino == null) {
            System.out.println("Error: La cuenta destino no existe.");
        }
    }
}