package modelo;

public abstract class Usuario {
    protected String nombreUsuario;
    protected String contrasenia;
    protected boolean estado;

    /*Constructor*/
    public Usuario(String nombreUsuario, String contrasenia, boolean estado) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.estado = estado;
    }

    /*Getters*/
    public String getNombreUsuario() {return nombreUsuario;}
    public String getContrasenia() {return contrasenia;}
    public boolean getEstado() {return estado;}
    
    /*Setters*/
    public void setNombreUsuario(String nombreUsuario) {this.nombreUsuario = nombreUsuario;}
    public void setContrasenia(String contrasenia) {this.contrasenia = contrasenia;}
    public void setEstado(boolean estado) {this.estado = estado;}

    // Método login: valida credenciales del usuario según la guía de laboratorio
    public boolean login(String usuario, String contraseña) { // Parámetro cambiado de "password" a "contraseña" según requerimientos
        if (!estado) { // Verifica si el usuario está activo
            System.out.println("Usuario inactivo. No puede iniciar sesión.");
            return false; // Retorna false si el usuario está inactivo
        }
        
        // Compara el nombre de usuario y la contraseña proporcionados con los almacenados
        if (this.nombreUsuario.equals(usuario) && this.contrasenia.equals(contraseña)) { // Usa el parámetro "contraseña" en la comparación
            System.out.println("Login exitoso. Bienvenido " + nombreUsuario);
            return true; // Retorna true si las credenciales son correctas
        } else {
            System.out.println("Credenciales incorrectas.");
            return false; // Retorna false si las credenciales no coinciden
        }
    }

    public void mostrarDatos() {
        System.out.println("=== Datos de Usuario ===");
        System.out.println("Usuario: " + nombreUsuario);
        System.out.println("Estado: " + (estado ? "Activo" : "Inactivo"));
    }

    public abstract void mostrarPermisos();
}