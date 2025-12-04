package modelo;

public class Inicializador {
    
    public static void cargarDatosIniciales(Banco banco, GestorUsuarios gestorUsuarios) {
        // ==========================================
        // 1. CREAR ADMINISTRADOR
        // ==========================================
        Administrador admin = new Administrador("Super", "Admin", "999999999", "admin@banco.com", 
                                              30, "00000001", "Calle Principal 123", "EMP001");
        
        banco.getListaEmpleados().add(admin);
        gestorUsuarios.crearUsuarioAdministrador("admin", "admin123", admin);
        

        // ==========================================
        // 2. EMPLEADOS
        // ==========================================
        
        // Empleado 1: Juan Perez
        Empleado emp1 = new Empleado("Juan", "Perez Vargas", "987654321", "juan.perez@banco.com", 
                                     25, "10000001", "Av. Siempre Viva 742", "EMP002");
        banco.registrarEmpleado(emp1);
        gestorUsuarios.crearUsuarioEmpleado("juan", "juan123", emp1);
        
        // Empleado 2: Ana Gomez
        Empleado emp2 = new Empleado("Ana", "Gomez Guevara", "987123456", "ana.gomez@banco.com", 
                                     27, "10000002", "Jr. Los Pinos 101", "EMP003");
        banco.registrarEmpleado(emp2);
        gestorUsuarios.crearUsuarioEmpleado("ana", "ana123", emp2);


        // ==========================================
        // 3. CLIENTES
        // ==========================================
        
        // --- Cliente 1: Maria (CTA001) ---
        Cliente cli1 = new Cliente("Maria", "Lopez Ludeña", "912345678", "maria@mail.com", 
                                    28, "20000001", "Jr. Flores 456", "CLI001");
        banco.registrarCliente(cli1);
        gestorUsuarios.crearUsuarioCliente("maria", "maria123", cli1);
        
        banco.crearCuenta("CTA00000001", cli1);
        if(banco.buscarCuenta("CTA00000001") != null) 
            banco.buscarCuenta("CTA00000001").setSaldo(1500.00); 
        banco.crearCuenta("CTA00000004", cli1);
        if(banco.buscarCuenta("CTA00000004") != null) 
            banco.buscarCuenta("CTA00000004").setSaldo(1000.00);

        // --- Cliente 2: Carlos (CTA002) ---
        Cliente cli2 = new Cliente("Carlos", "Ruiz Motta", "922334455", "carlos@mail.com", 
                                    35, "20000002", "Av. Arequipa 880", "CLI002");
        banco.registrarCliente(cli2);
        gestorUsuarios.crearUsuarioCliente("carlos", "carlos123", cli2);
        
        banco.crearCuenta("CTA00000002", cli2);
        if(banco.buscarCuenta("CTA00000002") != null) 
            banco.buscarCuenta("CTA00000002").setSaldo(5000.50);

        // --- Cliente 3: Elena (CTA003) ---
        Cliente cli3 = new Cliente("Elena", "Diaz Yucra", "933445566", "elena@mail.com", 
                                    42, "20000003", "Calle Lima 202", "CLI003");
        banco.registrarCliente(cli3);
        gestorUsuarios.crearUsuarioCliente("elena", "elena123", cli3);
        
        banco.crearCuenta("CTA00000003", cli3);
        if(banco.buscarCuenta("CTA00000003") != null) 
            banco.buscarCuenta("CTA00000003").setSaldo(300.00);


        // ==========================================
        // DATOS INICIALIZADOS
        // ==========================================
        System.out.println("\n=====================================================");
        System.out.println(">> BASE DE DATOS INICIALIZADA CON ÉXITO <<");
        System.out.println("-----------------------------------------------------");
        System.out.println(" [ADMINISTRADOR]");
        System.out.println(" - User: 'admin'   Pass: 'admin123'");
        System.out.println("-----------------------------------------------------");
        System.out.println(" [EMPLEADOS / CAJEROS]");
        System.out.println(" 1. User: 'juan'    Pass: 'juan123'   (Juan Perez)");
        System.out.println(" 2. User: 'ana'     Pass: 'ana123'    (Ana Gomez)");
        System.out.println("-----------------------------------------------------");
        System.out.println(" [CLIENTES]");
        System.out.println(" 1. User: 'maria'   Pass: 'maria123'  -> CTA00000001 (S/ 1500)");
        System.out.println(" 2. User: 'carlos'  Pass: 'carlos123' -> CTA00000002 (S/ 5000)");
        System.out.println(" 3. User: 'elena'   Pass: 'elena123'  -> CTA00000003 (S/  300)");
        System.out.println("=====================================================\n");
    }
}