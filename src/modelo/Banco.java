package modelo;
        
import BaseDatos.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Banco {
    private ArrayList<Titular> titulares;
    /*Constructor vacío - ya no necesitamos ArrayLists en memoria*/
    public Banco() {
        this.titulares = new ArrayList<>();
    }

    /* === MÉTODOS DE REGISTRO === */

    public boolean registrarCliente(Cliente cliente) {
        if (!Validaciones.validarObjeto(cliente)) {
            return false;
        }

        
        // 1. DNI
        if (ClienteDAO.existeDNI(cliente.getDni()) || EmpleadoDAO.existeDNI(cliente.getDni())) {
            System.out.println("Error: DNI duplicado.");
            return false; // Retorna falso para avisar a la ventana
        }

        // 2. Correo
        if (ClienteDAO.existeCorreo(cliente.getCorreo()) || EmpleadoDAO.existeCorreo(cliente.getCorreo())) {
            System.out.println("Error: Correo duplicado.");
            return false;
        }

        // 3. Teléfono
        if (ClienteDAO.existeTelefono(cliente.getTelefono()) || EmpleadoDAO.existeTelefono(cliente.getTelefono())) {
            System.out.println("Error: Teléfono duplicado.");
            return false;
        }

        // Si pasa todo, intentamos insertar
        boolean exito = ClienteDAO.insertarCliente(
            cliente.getNombre(), cliente.getApellido(), cliente.getTelefono(),
            cliente.getCorreo(), cliente.getEdad(), cliente.getDni(),
            cliente.getDireccion(), cliente.getCodigoCliente()
        );
        
        return exito; // Devolvemos el resultado del DAO
    }
    
    
    public void registrarCuenta(Connection conn, Cuenta cuenta) {
        if (!Validaciones.validarObjeto(cuenta)) {
            System.out.println("Error: La cuenta no puede ser nula.");
            return;
        }

        // Verificar si ya existe en la BD usando la conexión proporcionada
        if (CuentaDAO.obtenerCuenta(conn, cuenta.getCodigoCuenta()) != null) {
            System.out.println("Error: Ya existe una cuenta con el código " + cuenta.getCodigoCuenta());
            return;
        }

        System.out.println("Error: Use crearCuenta() para asociar la cuenta con un cliente.");
    }


    public boolean registrarEmpleado(Empleado empleado) {
        if (!Validaciones.validarObjeto(empleado)) {
            return false;
        }
        
        // --- VALIDACIONES CRUZADAS (Busca en ambas tablas) ---
        
        // 1. DNI
        if (EmpleadoDAO.existeDNI(empleado.getDni()) || ClienteDAO.existeDNI(empleado.getDni())) {
            System.out.println("Error: El DNI ya está registrado en el sistema.");
            return false;
        }
        
        // 2. Correo
        if (EmpleadoDAO.existeCorreo(empleado.getCorreo()) || ClienteDAO.existeCorreo(empleado.getCorreo())) {
            System.out.println("Error: El correo ya está en uso.");
            return false;
        }
        
        // 3. Teléfono
        if (EmpleadoDAO.existeTelefono(empleado.getTelefono()) || ClienteDAO.existeTelefono(empleado.getTelefono())) {
            System.out.println("Error: El teléfono ya está registrado.");
            return false;
        }
        
        // --- INSERTAR EN BD ---
        boolean exito = EmpleadoDAO.insertarEmpleado(
            empleado.getNombre(),
            empleado.getApellido(),
            empleado.getTelefono(),
            empleado.getCorreo(),
            empleado.getEdad(),
            empleado.getDni(),
            empleado.getDireccion(),
            empleado.getCodigoEmpleado()
        );
        
        return exito;
    }
    
    public void registrarUsuario(Usuario usuario) {
        if (usuario == null) {
            System.out.println("Error: El usuario no puede ser nulo.");
            return;
        }

        if (UsuarioDAO.existeUsuario(usuario.getNombreUsuario())) {
            System.out.println("Error: El usuario '" + usuario.getNombreUsuario() + "' ya existe.");
            return;
        }

        boolean exito = UsuarioDAO.insertarUsuario(usuario);
        
        if (exito) {
            System.out.println("Usuario web registrado: " + usuario.getNombreUsuario());
        } else {
            System.out.println("Error al registrar usuario en la base de datos.");
        }
    }

    public void registrarTitular(Cliente cliente, Cuenta cuenta) {
        // Ya no es necesario, la relación se maneja en la tabla cuenta
        System.out.println("Relación cliente-cuenta ya establecida en la base de datos.");
    }
    
    /* === MÉTODOS DE BÚSQUEDA === */

    public Empleado buscarEmpleado(String codigoEmpleado) {
        if (!Validaciones.validarTexto(codigoEmpleado)) {
            return null;
        }

        // Solicita el objeto directamente
        Empleado e = EmpleadoDAO.obtenerEmpleado(codigoEmpleado);

        if (e == null) {
            return null;
        }

        return e; // Listo, ya no hay nada que parsear
    }


    public Cliente buscarCliente(String codigoCliente) {
        if (!Validaciones.validarTexto(codigoCliente)) {
            return null;
        }

        // Ahora obtenerCliente devuelve un Cliente directamente
        Cliente cliente = ClienteDAO.obtenerCliente(codigoCliente);

        if (cliente == null) {
            return null;
        }

        return cliente; // No hay nada más que parsear
    }


    public Cuenta buscarCuenta(String codigoCuenta) {
        if (!Validaciones.validarTexto(codigoCuenta)) {
            return null;
        }

        // El DAO devuelve directamente una Cuenta usando la conexión proporcionada
        return CuentaDAO.obtenerCuenta(codigoCuenta);
    }

    /* === CREACIÓN DE CUENTA === */

    public void crearCuenta(String codigoCuenta, Cliente cliente) {
        if (!Validaciones.validarCodigoCuenta(codigoCuenta)) {
            System.out.println("Error: " + Validaciones.obtenerMensajeError("codigo_cuenta"));
            return;
        }
        
        if (!Validaciones.validarObjeto(cliente)) {
            System.out.println("Error: El cliente no puede ser nulo.");
            return;
        }
        
        // Insertar cuenta en la BD asociada al cliente
        boolean exito = CuentaDAO.insertarCuenta(codigoCuenta, cliente.getCodigoCliente());
        
        if (exito) {
            System.out.println("Cuenta creada y registrada correctamente");
        }
    }

    /* === TRANSACCIONES === */

    public boolean depositar(String codigoCliente, String codigoCuenta, double monto, 
                         Empleado empleado, String ID) {

        try (Connection conn = Conexion.conectar()) {
            if (conn == null) {
                System.out.println("No se pudo conectar a la base de datos.");
                return false;
            }

            // 1. Obtener dueño real desde SQL
            String dueñoReal = CuentaDAO.obtenerCodigoClientePorCuenta(conn, codigoCuenta);
            if (dueñoReal == null || !dueñoReal.equals(codigoCliente)) {
                System.out.println("La cuenta NO pertenece al cliente");
                return false;
            }

            // 2. Obtener saldo actual
            double saldoActual = CuentaDAO.obtenerSaldo(conn, codigoCuenta);

            // 3. Actualizar saldo
            double nuevoSaldo = saldoActual + monto;
            CuentaDAO.actualizarSaldo(conn, codigoCuenta, nuevoSaldo);

            // 4. Registrar transacción
            String codigoEmp = (empleado != null) ? empleado.getCodigoEmpleado() : null;
            TransaccionDAO.insertarTransaccion(conn, codigoCuenta, null, codigoEmp, monto, "deposito");

            return true;

        } catch (Exception e) {
            System.out.println("Error en depósito: " + e.getMessage());
            return false;
        }

    }

    public boolean retirar(Connection conn, String codigoCliente, String codigoCuenta, double monto,
                           Empleado empleado, String ID) {

        try {
            // 1. Verificar en SQL que la cuenta pertenece al cliente
            String dueño = CuentaDAO.obtenerCodigoClientePorCuenta(conn, codigoCuenta);
            if (dueño == null || !dueño.equals(codigoCliente)) {
                System.out.println("La cuenta NO pertenece al cliente.");
                return false;
            }

            // 2. Obtener la cuenta desde SQL
            Cuenta cuenta = CuentaDAO.obtenerCuenta(conn, codigoCuenta);
            if (cuenta == null) {
                System.out.println("No existe la cuenta en SQL.");
                return false;
            }

            // 3. Verificar saldo suficiente
            double saldoActual = CuentaDAO.obtenerSaldo(conn, codigoCuenta);
            if (saldoActual < monto) {
                System.out.println("Saldo insuficiente.");
                return false;
            }

            // 4. Restar el monto al saldo y actualizar en SQL
            double nuevoSaldo = saldoActual - monto;
            boolean actualizado = CuentaDAO.actualizarSaldo(conn, codigoCuenta, nuevoSaldo);
            if (!actualizado) {
                System.out.println("Error al actualizar el saldo en SQL.");
                return false;
            }

            // 5. Registrar transacción en SQL
            String codigoEmp = (empleado != null) ? empleado.getCodigoEmpleado() : null;
            TransaccionDAO.insertarTransaccion(conn, codigoCuenta, null, codigoEmp, monto, "retiro");

            return true;

        } catch (Exception e) {
            System.out.println("Error en retiro: " + e.getMessage());
            return false;
        }
    }




    public boolean transferir(Connection conn, String codigoClienteOrigen, String codigoCuentaOrigen,
                                   String codigoCuentaDestino, double monto, Empleado empleado) {
            boolean estadoAutocommit = true; 
            try {
                estadoAutocommit = conn.getAutoCommit();
                conn.setAutoCommit(false); // <--- IMPORTANTE: Iniciamos transacción manual

                if (codigoCuentaOrigen.equals(codigoCuentaDestino)) {
                    System.out.println("Error: No puede transferir a la misma cuenta.");
                    return false;
                }

                // B. Verificar que la cuenta origen sea del cliente
                String dueño = CuentaDAO.obtenerCodigoClientePorCuenta(conn, codigoCuentaOrigen);
                if (dueño == null || !dueño.equals(codigoClienteOrigen)) {
                    System.out.println("Error: La cuenta origen no pertenece al cliente.");
                    return false;
                }

                // C. Obtener saldos y verificar existencia
                Double saldoOrigen = CuentaDAO.obtenerSaldo(conn, codigoCuentaOrigen);
                Double saldoDestino = CuentaDAO.obtenerSaldo(conn, codigoCuentaDestino);

                if (saldoOrigen == null) {
                    System.out.println("Error: La cuenta de origen no existe.");
                    return false;
                }
                if (saldoDestino == null) {
                    System.out.println("Error: La cuenta de destino no existe.");
                    return false;
                }

                // D. Verificar fondos suficientes
                if (saldoOrigen < monto) {
                    System.out.println("Error: Saldo insuficiente para realizar la transferencia.");
                    return false;
                }

                // Restar al origen
                if (!CuentaDAO.actualizarSaldo(conn, codigoCuentaOrigen, saldoOrigen - monto)) {
                    conn.rollback(); 
                    return false;
                }

                // Sumar al destino
                if (!CuentaDAO.actualizarSaldo(conn, codigoCuentaDestino, saldoDestino + monto)) {
                    conn.rollback(); 
                    return false;
                }

                // Guardar Historial
                String codigoEmp = (empleado != null) ? empleado.getCodigoEmpleado() : null;
                if (!TransaccionDAO.insertarTransaccion(conn, codigoCuentaOrigen, codigoCuentaDestino, codigoEmp, monto, "transferencia")) {
                    conn.rollback(); // Si falla el historial, deshacemos todo el dinero movido
                    return false;
                }

                conn.commit(); // Confirmamos los cambios
                return true;

            } catch (Exception e) {
                try { conn.rollback(); } catch (SQLException ex) { } // Deshacer en caso de error inesperado
                System.out.println("Error crítico en transferencia: " + e.getMessage());
                return false;
            } finally {
                try { conn.setAutoCommit(estadoAutocommit); } catch (SQLException ex) { }
                
            }
    }



    /* === MÉTODO AUXILIAR DE VALIDACIÓN === */
    
    private boolean validarDatosTransaccion(String codigoCliente, String codigoCuenta, String ID) {
        if (!Validaciones.validarCodigoCliente(codigoCliente)) {
            System.out.println("Error: " + Validaciones.obtenerMensajeError("codigo_cliente"));
            return false;
        }
        
        if (!Validaciones.validarCodigoCuenta(codigoCuenta)) {
            System.out.println("Error: " + Validaciones.obtenerMensajeError("codigo_cuenta"));
            return false;
        }
        
        if (!Validaciones.validarIdTransaccion(ID)) {
            System.out.println("Error: " + Validaciones.obtenerMensajeError("id_transaccion"));
            return false;
        }
        
        return true;
    }

    public void imprimirEstadoValidacion(boolean valido, Transaccion t) {
        if(valido) {
            t.mostrarEstado();
        } else {
            System.out.println("Error: el cliente no es titular de la cuenta o no existe.");
        }
    }

    /* === MÉTODOS PARA MOSTRAR LISTAS === */
    
    public void mostrarClientes() {
        List<Cliente> clientes = ClienteDAO.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.println("\n=== LISTA DE CLIENTES ===");
        for (Cliente c : clientes) {
            System.out.println(c);
            System.out.println("------------------------");
        }
    }
    
    public void mostrarEmpleados() {
        List<Empleado> empleados = EmpleadoDAO.listarEmpleados();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        System.out.println("\n=== LISTA DE EMPLEADOS ===");
        for (Empleado emp : empleados) {
            System.out.println(emp);
        }
    }
    
    public void mostrarCuentas() {
        List<Cuenta> cuentas = CuentaDAO.listarCuentas();

        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas registradas.");
            return;
        }

        System.out.println("\n=== LISTA DE CUENTAS ===");
        for (Cuenta c : cuentas) {
            System.out.println(c);
        }
    }

   
    public List<Cuenta> buscarCuentasDeCliente(String codigoCliente) {
        if (!Validaciones.validarTexto(codigoCliente)) {
            return new ArrayList<>();
        }
        return CuentaDAO.buscarCuentasCliente(codigoCliente);
    }

    
    public void mostrarCuentasDeCliente(String codigoCliente) {
        List<Cuenta> cuentas = buscarCuentasDeCliente(codigoCliente);
        
        if (cuentas.isEmpty()) {
            System.out.println("El cliente no tiene cuentas registradas.");
            return;
        }
        
        System.out.println("\n=== CUENTAS DEL CLIENTE " + codigoCliente + " ===");
        for (Cuenta c : cuentas) {
            c.mostrarDatos();
        }
    }

    /* === MÉTODOS PARA OBTENER LISTAS DESDE LA BD === */
    
    public List<Cliente> getListaClientes() {
        return ClienteDAO.listarClientes();
    }
    
    public List<Empleado> getListaEmpleados() {
        return EmpleadoDAO.listarEmpleados();
    }
    public List<Cuenta> getListaCuentas() {
        return CuentaDAO.listarCuentas();
    }
    
    public List<Usuario> getListaUsuarios() {
        return UsuarioDAO.listarUsuarios();
    }
    
    public ArrayList<Titular> getListaTitular() {
        return titulares;
    }
}