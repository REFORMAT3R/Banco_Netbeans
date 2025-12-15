package modelo;

import BaseDatos.*;

public class Inicializador {
    
    // CAMBIO 1: Quitamos 'GestorUsuarios' de los parámetros
    public static void cargarDatosIniciales(Banco banco) {
        
        System.out.println("=== INICIANDO CARGA DE DATOS EN SQL ===");

        // ==========================================
        // 1. CREAR ADMINISTRADOR
        // ==========================================
        Administrador admin = new Administrador("Super", "Admin", "999999999", "admin@banco.com", 
                                              30, "00000001", "Calle Principal 123", "EMP001");
        
        // Guardamos los datos personales en la tabla 'empleado'
        banco.registrarEmpleado(admin); 
        
        //Creamos el Usuario Web y lo guardamos en la tabla 'usuario'
        UsuarioAdministrador uAdmin = new UsuarioAdministrador("admin", "admin123", true, admin);
        banco.registrarUsuario(uAdmin);
        

        // ==========================================
        // 2. EMPLEADOS
        // ==========================================
        
        // Empleado 1: Juan Perez
        Empleado emp1 = new Empleado("Juan", "Perez Vargas", "987654321", "juan.perez@banco.com", 
                                     25, "10000001", "Av. Siempre Viva 742", "EMP002");
        banco.registrarEmpleado(emp1);
        
        UsuarioEmpleado uEmp1 = new UsuarioEmpleado("juan", "juan123", true, emp1);
        banco.registrarUsuario(uEmp1);
        
        // Empleado 2: Ana Gomez
        Empleado emp2 = new Empleado("Ana", "Gomez Guevara", "987123456", "ana.gomez@banco.com", 
                                     27, "10000002", "Jr. Los Pinos 101", "EMP003");
        banco.registrarEmpleado(emp2);
        
        UsuarioEmpleado uEmp2 = new UsuarioEmpleado("ana", "ana123", true, emp2);
        banco.registrarUsuario(uEmp2);


        // ==========================================
        // 3. CLIENTES
        // ==========================================
        
        Cliente cli1 = new Cliente("Maria", "Lopez Ludeña", "912345678", "maria@mail.com", 
                            28, "20000001", "Jr. Flores 456", "CLI001");
        banco.registrarCliente(cli1);
        
        UsuarioCliente uCli1 = new UsuarioCliente("maria", "maria123", true, cli1);
        banco.registrarUsuario(uCli1);

        if(banco.buscarCuenta("CTA00000001") == null) {
             CuentaDAO.insertarCuenta("CTA00000001", cli1.getCodigoCliente());
             CuentaDAO.insertarCuenta("CTA00000004", cli1.getCodigoCliente());
        }

        Cliente cli2 = new Cliente("Carlos", "Ruiz Motta", "922334455", "carlos@mail.com", 
                                    35, "20000002", "Av. Arequipa 880", "CLI002");
        banco.registrarCliente(cli2);
        
        UsuarioCliente uCli2 = new UsuarioCliente("carlos", "carlos123", true, cli2);
        banco.registrarUsuario(uCli2);

        if(banco.buscarCuenta("CTA00000002") == null) {
            CuentaDAO.insertarCuenta("CTA00000002", cli2.getCodigoCliente());
        }

        Cliente cli3 = new Cliente("Elena", "Diaz Yucra", "933445566", "elena@mail.com", 
                                    42, "20000003", "Calle Lima 202", "CLI003");
        banco.registrarCliente(cli3);
        
        UsuarioCliente uCli3 = new UsuarioCliente("elena", "elena123", true, cli3);
        banco.registrarUsuario(uCli3);

        if(banco.buscarCuenta("CTA00000003") == null) {
            CuentaDAO.insertarCuenta("CTA00000003", cli3.getCodigoCliente());
        }


        // ==========================================
        // RESUMEN
        // ==========================================
        System.out.println("\n=====================================================");
        System.out.println(">> BASE DE DATOS INICIALIZADA CON ÉXITO <<");
        System.out.println("-----------------------------------------------------");
        System.out.println(" [ADMINISTRADOR]");
        System.out.println(" - User: 'admin'    Pass: 'admin123'");
        System.out.println("-----------------------------------------------------");
        System.out.println(" [EMPLEADOS]");
        System.out.println(" 1. User: 'juan'     Pass: 'juan123'");
        System.out.println(" 2. User: 'ana'      Pass: 'ana123'");
        System.out.println("-----------------------------------------------------");
        System.out.println(" [CLIENTES]");
        System.out.println(" 1. User: 'maria'    Pass: 'maria123'");
        System.out.println(" 2. User: 'carlos'   Pass: 'carlos123'");
        System.out.println(" 3. User: 'elena'    Pass: 'elena123'");
        System.out.println("=====================================================\n");
    }
}
