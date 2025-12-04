package modelo;

import java.util.*;

public class Banco {
    /*Atributos - ArrayLists*/
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Empleado> listaEmpleados;
    private ArrayList<Cuenta> listaCuentas;
    private ArrayList<Titular> listaTitular;

    /*Constructor*/
    public Banco() {
        this.listaClientes = new ArrayList<>();
        this.listaEmpleados = new ArrayList<>();
        this.listaCuentas = new ArrayList<>();
        this.listaTitular = new ArrayList<>();
    }

    /* === MÉTODOS DE REGISTRO === */

    public void registrarCliente(Cliente cliente) {
        if (!Validaciones.validarObjeto(cliente)) {
            System.out.println("Error: El cliente no puede ser nulo.");
            return;
        }
        
        if (buscarCliente(cliente.getCodigoCliente()) != null) {
            System.out.println("Error: Ya existe un cliente con el codigo " + cliente.getCodigoCliente());
            return;
        }
        
        listaClientes.add(cliente);
        System.out.println("Cliente registrado correctamente: " + cliente.getApellido() + " " 
        + cliente.getNombre());
    }

    public void registrarCuenta(Cuenta cuenta) {
        if (!Validaciones.validarObjeto(cuenta)) {
            System.out.println("Error: La cuenta no puede ser nula.");
            return;
        }
        
        if (buscarCuenta(cuenta.getCodigoCuenta()) != null) {
            System.out.println("Error: Ya existe una cuenta con el codigo " + cuenta.getCodigoCuenta());
            return;
        }
        
        listaCuentas.add(cuenta);
    }

    public void registrarEmpleado(Empleado empleado) {
        if (!Validaciones.validarObjeto(empleado)) {
            System.out.println("Error: El empleado no puede ser nulo.");
            return;
        }
        
        if (buscarEmpleado(empleado.getCodigoEmpleado()) != null) {
            System.out.println("Error: Ya existe un empleado con el codigo " + empleado.getCodigoEmpleado());
            return;
        }
        
        listaEmpleados.add(empleado);
        System.out.println("Empleado registrado correctamente: " + empleado.getApellido() + " " 
        + empleado.getNombre());
    }

    public void registrarTitular(Cliente cliente, Cuenta cuenta) {
        if (!Validaciones.validarObjeto(cliente) || !Validaciones.validarObjeto(cuenta)) {
            System.out.println("Error: Cliente o cuenta no pueden ser nulos.");
            return;
        }
        
        if (existeTitular(cliente.getCodigoCliente(), cuenta.getCodigoCuenta()) != null) {
            System.out.println("Error: Ya existe un titular con esos datos.");
            return;
        }
        
        listaTitular.add(new Titular(cliente, cuenta));
    }

    /* === MÉTODOS DE BÚSQUEDA === */

    public Empleado buscarEmpleado(String codigoEmpleado) {
        if (!Validaciones.validarTexto(codigoEmpleado)) {
            return null;
        }
        
        for (Empleado e : listaEmpleados) {
            if(e.getCodigoEmpleado().equalsIgnoreCase(codigoEmpleado)) {
                return e;
            }
        }
        return null;
    }

    public Cliente buscarCliente(String codigoCliente) {
        if (!Validaciones.validarTexto(codigoCliente)) {
            return null;
        }
        
        for (Cliente c : listaClientes) {
            if(c.getCodigoCliente().equalsIgnoreCase(codigoCliente)) {
                return c;
            }
        }
        return null;
    }

    public Cuenta buscarCuenta(String codigoCuenta) {
        if (!Validaciones.validarTexto(codigoCuenta)) {
            return null;
        }
        
        for (Cuenta c : listaCuentas) {
            if(c.getCodigoCuenta().equalsIgnoreCase(codigoCuenta)) {
                return c;
            }
        }
        return null;
    }

    public Titular existeTitular(String codigoCliente, String codigoCuenta) {
        if (!Validaciones.validarTexto(codigoCliente) || !Validaciones.validarTexto(codigoCuenta)) {
            return null;
        }
        
        for(Titular t : listaTitular) {
            if(t.getCliente().getCodigoCliente().equalsIgnoreCase(codigoCliente) && 
            t.getCuenta().getCodigoCuenta().equalsIgnoreCase(codigoCuenta)) {
                return t;
            }
        }
        return null;
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
        
        Cuenta nuevaCuenta = new Cuenta(codigoCuenta);
        registrarCuenta(nuevaCuenta);
        registrarTitular(cliente, nuevaCuenta);
        System.out.println("Cuenta creada y registrada correctamente");
    }

    /* === TRANSACCIONES === */

    public Deposito depositar(String codigoCliente, String codigoCuenta, double monto, 
                            Empleado empleado, String ID) {
        if (!validarDatosTransaccion(codigoCliente, codigoCuenta, ID)) {
            return null;
        }
        
        Titular titular = existeTitular(codigoCliente, codigoCuenta);
        
        if(titular != null) {
            Deposito dep = new Deposito(empleado, monto, ID);
            dep.procesar(titular.getCuenta());
            titular.getCuenta().agregarTransaccion(dep);
            imprimirEstadoValidacion(true, dep);
            return dep;
        } else {
            imprimirEstadoValidacion(false, null);
            return null;
        }
    }

    public Retiro retirar(String codigoCliente, String codigoCuenta, double monto, 
                        Empleado empleado, String ID) {
        if (!validarDatosTransaccion(codigoCliente, codigoCuenta, ID)) {
            return null;
        }
        
        Titular titular = existeTitular(codigoCliente, codigoCuenta);
        
        if(titular != null) {
            Retiro ret = new Retiro(empleado, monto, ID);
            ret.procesar(titular.getCuenta());
            titular.getCuenta().agregarTransaccion(ret);
            imprimirEstadoValidacion(true, ret);
            return ret;
        } else {
            imprimirEstadoValidacion(false, null);
            return null;
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
            Transferencia trans = new Transferencia(empleado, destino, monto, ID);
            trans.procesar(titular.getCuenta());
            titular.getCuenta().agregarTransaccion(trans);
            destino.agregarTransaccion(trans);
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
        if (listaClientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.println("\n=== LISTA DE CLIENTES ===");
        for (Cliente c : listaClientes) {
            c.mostrarDatos();
            System.out.println("------------------------");
        }
    }
    
    public void mostrarEmpleados() {
        if (listaEmpleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        System.out.println("\n=== LISTA DE EMPLEADOS ===");
        for (Empleado e : listaEmpleados) {
            e.mostrarDatos();
            System.out.println("------------------------");
        }
    }
    
    public void mostrarCuentas() {
        if (listaCuentas.isEmpty()) {
            System.out.println("No hay cuentas registradas.");
            return;
        }
        System.out.println("\n=== LISTA DE CUENTAS ===");
        for (Cuenta c : listaCuentas) {
            c.mostrarDatos();
        }
    }
    
    public void mostrarTitulares() {
        if (listaTitular.isEmpty()) {
            System.out.println("No hay titulares registrados.");
            return;
        }
        System.out.println("\n=== LISTA DE TITULARES ===");
        for (Titular t : listaTitular) {
            t.mostrarDatos();
            System.out.println("------------------------");
        }
    }
    
    public ArrayList<Cuenta> buscarCuentasDeCliente(String codigoCliente) {
        ArrayList<Cuenta> cuentasCliente = new ArrayList<>();
        
        if (!Validaciones.validarTexto(codigoCliente)) {
            return cuentasCliente;
        }
        
        for (Titular t : listaTitular) {
            if (t.getCliente().getCodigoCliente().equalsIgnoreCase(codigoCliente)) {
                cuentasCliente.add(t.getCuenta());
            }
        }
        
        return cuentasCliente;
    }
    
    public void mostrarCuentasDeCliente(String codigoCliente) {
        ArrayList<Cuenta> cuentas = buscarCuentasDeCliente(codigoCliente);
        
        if (cuentas.isEmpty()) {
            System.out.println("El cliente no tiene cuentas registradas.");
            return;
        }
        
        System.out.println("\n=== CUENTAS DEL CLIENTE " + codigoCliente + " ===");
        for (Cuenta c : cuentas) {
            c.mostrarDatos();
        }
    }

    /* === GETTERS === */
    
    public ArrayList<Cliente> getListaClientes() {return listaClientes;}
    public ArrayList<Empleado> getListaEmpleados() {return listaEmpleados;}
    public ArrayList<Cuenta> getListaCuentas() {return listaCuentas;}
    public ArrayList<Titular> getListaTitular() {return listaTitular;}
}