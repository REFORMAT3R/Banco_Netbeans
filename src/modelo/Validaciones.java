package modelo;

public class Validaciones {
    
    // Constructor privado para evitar instanciación
    private Validaciones() {}
    
    // ==================== VALIDACIÓN BÁSICA ====================
    
    // Valida que no sea nulo o vacío
    public static boolean validarTexto(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
    
    // Valida que no sea nulo
    public static boolean validarObjeto(Object objeto) {
        return objeto != null;
    }
    
    // ==================== VALIDACIONES DE PERSONA ====================
    
    // Nombre: solo letras, espacios y acentos (mínimo 2 caracteres)
    public static boolean validarNombre(String nombre) {
        if (!validarTexto(nombre)) return false;
        if (nombre.length() < 2) return false;
        return nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }
    
    // DNI: exactamente 8 dígitos
    public static boolean validarDNI(String dni) {
        if (!validarTexto(dni)) return false;
        if (dni.length() != 8) return false;
        return dni.matches("\\d+");
    }
    
    // Teléfono/Celular: 9 dígitos comenzando con 9
    public static boolean validarCelular(String celular) {
        if (!validarTexto(celular)) return false;
        if (celular.length() != 9) return false;
        if (!celular.startsWith("9")) return false;
        return celular.matches("\\d+");
    }
    
    // Correo: formato usuario@dominio.extension
    public static boolean validarCorreo(String correo) {
        if (!validarTexto(correo)) return false;
        if (!correo.contains("@")) return false;
        if (!correo.contains(".")) return false;
        String[] partes = correo.split("@");
        if (partes.length != 2) return false;
        if (partes[0].isEmpty() || partes[1].isEmpty()) return false;
        return true;
    }
    
    // Edad: entre 18 y 120 años
    public static boolean validarEdad(int edad) {
        return edad >= 18 && edad <= 120;
    }
    
    // Dirección: no vacía y al menos 5 caracteres
    public static boolean validarDireccion(String direccion) {
        if (!validarTexto(direccion)) return false;
        return direccion.trim().length() >= 5;
    }
    
    // Nombre de usuario: solo letras, números y guion bajo (mínimo 4 caracteres)
    public static boolean validarNombreUsuario(String nombreUsuario) {
        if (!validarTexto(nombreUsuario)) return false;
        if (nombreUsuario.length() < 4) return false;
        return nombreUsuario.matches("[a-zA-Z0-9_]+");
    }
    
    // Contraseña: al menos 6 caracteres
    public static boolean validarContrasena(String contrasena) {
        if (!validarTexto(contrasena)) return false;
        return contrasena.length() >= 6;
    }
    
    // ==================== VALIDACIONES DE CÓDIGOS ====================
    
    // Código Cliente: CLI seguido de 3-6 dígitos (ej: CLI001, CLI123456)
    public static boolean validarCodigoCliente(String codigo) {
        if (!validarTexto(codigo)) return false;
        if (codigo.length() < 6 || codigo.length() > 9) return false;
        if (!codigo.toUpperCase().startsWith("CLI")) return false;
        String numeros = codigo.substring(3);
        return numeros.matches("\\d+");
    }
    
    // Código Empleado: EMP seguido de 3-6 dígitos (ej: EMP001, EMP123456)
    public static boolean validarCodigoEmpleado(String codigo) {
        if (!validarTexto(codigo)) return false;
        if (codigo.length() < 6 || codigo.length() > 9) return false;
        if (!codigo.toUpperCase().startsWith("EMP")) return false;
        String numeros = codigo.substring(3);
        return numeros.matches("\\d+");
    }
    
    // Código Cuenta: CTA seguido de 8-10 dígitos (ej: CTA00012345, CTA0001234567)
    public static boolean validarCodigoCuenta(String codigo) {
        if (!validarTexto(codigo)) return false;
        if (codigo.length() < 11 || codigo.length() > 13) return false;
        if (!codigo.toUpperCase().startsWith("CTA")) return false;
        String numeros = codigo.substring(3);
        return numeros.matches("\\d+");
    }
    
    // ID Transacción: TXN seguido de números (ej: TXN001, TXN123456)
    public static boolean validarIdTransaccion(String id) {
        if (!validarTexto(id)) return false;
        if (id.length() < 4) return false;
        if (!id.toUpperCase().startsWith("TXN")) return false;
        String numeros = id.substring(3);
        return numeros.matches("\\d+");
    }

    // ==================== VALIDACIONES DE CUENTAS ====================
    
    // Verifica que dos cuentas sean diferentes
    public static boolean validarCuentasDiferentes(String codigo1, String codigo2) {
        if (!validarTexto(codigo1) || !validarTexto(codigo2)) return false;
        return !codigo1.equalsIgnoreCase(codigo2);
    }
    
    // Verifica que la cuenta sea válida
    public static boolean esCuentaValida(Cuenta cuenta) {
        return cuenta != null;
    }
    
    // ==================== VALIDACIONES COMPUESTAS ====================
    
    // Valida todos los datos de una persona
    public static boolean validarDatosPersona(String nombre, String apellido, String telefono, 
                                            String correo, int edad, String dni, String direccion) {
        return validarNombre(nombre) && 
            validarNombre(apellido) && 
            validarCelular(telefono) && 
            validarCorreo(correo) && 
            validarEdad(edad) && 
            validarDNI(dni) && 
            validarDireccion(direccion);
    }
    
    // Valida datos de una transacción
    
    // ==================== MENSAJES DE ERROR ====================
    
    public static String obtenerMensajeError(String campo) {
        String c = campo.toLowerCase();
        
        if (c.equals("nombre") || c.equals("apellido")) 
            return "Debe contener solo letras (mínimo 2 caracteres)";
        if (c.equals("dni")) 
            return "Debe tener exactamente 8 dígitos";
        if (c.contains("telefono") || c.contains("celular")) 
            return "Debe tener 9 dígitos e iniciar con 9";
        if (c.equals("correo")) 
            return "Formato: usuario@dominio.com";
        if (c.equals("edad")) 
            return "Debe estar entre 18 y 120 años";
        if (c.contains("direccion")) 
            return "Debe tener al menos 5 caracteres";
        if (c.contains("usuario") || c.equals("nombreusuario")) 
            return "Mínimo 4 caracteres (letras, números y guion bajo)";
        if (c.contains("contrasena") || c.contains("contraseña") || c.equals("password")) 
            return "Debe tener al menos 6 caracteres";
        if (c.equals("codigo_cliente")) 
            return "Formato: CLI001 (CLI + 3 a 6 dígitos)";
        if (c.equals("codigo_empleado")) 
            return "Formato: EMP001 (EMP + 3 a 6 dígitos)";
        if (c.equals("codigo_cuenta")) 
            return "Formato: CTA00012345 (CTA + 8 a 10 dígitos)";
        if (c.equals("id_transaccion")) 
            return "Formato: TXN12345 (TXN + números)";
        if (c.equals("cuentas_iguales")) 
            return "Las cuentas origen y destino deben ser diferentes";
        if (c.equals("cuenta_invalida")) 
            return "La cuenta no es válida";
        if (c.equals("datos_persona")) 
            return "Los datos personales no son válidos";
        if (c.equals("datos_transaccion")) 
            return "Los datos de la transacción no son válidos";
        
        return "El campo '" + campo + "' no es válido";
    }
}