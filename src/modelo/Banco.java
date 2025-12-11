package modelo;
        
import BaseDatos.*;
import java.util.*;

public class Banco {
    private ArrayList<Titular> titulares;
    /*Constructor vacío - ya no necesitamos ArrayLists en memoria*/
    public Banco() {
        this.titulares = new ArrayList<>();
    }

    /* === MÉTODOS DE REGISTRO === */

    public void registrarCliente(Cliente cliente) {
        if (!Validaciones.validarObjeto(cliente)) {
            System.out.println("Error: El cliente no puede ser nulo.");
            return;
        }
        
        // Verificar si ya existe en la BD
        if (ClienteDAO.obtenerCliente(cliente.getCodigoCliente()) != null) {
            System.out.println("Error: Ya existe un cliente con el codigo " + cliente.getCodigoCliente());
            return;
        }
        
        // Insertar en la base de datos
        boolean exito = ClienteDAO.insertarCliente(
            cliente.getNombre(),
            cliente.getApellido(),
            cliente.getTelefono(),
            cliente.getCorreo(),
            cliente.getEdad(),
            cliente.getDni(),
            cliente.getDireccion(),
            cliente.getCodigoCliente()
        );
        
        if (exito) {
            System.out.println("Cliente registrado correctamente: " + cliente.getApellido() + " " + cliente.getNombre());
        }
    }

    public void registrarCuenta(Cuenta cuenta) {
        if (!Validaciones.validarObjeto(cuenta)) {
            System.out.println("Error: La cuenta no puede ser nula.");
            return;
        }
        
        // Verificar si ya existe en la BD
        if (CuentaDAO.obtenerCuenta(cuenta.getCodigoCuenta()) != null) {
            System.out.println("Error: Ya existe una cuenta con el codigo " + cuenta.getCodigoCuenta());
            return;
        }
        
        System.out.println("Error: Use crearCuenta() para asociar la cuenta con un cliente.");
    }

    public void registrarEmpleado(Empleado empleado) {
        if (!Validaciones.validarObjeto(empleado)) {
            System.out.println("Error: El empleado no puede ser nulo.");
            return;
        }
        
        // Verificar si ya existe en la BD
        if (EmpleadoDAO.obtenerEmpleado(empleado.getCodigoEmpleado()) != null) {
            System.out.println("Error: Ya existe un empleado con el codigo " + empleado.getCodigoEmpleado());
            return;
        }
        
        // Insertar en la base de datos
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
        
        if (exito) {
            System.out.println("Empleado registrado correctamente: " + empleado.getApellido() + " " + empleado.getNombre());
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

        // El DAO devuelve directamente una Cuenta
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

        try {
            // 1. Obtener dueño real desde SQL
            String dueñoReal = CuentaDAO.obtenerCodigoClientePorCuenta(codigoCuenta);

            if (dueñoReal == null || !dueñoReal.equals(codigoCliente)) {
                System.out.println("La cuenta NO pertenece al cliente");
                return false;
            }

            // 2. Obtener cuenta desde SQL
            Cuenta cuenta = CuentaDAO.obtenerCuenta(codigoCuenta);

            if (cuenta == null) {
                System.out.println("Cuenta no encontrada");
                return false;
            }

            // 3. Actualizar saldo en memoria
            double nuevoSaldo = CuentaDAO.obtenerSaldo(codigoCuenta) + monto;

            // 4. Actualizar saldo en SQL
            CuentaDAO.actualizarSaldo(codigoCuenta, nuevoSaldo);

            // 5. Registrar transacción en SQL
            String codigoEmp = (empleado != null) ? empleado.getCodigoEmpleado() : null;
            TransaccionDAO.insertarTransaccion(codigoCuenta, null, codigoEmp, monto, "deposito");

            return true;

        } catch (Exception e) {
            System.out.println("Error en depósito: " + e.getMessage());
            return false;
        }
    }





    public boolean retirar(String codigoCliente, String codigoCuenta, double monto,
                       Empleado empleado, String ID) {

        try {
            // 1. Verificar en SQL que la cuenta pertenece al cliente
            String dueño = CuentaDAO.obtenerCodigoClientePorCuenta(codigoCuenta);
            if (dueño == null || !dueño.equals(codigoCliente)) {
                System.out.println("La cuenta NO pertenece al cliente.");
                return false;
            }

            // 2. Obtener la cuenta desde SQL
            Cuenta cuenta = CuentaDAO.obtenerCuenta(codigoCuenta);
            if (cuenta == null) {
                System.out.println("No existe la cuenta en SQL.");
                return false;
            }

            // 3. Verificar saldo suficiente
            double saldoActual = CuentaDAO.obtenerSaldo(codigoCuenta);
            if (saldoActual < monto) {
                System.out.println("Saldo insuficiente.");
                return false;
            }

            // 4. Restar el monto al saldo y actualizar en SQL
            double nuevoSaldo = saldoActual - monto;
            boolean actualizado = CuentaDAO.actualizarSaldo(codigoCuenta, nuevoSaldo);
            if (!actualizado) {
                System.out.println("Error al actualizar el saldo en SQL.");
                return false;
            }

            // 5. Registrar transacción en SQL
            String codigoEmp = (empleado != null) ? empleado.getCodigoEmpleado() : null;
            TransaccionDAO.insertarTransaccion(codigoCuenta, null, codigoEmp, monto, "retiro");

            return true;

        } catch (Exception e) {
            System.out.println("Error en retiro: " + e.getMessage());
            return false;
        }
    }



    public Transferencia transferir(String codigoClienteOrigen, String codigoCuentaOrigen, 
                                String codigoCuentaDestino, double monto, Empleado empleado, String ID) {
        
        if (!validarDatosTransaccion(codigoClienteOrigen, codigoCuentaOrigen, ID)) {
            return null;
        }
        
        if (!Validaciones.validarCodigoCuenta(codigoCuentaDestino)) {
            System.out.println("Error: " + Validaciones.obtenerMensajeError("codigo_cuenta"));
            return null;
        }
        
        if (!Validaciones.validarCuentasDiferentes(codigoCuentaOrigen, codigoCuentaDestino)) {
            System.out.println("Error: " + Validaciones.obtenerMensajeError("cuentas_iguales"));
            return null;
        }
        
        Titular titular = existeTitular(codigoClienteOrigen, codigoCuentaOrigen);
        Cuenta destino = buscarCuenta(codigoCuentaDestino);

        if(titular != null && destino != null) {
            Cuenta origen = titular.getCuenta();
            
            // Crear y procesar la transferencia
            Transferencia trans = new Transferencia(empleado, destino, monto, ID);
            trans.procesar(origen);
            
            // Actualizar saldos en la BD
            CuentaDAO.actualizarSaldo(codigoCuentaOrigen, origen.getSaldo());
            CuentaDAO.actualizarSaldo(codigoCuentaDestino, destino.getSaldo());
            
            // Registrar transacción en la BD
            String codigoEmp = empleado != null ? empleado.getCodigoEmpleado() : null;
            TransaccionDAO.insertarTransaccion(codigoCuentaOrigen, codigoCuentaDestino, 
                                              codigoEmp, monto, "transferencia");
            
            imprimirEstadoValidacion(true, trans);
            return trans;
        } else {
            if (titular == null) {
                System.out.println("Error: el cliente no es titular de la cuenta o no existe.");
            }
            if (destino == null) {
                System.out.println("Error: la cuenta destino no existe.");
            }
            return null;
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
    
    public ArrayList<Titular> getListaTitular() {
        return titulares;
    }
}